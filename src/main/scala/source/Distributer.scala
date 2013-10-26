package chatclient.source

import akka.actor.{ActorRef, Actor, Props, Identify, ActorIdentity, ReceiveTimeout}
import scala.concurrent.duration._

import chatclient.ccd.PatternPackage._

/**
* this class will handle package data from ui events as well as send/receieve 
* messages from the remote actor
**/
class Distributer(path:String) extends Actor {

	/***********************************************
	* get a reference to the remote node
	***********************************************/
	context.setReceiveTimeout(3.seconds)
	requestIdentity

	def requestIdentity: Unit =
		 	context.actorSelection(path) ! Identify(path)

	def receive = {

		case ActorIdentity(`path`, Some(actor)) => {

		    context.setReceiveTimeout(Duration.Undefined)
		    context.become(active(actor))
		}

	    case ActorIdentity(`path`, None) => println("Remote actor not availible")

	    case ReceiveTimeout => requestIdentity

	    case _ => println("Not ready yet")
	}

	/***********************************************
	* listen for incoming client interface &
	* remote messages
	***********************************************/
	def active(remoteActor: ActorRef): Actor.Receive = {

		case Login(email) => remoteActor ! Login(email)

		case Logout(email) => remoteActor ! Logout(email)

		case SendMessage(from, message) => 
			remoteActor ! SentMessage(from, message)

		case Ready(profile, messages) => {

			Client.clientReady(profile.email, profile.name)
			Client.displayOfflineMessages(messages)
		}

		case ReceiveMessage(from, message) =>
			Client.displayMessage(from, message)

		case FailedAuthError(err) =>
			println(err)

		case UnkownMessageError(err) =>
			println(err)

		case _ => println("unknown messege")
	}
}