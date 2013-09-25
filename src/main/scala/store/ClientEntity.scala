package chatclient.store

import akka.actor.Actor
import akka.actor.Actor._
import akka.actor.Props

import com.mongodb.casbah.Imports._

object ClientEntity {

	case class All

	// class that contains methods to perform specific tasks against the datastore
	class ClientEntity(datastore:Datastore) extends Actor {
		
		import RemotePackager._
		import chatclient.sink.Interceptor._

		val pack = context.actorOf(Props[RemotePackager], "package.store.chatclient")

		def receive = {
			
			case All => {
				sender ! Clients(datastore.find())
			}
		}
	}
}

