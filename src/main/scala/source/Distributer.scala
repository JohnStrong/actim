package chatclient.source

import akka.actor.{ActorRef, Actor, Props, Identify, ActorIdentity, ReceiveTimeout}
import scala.concurrent.duration._

import xml._

import chatclient.ccw.CCW._

/**
* this class will handle package data from ui events as well as send/receieve 
* messages from the remote actor
**/
class Distributer(path:String) extends Actor {

	import Packager._

	val pack = context.actorOf(Props[Packager], name = "packager")
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

		// unpackaged messages
		case Login(email) => pack ! PackageLogin(email)
		case SendMessage(to, from, body) => pack ! PackageMessage(to, from, body)

		// packaged messages
		case PLogin(elem) => actor ! Account(elem)
		case PMessage(elem) => actor ! Message(elem)

		// response messages
		case Home(details) => //todo
		case _ => println("unknown messege")
	}
}