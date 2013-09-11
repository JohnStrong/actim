package chatclient.source

import scala.actors.Actor
import scala.actors.remote.RemoteActor
import scala.actors.remote.RemoteActor._
import scala.actors.remote.Node

import chatclient.sink._

case class Confirm(result: String)

class ClientHandler(peer:Node) extends Actor{
	def act() {
		RemoteActor.classLoader = getClass().getClassLoader()
		val remote = select(peer, 'ChatClient)

		remote ! Auth("strngj411@gmail.com")

		loop {
			react {
				case Confirm(result:String) => println("confirmed....")
			}
		}
	}
}