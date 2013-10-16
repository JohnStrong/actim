package chatclient.ccd

import xml._

object PatternPackage {

	case class Message(to: String, from: String, body: String)
	case class Login(email: String)
	case class Ready(clientDetails: Profile)

	case class Done(status: String)
	case class Profile(email: String, name: String)
}