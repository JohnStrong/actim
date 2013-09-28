package chatclient.sink


import scala.concurrent.Await

import akka.actor.{ Actor, Props, ActorRef}
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._

import xml._

import com.mongodb.casbah.Imports._

object Interceptor {

	// client interceptor messages
	case class Account(message: Elem)
	case class Message(message: Elem)
	case class Done(status: String)
}
/**
* Remote Actor that handles incoming messages from any client and responds with an xml message
**/
class Interceptor extends Actor {

	import chatclient.store.{ClientStore, MessageStore, ClientEntity}
	import Interceptor._
	import ClientEntity._
	
	// start client entity actor
	val clientStore = new ClientStore
	val clientEntity = context.actorOf(Props(classOf[ClientEntity], 
		clientStore), name = "clientEntity")

	// all client accounts
	val clients = allAccounts()

	def allAccounts():Iterator[Client] = {
		implicit val timeout = Timeout(5 seconds)
		Await.result(clientEntity ? All, timeout.duration).asInstanceOf[Iterator[Client]]
	}

	// listen for messages
	def receive = {

		case Account(credentials) => {
			//todo: return xml message of userInstance parameter values
			val email = credentials \\ "login"
			findClient(clients, "j.strong1@nuigalway.ie") match {
				case Some(c) => println(c)
				case _ => println("no client matching that email was found")
			}
		}
		case Message(message) => 
			println(message \\ "to"); println(message \\ "from") //todo
		case Done(x) => context.stop(self)
		case _ => println("unrecognised message")
	}

	private def findClient(clients:Iterator[Client], target:String):Option[Client] = {
		val c = for (client <- clients 
				if client.email == target) yield Some(client)

		c.next()
	}
}