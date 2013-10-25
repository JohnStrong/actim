package chatclient.ccd

import scala.collection._

object PatternPackage {

	case class SendMessage(from: String, body: String)
	case class Login(email: String)

	case class Ready(clientDetails: Profile, messages: mutable.Map[String, String])
	case class SentMessage(name:String, body:String)
	case class ReceiveMessage(from:String, message:String)
	case class Done(status: String)

	// entity messages
	case class Profile(email: String, name: String)

	// error messages
	case class ServerError(message: String)
	case class DatastoreError(message: String)

	// misc
	case class UnrecognisedMessage(warning:String)
}