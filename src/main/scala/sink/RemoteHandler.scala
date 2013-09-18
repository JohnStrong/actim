package chatclient.sink

import akka.actor.Actor
import akka.actor.Props

import chatclient.source.Client
import chatclient.store.{ClientStore, MessageStore, ClientEntity}

import xml._

// singleton that holds case objects for remote Actor
object Remote {

	// authentication/get profile data
	case class Validate(message:Elem)
	case class Done(status:String)
}

class Remote extends Actor {

	// start client entity actor
	
	val clientEntity = context.actorOf(Props[ClientEntity])

	// listen for messages
	def receive = {

		// if successful return profile and offline messages
		case Remote.Validate(message) => {
			
			// clientEntity ! One()
			// to finish
		}

		case Remote.Done(x) => context.stop(self)
	}
}