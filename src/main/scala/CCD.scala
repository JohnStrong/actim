package chatclient.ccd

import scala.collection._

object PatternPackage {

	// client side massages
	case class SendMessage(from: String, body: String)
	case class Login(email: String)
	case class Logout(email: String)
	case class Ready(
		clientDetails: Profile, 
		messages: List[Tuple2[String, String]]
	)
	case class ReceiveMessage(from:String, message:String)

	
	// server side messages
	case class SentMessage(name:String, body:String)
	case class Done(status: String)
	case class Profile(email: String, name: String)

	// error messages
	trait Error
	case class UnkownMessageError(message: String) extends Error
	case class FailedAuthError(warning:String) extends Error

}
