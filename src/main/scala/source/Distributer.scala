package chatclient.source

import akka.actor.{ActorRef, Actor, Props, Identify, ActorIdentity, ReceiveTimeout}
import scala.concurrent.duration._

object Distributer {

	case class Login(email: String)
	case class Packaged(elem: xml.Elem)
	case class Confirm(details: xml.Elem)

	/**
	* this class will handle package data from ui events as well as send/receieve 
	* messages from the remote actor
	**/
	class Distributer(path:String) extends Actor {

		import Client._
		import Packager._
		import chatclient.sink.Interceptor._

		val pack = context.actorOf(Props[Packager], name = "package.source.chatclient")
		context.setReceiveTimeout(5.seconds)
  		requestIdentity()


		def requestIdentity(): Unit =
   		 	context.actorSelection(path) ! Identify(path)

   		 // listen for identity request response
		def receive = {
			case ActorIdentity(`path`, Some(actor)) ⇒
			    context.setReceiveTimeout(Duration.Undefined)
			    context.become(active(actor))
		    case ActorIdentity(`path`, None) ⇒ println("Remote actor not availible")
		    case ReceiveTimeout ⇒ requestIdentity()
		    case _ ⇒ println("Not ready yet")
		}

		// send an receive messages to/from remote
		def active(actor: ActorRef): Actor.Receive = {
			case Login(email) => pack ! PackageLogin(email)
			case Packaged(elem) => actor ! Retrieve(elem)
			case Confirm(details) => //todo
			case _ => println("unknown messege")
		}
	}
}