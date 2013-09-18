package chatclient.store

import akka.actor.Actor
import akka.actor.Actor._
import akka.actor.Props

import com.mongodb.casbah.Imports._

import xml._

object ClientEntity {
	case object All
	case class One(email: String)
}

// wrapper class for calls the datastore object
class ClientEntity extends Actor {
	
	val pack = context.actorOf(Props[Package], "package.store.chatclient")
	val datastore = new ClientStore

	def receive = {
		
		case ClientEntity.All => {
			datastore.getAll()
		}

		// find client information to build profile view
		case ClientEntity.One(email) => {

			datastore.getOne(email) match {

				case Some(x) => {
					pack ! Package.Client(x)
				}

				case _ => // handle error
			}
		}
	}
}