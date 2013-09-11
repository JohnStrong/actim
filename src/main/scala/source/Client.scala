package chatclient.source

import java.io._
import java.net._

import scala.actors.Actor
import scala.actors.remote.RemoteActor
import scala.actors.remote.RemoteActor._
import scala.actors.remote.Node

// client Actor
object Client extends App{
	val PORT = 8080
	val ADDRESS = "127.0.0.1"
	val peer = Node(ADDRESS, PORT)

	val clientHandler:Actor = new ClientHandler(peer)
	clientHandler.start
}