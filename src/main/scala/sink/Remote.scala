package chatclient.sink

import scala.actors.Actor
import scala.actors.remote.RemoteActor
import scala.actors.remote.RemoteActor._
import scala.actors.remote.Node

object Remote extends App {
	val PORT = 8080
	new RemoteHandler(PORT).start
}