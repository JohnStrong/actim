package chatclient.source

import akka.actor.Actor
import akka.actor.Props

import chatclient.sink.RemoteHandle

object ClientHandle {
	/* =+= actor case-classes =+= */
	case class Confirm(result: String)
	case class Auth(email: String)
}

// send/receive messages to/form remote actor
class ClientHandler extends Actor {
	
	val remote =
		context.actorSelection("akka.tcp://127.0.0.1:8080/ChatClient")

	// listen for incoming messages from the Client UI
	def receive = {
		case ClientHandle.Auth(email) => {
			remote ! RemoteHandle.Validate(email)
		}
		case ClientHandle.Confirm(str) => println("confirm : " + str)
	}
}