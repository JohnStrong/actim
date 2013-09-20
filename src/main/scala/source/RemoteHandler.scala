package chatclient.source

import akka.actor.{ActorRef, ActorSystem, Actor, Props}

object RemoteHandler {

	case class Login(email: String)
	case class PackagedLogin(elem: xml.Elem)
	case class Confirm(details: xml.Elem)

	class RemoteHandler(remoteActor:ActorRef) extends Actor {

		import Client._
		import Package._
		import chatclient.sink.Remote._

		// start package actor
		val pack = context.actorOf(Props[Package], name = "package.source.chatclient")

		def receive = {

			case Login(email) => {
				println(email); 
				pack ! PackageLogin(email)
				//todo
			}

			case PackagedLogin(elem) => {
				println(elem)
				remoteActor ! "test"
				//todo
			}

			case Confirm(details) => {
				//todo
			}
		}
	}
}