package chatclient.sink

import akka.actor.{ Actor, Props, ActorRef}
import xml._
import com.mongodb.casbah.Imports._

object Interceptor {

	// store messages
	case class Clients(list: List[DBObject])

	// client interceptor messages
	case class Account(message: Elem)
	case class Message(message: Elem)
	case class Done(status: String)

	/**
	* Remote Actor that handles incoming messages from any client and responds with an xml message
	**/
	class Interceptor extends Actor {

		import chatclient.store.{ClientStore, MessageStore, ClientEntity}
		import ClientEntity._
		
		// start client entity actor
		val clientStore = new ClientStore
		val clientEntity = context.actorOf(Props(classOf[ClientEntity], 
			clientStore), name = "cliententity.store.chatclient")

		// all client accounts
		allAccounts()

		def allAccounts() {
			clientEntity ! All
		}

		// listen for messages
		def receive = {

			case Clients(x) => context.become(action(self, x))
			case _ => println("unrecognised message from store")
		}

		// handles messages from clients
		def action(actor: ActorRef, clients: List[DBObject]): Actor.Receive = {
			case Account(login) => { 
					login match {
						case <client><login>{email}</login></client> => println(email)
						case _ => println("unrecognised message from client")
					}
				}

			case Message(message) => println(message \\ "to"); println(message \\ "from") //todo
			case Done(x) => context.stop(self)
			case _ => println("unrecognised message")
		}
	}
}