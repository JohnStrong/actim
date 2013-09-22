package chatclient.store

import akka.actor.Actor
import akka.actor.Actor._
import akka.actor.Props

import com.mongodb.casbah.Imports._

object ClientEntity {

	case object All
	case class One(email: String)

	// wrapper class for calls the datastore object
	class ClientEntity(datastore:Datastore) extends Actor {
		
		import RemotePackager._

		val pack = context.actorOf(Props[RemotePackager], "package.store.chatclient")

		def receive = {
			
			case All => datastore.getAll()
			case One(email) => {

				datastore.getOne(email) match {

					case Some(x) => {
						pack ! Client(x)
					}

					case _ => // handle error
				}
			}
		}
	}
}

