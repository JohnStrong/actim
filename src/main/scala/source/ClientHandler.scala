package chatclient.source

import akka.actor.Actor
import akka.actor.Props

import chatclient.sink.Remote

import xml._

object MessageHandler {
	/* =+= actor case-classes =+= */
	case class Confirm(profile: Elem)
	case class Auth(email: String)
}

// send/receive messages to/form remote actor
class Client extends Actor {
	
	val remote =
		context.actorSelection("akka.tcp://127.0.0.1:8080/ChatClient")

	// listen for incoming messages from the Client UI
	def receive = {
		
		case MessageHandler.Auth(email) => {
			remote ! Remote.Validate(email)
		}
		case MessageHandler.Confirm(profile) => {
			println("confirm : " + profile)
		}
	}
}