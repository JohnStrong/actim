package chatclient.source

import xml._

import akka.actor.Actor
import akka.actor.Props

// handle UI events and package to xml format
object MessagePackage {
	case class Login(email: String)
}

class ClientMessageHandler extends Actor{
	override def preStart():Unit = {
		val clientHandler = context.actorOf(Props[ClientHandler], "clientHandler")

		clientHandler ! ClientHandle.Auth("strngj411@gmail.com")
	}

	def receive = {
		case MessagePackage.Login(x) => // todo
	}
}