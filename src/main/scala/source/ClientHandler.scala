package chatclient.source

import akka.actor.Actor
import akka.actor.Props

import chatclient.sink.Remote

import xml._

object Client {

	case class Confirm(profile: Elem)
	case class Login(email: String)
}

// send/receive messages to/form remote actor
class Client extends Actor {

	val pack = context.actorOf(Props[Package], "package.source.chatclient")
	val remote = context.actorSelection("akka.tcp://127.0.0.1:8080/ChatClient")

	// listen for incoming messages from the Client UI
	def receive = {
		
		case Client.Login(email) => {

			pack ! Package.Login(email)
		}
		
		case Client.Confirm(profile) => {
			println("confirm : " + profile)
		}

		case _ => // unknown response
	}
}