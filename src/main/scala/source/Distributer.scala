package chatclient.source

import akka.actor.{ActorRef, Actor, Props, Identify, ActorIdentity, ReceiveTimeout}
import scala.concurrent.duration._

import chatclient.ccd.PatternPackage._

/**
* this class will handle package data from ui events as well as send/receieve 
* messages from the remote actor
**/
class Distributer(path:String) extends Actor {

	context.setReceiveTimeout(5.seconds)
	requestIdentity()

	def requestIdentity(): Unit =
		 	context.actorSelection(path) ! Identify(path)

		 // listen for identity request response
	def receive = {
		case ActorIdentity(`path`, Some(actor)) =>
		    context.setReceiveTimeout(Duration.Undefined)
		    context.become(active(actor))
	    case ActorIdentity(`path`, None) => println("Remote actor not availible")
	    case ReceiveTimeout => requestIdentity()
	    case _ => println("Not ready yet")
	}

	// send an receive messages to/from remote
	def active(actor: ActorRef): Actor.Receive = {

		// client messages
		case Login(email) => actor ! Login(email)
		case Message(to, from, message) => 
			actor ! Message(to, from, message)

		// server responses
		case Ready(profile) => 
			Client.clientReady(profile.email, profile.name)
		case _ => println("unknown messege")
	}
}