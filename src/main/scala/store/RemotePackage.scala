package chatclient.store

import akka.actor.Actor
import akka.actor.Actor._
import akka.actor.Props

import xml._
import com.mongodb.casbah.Imports._

object RemotePackage {
	case class Client(entity: DBObject)
	case class Message(entity: DBObject)

	class RemotePackage extends Actor {

		implicit def as(obj: DBObject) = new As(obj)

		override def preStart():Unit = {
			// override
		}

		def receive = {
			case RemotePackage.Client(entity) => {
				
				// get attributes we are interested in
				val email = entity string("email")
				val name = entity string("name")
				val about = entity string("about")

				val details = <client>
					<id>{email}</id>
					<name>{name}</name>
					<about>{about}</about>
				</client>

				// return to sender

			}	

			// package message columns
			case RemotePackage.Message(entity) => {
				
				<message>test</message>

				// return to sender
			}
		}
	}
}