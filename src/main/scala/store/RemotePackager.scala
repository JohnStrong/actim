package chatclient.store

import akka.actor.Actor
import akka.actor.Actor._
import akka.actor.Props

import xml._
import com.mongodb.casbah.Imports._

object RemotePackager {

	case class Client(entity: DBObject)
	case class Message(entity: DBObject)

	class RemotePackager extends Actor {

		import ClientEntity._

		implicit def as(obj: DBObject) = new As(obj)
		
		def receive = {

			case Client(entity) => {
				
				// get attributes we are interested in
				val email = entity string("email")
				val name = entity string("name")
				val about = entity string("about")

				val details = <client>
					<id>{email}</id>
					<name>{name}</name>
					<about>{about}</about>
				</client>
			}	

			// package message columns
			case Message(entity) => {
				
				<message>test</message>

				// return to sender
			}
		}
	}
}