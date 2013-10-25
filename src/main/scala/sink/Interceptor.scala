package chatclient.sink


import scala.concurrent.Await

//import akka.routing.{FromConfig, Broadcast}
import akka.actor.{ Actor, Props, ActorRef, ActorSystem}
import com.typesafe.config.ConfigFactory

import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.duration._
import scala.collection._

import com.mongodb.casbah.Imports._

import chatclient.ccd.PatternPackage._

import chatclient.store._
import chatclient.store.EntityMessages._

/**
* Remote Actor that handles incoming messages from any client and responds with an xml message
**/

class Interceptor extends Actor {

	// used for operation requiring immediate future result
	implicit val TIMEOUT = Timeout(5 seconds)
		
	/**********************************************
	* get the Entity classes for wrapping db queries
	***********************************************/
	private val clientStore = new ClientStore
	private val messageStore = new MessageStore

	private val clientEntity = context.actorOf(Props(classOf[ClientEntity], 
		clientStore), name = "clientEntity")

	private val messageEntity = context.actorOf(Props(classOf[MessageEntity], 
		messageStore), name = "messageEntity") 

	/***********************************************
	* store application state
	***********************************************/
	private val clients = allAccounts
	private val connected = mutable.Map.empty[String, ActorRef]

	def allAccounts:List[Client] = {

		Await.result(clientEntity ? All, TIMEOUT.duration).asInstanceOf[List[Client]]
	}

	/***********************************************
	* listen for incoming client messages
	***********************************************/
	def receive = {

		case Login(email) => {

			if(!connected.contains(email)) {

				findClient(allAccounts, email) match {

					case Some(c) => {

						connected += (email -> sender)

						val unparsedM = Await.result(
							messageEntity ? All, 
							TIMEOUT.duration
						).asInstanceOf[List[Message]]
						
						unparsedM.foreach(m => println(m))

						sender ! Ready(Profile(c.email, c.name), extractMessages(unparsedM))
					}

					case _ => sender ! UnrecognisedMessage("client email does not match any entry")
				}
			}
		}

		case SentMessage(from, message) => {

			// insert the message into datastore to be retrieved by new clients
			messageEntity ! Insert(from, message)

			// send message to all users online
			connected.foreach(x => x._2 ! ReceiveMessage(from, message))
		}

		case Done(x) => context.stop(self)

		case _ => println("unrecognised message")
	}

	/***********************************************
	* helper methods
	***********************************************/
	private def findClient(clients:List[Client], target:String):Option[Client] = {
		
		var client:Option[Client] = None
		var found = false

		for(c <- clients if found != true) {
			if(c.email == target) {
				client = Some(c)
				found = true
			}
		}

		client
	}

	private def extractMessages(unparsedMessages:List[Message]):mutable.Map[String, String] = {
		
		var messages = mutable.Map.empty[String, String]
		
		unparsedMessages.foreach(m => {
			messages += (m.name -> m.body) 
		})

		messages
	}
}