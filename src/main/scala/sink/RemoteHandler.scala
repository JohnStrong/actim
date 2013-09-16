package chatclient.sink

import akka.actor.Actor
import akka.actor.Props

import chatclient.source.MessageHandler
import chatclient.store.{ClientStore, MessageStore, ClientEntity}

import xml._

// singleton that holds case objects for remote Actor
object Remote {

	// authentication/get profile data
	case class Validate(email:String)
	case class Done(status:String)
}

class Remote(port:Int) extends Actor {

	// mongo store for remote storage
	val clientEntity = new ClientEntity(new ClientStore())

	def receive = {
		
		// if successful return profile and offline messages
		case Remote.Validate(email) => {
			implicit val e = email

			clientEntity.getOne match {
				case Some(profile) => {
					// return xml to message handler
					sender ! MessageHandler.Confirm(profile)
				}
				case _ => // send error
			}

			
		}
	}
}