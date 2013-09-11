package chatclient.sink

import scala.actors.Actor
import scala.actors.remote.RemoteActor
import scala.actors.remote.RemoteActor._
import scala.actors.remote.Node

import chatclient.source._
import chatclient.store._

case class Auth(email:String)

class RemoteHandler(port:Int) extends Actor {
	// mongo store for remote storage
	private val store = new Datastore

	def act() {
		RemoteActor.classLoader = getClass().getClassLoader()
		alive(port)
		register('ChatClient, this)
		
		loop {
			react {
				case Auth(email) => {
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