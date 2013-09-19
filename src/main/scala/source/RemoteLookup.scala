package chatclient.source

import akka.actor._

import scala.concurrent.duration._

object RemoteLookup {

	case class Login(email: String)
	case class PackagedLogin(elem: xml.Elem)
	case class Confirm(details: xml.Elem)

	class RemoteLookup(path:String) extends Actor {

		import Client._
		import Package._
		import chatclient.sink.Remote._

		// start package actor
		val pack = context.actorOf(Props[Package], "chatclient.source.chatclient")

		context.setReceiveTimeout(3.seconds)
		sendIdentifyRequest()

		// get a handle to the remote actor
		def sendIdentifyRequest():Unit = {
			context.actorSelection(path) ! Identify(path)
		}

		def receive = {

			case ActorIdentity(`path`, Some(actor)) => 
				println("actor identified")
				
				context.setReceiveTimeout(Duration.Undefined)
				context.become(active(actor))
			case ActorIdentity(`path`, None) => println("Remote actor not availible")
			case ReceiveTimeout => sendIdentifyRequest()
			case _ => println("Not ready yet")
		}

		def active(client: ActorRef): Actor.Receive = {
			// listen for incoming messages from the Client UI

			case Login(email) => {
				println(email)
				// todo
			}
			
			case PackagedLogin(elem) => {
				println(elem)
				// todo
			}

			case Confirm(profile) => {
				println("confirm : " + profile)
				// todo
			}

			case _ => println("unknown")
		}
	}
}