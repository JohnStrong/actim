package chatclient.ccd

import xml._

object PatternPackage {

	case class Message(from: String, body: String)
	case class Login(email: String)
	case class Ready(clientDetails: Profile)

	case class Done(status: String)
	case class Profile(email: String, name: String)

	// error messages
	case class ServerError(message: String)
	case class DatastoreError(message: String)
}