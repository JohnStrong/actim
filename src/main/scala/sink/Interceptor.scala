package chatclient.sink


import scala.concurrent.Await

import akka.actor.{ Actor, Props, ActorRef}
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._

import xml._

import com.mongodb.casbah.Imports._

import chatclient.ccd.PatternPackage._

/**
* Remote Actor that handles incoming messages from any client and responds with an xml message
**/
class Interceptor extends Actor {

	import chatclient.store.{ClientStore, MessageStore, ClientEntity}
	import ClientEntity._
	
	// start client entity actor
	private val clientStore = new ClientStore
	private val clientEntity = context.actorOf(Props(classOf[ClientEntity], 
		clientStore), name = "clientEntity")

	// all client accounts
	//private val clients = allAccounts()

	def allAccounts:List[Client] = {
		implicit val timeout = Timeout(5 seconds)
		Await.result(clientEntity ? All, timeout.duration).asInstanceOf[List[Client]]
	}

	// listen for messages
	def receive = {

		case Login(email) => {

			findClient(allAccounts, email) match {
				case Some(c) => sender ! Ready(Profile(c.email, c.name))
				case _ => println("no client matching that email was found")
			}
		}
		case Message(to, from, message) => //todo
		case Done(x) => context.stop(self)
		case _ => println("unrecognised message")
	}

	// verify that a client with the given email exists before allowing 
	// them to chat
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
}