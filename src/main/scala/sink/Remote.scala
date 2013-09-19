package chatclient.sink

import akka.actor.Actor
import akka.actor.Props

object Remote {
	// authentication/get profile data
	case class Retrieve(message:xml.Elem)
	case class Done(status:String)

	class Remote extends Actor {

		import chatclient.source.Client
		import chatclient.store.{ClientStore, MessageStore, ClientEntity}

		import ClientEntity._
		
		// start client entity actor
		val clientEntity = context.actorOf(Props(new ClientEntity(
			new ClientStore())), name="cliententity.store.chatclient")

		// listen for messages
		def receive = {

			// if successful return profile details
			case Retrieve(message) => {
				println(message)
				// clientEntity ! ClientEntity.One("")
			}

			case Done(x) => context.stop(self)
		}
	}
}