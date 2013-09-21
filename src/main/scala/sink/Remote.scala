package chatclient.sink

import akka.actor.{ Actor, Props}

object Remote {

	case class Retrieve(message:xml.Elem)
	case class Done(status:String)

	/**
	* Remote Actor that handles incoming messages from any client and responds with an xml message
	**/
	class Remote extends Actor {

		import chatclient.store.{ClientStore, MessageStore, ClientEntity}
		import ClientEntity._
		
		val clientStore = new ClientStore

		// start client entity actor
		val clientEntity = context.actorOf(Props(classOf[ClientEntity], 
			clientStore), name = "cliententity.store.chatclient")

		// listen for messages
		def receive = {

			// if successful return profile details
			case Retrieve(message) => //todo: parse xml to deduce apropriate action
			case Done(x) => context.stop(self)
			case _ => println("unrecognised message")
		}
	}
}