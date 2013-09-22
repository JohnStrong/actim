package chatclient.sink

import akka.actor.{ Actor, Props}

object Interceptor {

	case class Retrieve(message:xml.Elem)
	case class Done(status:String)

	/**
	* Remote Actor that handles incoming messages from any client and responds with an xml message
	**/
	class Interceptor extends Actor {

		import chatclient.store.{ClientStore, MessageStore, ClientEntity}
		import ClientEntity._
		
		// start client entity actor
		val clientStore = new ClientStore
		val clientEntity = context.actorOf(Props(classOf[ClientEntity], 
			clientStore), name = "cliententity.store.chatclient")

		override def preStart():Unit = {
			val users = clientEntity ! All()
			println(users)
		}

		// listen for messages
		def receive = {

			// if successful return profile details
			case Retrieve(message) => println(message) //todo
			case Done(x) => context.stop(self)
			case _ => println("unrecognised message")
		}
	}
}