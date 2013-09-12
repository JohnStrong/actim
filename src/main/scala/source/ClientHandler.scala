package chatclient.source

import scala.actors._
import scala.actors.Actor._
import scala.actors.remote.RemoteActor
import scala.actors.remote.RemoteActor._
import scala.actors.remote.Node

import chatclient.sink.RemoteHandler
import chatclient.sink.Validate

/* =+= actor case-classes =+= */
case class Confirm(result: String)
case class Auth(email: String)

// handle UI events
class ClientMessageHandler {
	private val clientActor = new ClientActor

	def login(email:String) {
		clientActor.message ! Auth(email)
	}
}

// send/receive messages to/form remote actor
class ClientActor {
	RemoteActor.classLoader = getClass().getClassLoader()

	// Port to listen for Remote Actor on
	private val PORT = 8080

	// Address of the Remote Actor
	private val ADDRESS = "127.0.0.1"

	// Open a Peer connection to the Port on the Address
	private val PEER = Node(ADDRESS, PORT)

	val message:Actor = actor {
		// find the remote actor on the 'ChatClient namespace
		val remote = select(PEER, 'ChatClient)

		// listen for incoming messages from the Client UI
		receive {
			case Auth(email) => {
				remote ! Validate(email)
				reply {
					receive {
						case Confirm(str) => println("confirm : " + str)
					}
				}
			}
		}
	}
}