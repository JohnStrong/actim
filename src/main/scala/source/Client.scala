package chatclient.source


import akka.actor.Actor
import akka.actor.Props

object Client {

	case class Confirm(profile: xml.Elem)
	case class Login(email: String)
	case class PackagedLogin(elem: xml.Elem)

	// send/receive messages to/form remote actor
	class Client extends Actor {

		import chatclient.sink.Remote._
		import Package._

		// start package actor
		val pack = context.actorOf(Props[Package], "package.source.chatclient")

		// get a handle to the remote actor
		val remote = context.actorSelection("akka.tcp://RemoteSystem@127.0.0.1:8080/remote.sink.chatclient")

		// listen for incoming messages from the Client UI
		def receive = {
			
			case Login(email) => {
				pack ! PackageLogin(email)
			}
			
			case PackagedLogin(elem) => {
				println(elem)
				remote ! Retrieve(elem)
			}

			case Confirm(profile) => {
				println("confirm : " + profile)
			}

			case _ => println("unknown")
		}
	}
}