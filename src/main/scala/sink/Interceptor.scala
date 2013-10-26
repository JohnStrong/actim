package chatclient.sink


import scala.concurrent.Await
import scala.concurrent.duration._
import scala.collection._

import akka.actor.{ Actor, Props, ActorRef, ActorSystem}
import com.typesafe.config.ConfigFactory

import akka.pattern.ask
import akka.util.Timeout

import com.mongodb.casbah.Imports._

import chatclient.ccd.PatternPackage._

import chatclient.store._
import chatclient.store.EntityMessages._
	
/**
* Remote Actor that handles incoming messages from any client 
* and responds with an xml message
**/
class Interceptor extends Actor {
	
	/**********************************************
	* get the Entity classes for wrapping db queries
	***********************************************/
	private val clientStore = new ClientStore
	private val messageStore = new MessageStore

	private val clientEntity = context.actorOf(
		Props(classOf[ClientEntity], 
			clientStore), name = "clientEntity")

	private val messageEntity = context.actorOf(
		Props(classOf[MessageEntity], 
			messageStore), name = "messageEntity")

	/***********************************************
	* store application state
	***********************************************/
	private val clients = allAccounts

	private val connected = mutable.Map.empty[String, ActorRef]

	/***********************************************
	* listen for incoming client messages
	***********************************************/
	def receive = {

		case Login(email) if !connected.contains(email) => {

			findClient(allAccounts, email) match {

				case Some(c) => {

					connected += (email -> sender)

					sender ! Ready(
						Profile(c.email, c.name), 
						extractMessages
					)
				}

				case _ => 
					sender ! FailedAuthError("client email does not match any entry")
			}
		}

		case Logout(email) => {

			connected - email 
			connected.foreach((kv) => println(kv._1))
		}

		// insert the message into datastore to be retrieved by new clients	
		// send message to all users online
		case SentMessage(from, message) => {

			messageEntity ! Insert(from, message)
			connected.foreach(x => 
				x._2 ! ReceiveMessage(from, message))
		}

		case Done(x) => context.stop(self)

		case _ => 
			sender ! UnkownMessageError("unrecognised message")
	}

	/***********************************************
	* helper methods
	***********************************************/

	private def allAccounts:List[Client] = {
		
		// used for operation requiring immediate future result
		implicit val TIMEOUT = Timeout(5 seconds)

		Await.result(
			clientEntity ? All, 
			TIMEOUT.duration
		).asInstanceOf[List[Client]]
	}

	// returns an Option of an element in the 'clients' collection that equals the 'target'
	private def findClient(clients:List[Client], target:String):Option[Client] =
		clients.find(client => client.email == target)

	// returns a List of Tuples containing a message body and the source' username
	private def extractMessages:List[(String, String)] = {
		
		implicit val TIMEOUT = Timeout(10 seconds)

		val unparsedMessages = Await.result(
				messageEntity ? All, 
				TIMEOUT.duration
			).asInstanceOf[List[Message]]

		unparsedMessages.map(m => {
			new Tuple2(m.name, m.body)
		}).toList
	}
}