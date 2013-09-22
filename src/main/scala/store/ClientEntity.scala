package chatclient.store

import akka.actor.Actor
import akka.actor.Actor._
import akka.actor.Props

import com.mongodb.casbah.Imports._

object ClientEntity {

	case class All
	case class One(email: String)

	// class that contains methods to perform specific tasks against the datastore
	class ClientEntity(datastore:Datastore) extends Actor {
		
		import RemotePackager._

		val pack = context.actorOf(Props[RemotePackager], "package.store.chatclient")

		def receive = {
			
			// TODO
			case All => {
				datastore.getAll()
				"test"
			}

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

