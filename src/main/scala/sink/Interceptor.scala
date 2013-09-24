package chatclient.sink

import akka.actor.{ Actor, Props}
import xml._

object Interceptor {

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
		val accounts = allAccounts()

		// retrieve all accounts in datastore
		def allAccounts() {
			// todo
			val users = clientEntity ! All()
			println(users)
		}

		// listen for messages
		def receive = {

			// if successful return profile details
			case Account(login) => { 
				login match {
					case <client><login>{email}</login></client> => println(email)
					case _ => println("unrecognised message")
				}
			}

			case Message(message) => println(message \\ "to"); println(message \\ "from") //todo
			case Done(x) => context.stop(self)
			case _ => println("unrecognised message")
		}
	}
}