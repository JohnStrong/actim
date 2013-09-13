package chatclient.sink

import scala.actors.Actor
import scala.actors.remote.RemoteActor
import scala.actors.remote.RemoteActor._

import chatclient.store.QueryProcessor
import chatclient.source._

import xml._

case class Validate(email:String)

class RemoteHandler(port:Int) extends Actor {

	// thread listening for client messages
	def act() {
		RemoteActor.classLoader = getClass().getClassLoader()
		alive(port)
		register('ChatClient, this)
		
		// mongo store for remote storage
		val query = new QueryProcessor

		loop {
			react {
				// user authentication
				case Validate(email) => {
					sender ! Confirm(query find(email))
				}
			}
		}
	}
}