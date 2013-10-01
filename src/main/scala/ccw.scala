package chatclient.ccw

import xml._

object CCW {

	case class Account(message: Elem)
	case class Message(message: Elem)
	case class Done(status: String)

	trait Client
	case class SendMessage(to: String, from: String, body: String) extends Client
	// sends to Packager class the email entered by the client on login
	case class Login(email: String) extends Client
	// receives a packaged (xml) message for a login
	case class PLogin(elem: xml.Elem) extends Client
	// receives a packaged (xml) message for messages
	case class PMessage(elem: xml.Elem) extends Client
	// parse user details returned from server for client home page
	case class Home(details: xml.Elem) extends Client
}