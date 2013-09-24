package chatclient.store

import akka.actor.Actor
import akka.actor.Actor._
import akka.actor.Props

import com.mongodb.casbah.Imports._

object ClientEntity {

	case class All
	
	case class PackagedClients(elem: xml.Elem)

	// class that contains methods to perform specific tasks against the datastore
	class ClientEntity(datastore:Datastore) extends Actor {
		
		import RemotePackager._

		val pack = context.actorOf(Props[RemotePackager], "package.store.chatclient")

		def receive = {
			
			case All => {
				datastore.find().map(x => {
					pack ! Client(x)
				})
			}

			case PackagedClients(x) => //todo (returns clients to sender)
		}
	}
}

