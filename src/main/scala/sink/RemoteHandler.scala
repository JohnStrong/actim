package chatclient.sink

import scala.actors.Actor
import scala.actors.remote.RemoteActor
import scala.actors.remote.RemoteActor._
import scala.actors.remote.Node

import chatclient.source._
import chatclient.store.ClientHandler
import chatclient.store.Confirm

import xml._

case class Validate(email:String)

class RemoteHandler(port:Int) extends Actor {
	
	// mongo store for remote storage
	private val store = new Datastore

	// thread listening for client messages
	def act() {
		RemoteActor.classLoader = getClass().getClassLoader()
		alive(port)
		register('ChatClient, this)
		
		loop {
			react {
				// user authentication
				case Validate(email) => {
					println("email: " + email)

					sender ! Confirm(store find(email) match {
						case Some(x) => "success"
						case _ => "fail"
					})
				}
			}
		}
	}
}