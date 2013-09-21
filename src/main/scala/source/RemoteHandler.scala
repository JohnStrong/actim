package chatclient.source

import akka.actor.{ActorRef, ActorSystem, Actor, Props}

object RemoteHandler {

	case class Login(email: String)
	case class Packaged(elem: xml.Elem)
	case class Confirm(details: xml.Elem)

	/**
	* this class will handle package data from ui events as well as send/receieve 
	* messages from the remote actor
	**/
	class RemoteHandler(remoteActor:ActorRef) extends Actor {

		import Client._
		import Package._
		import chatclient.sink.Remote._

		val pack = context.actorOf(Props[Package], name = "package.source.chatclient")

		def receive = {

			case Login(email) => pack ! PackageLogin(email)
			case Packaged(elem) => remoteActor ! Retrieve(elem)
			case Confirm(details) => //todo
			case _ => println("unknown messege")
		}
	}
}