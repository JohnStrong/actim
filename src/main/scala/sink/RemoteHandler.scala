package chatclient.sink

import akka.actor.Actor
import akka.actor.Props

import chatclient.store.QueryProcessor
import chatclient.source.ClientHandle

import xml._

class Remote extends Actor {

	// create chatClient actor
	override def preStart(): Unit = {
		val chatClient = context.actorOf(Props[RemoteHandler], "chatClient")
	}

	def receive = {
		case RemoteHandle.Done(x) => context.stop(self)
	}
}

// singleton that holds case objects for remote Actor
object RemoteHandle {

	// authentication/get profile data
	case class Validate(email:String)
	case class Done(status:String)
}

class RemoteHandler(port:Int) extends Actor {

	// mongo store for remote storage
	val query = new QueryProcessor

	def receive = {
		
		// if successful return profile and offline messages
		case RemoteHandle.Validate(email) => {
			implicit var e = email

			val profile:Elem = query getProfile
			val messages:Elem = query getUnreadMessages

			sender ! ClientHandle.Confirm("test")
		}
	}
}