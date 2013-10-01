package chatclient.ccw

import xml._

object CCW {

	trait Remote
	case class Account(message: Elem) extends Remote
	case class Message(message: Elem) extends Remote
	case class Done(status: String) extends Remote

	trait Client
	case class SendMessage(to: String, from: String, body: String) extends Client
	case class Login(email: String) extends Client
	case class PLogin(elem: xml.Elem) extends Client
	case class PMessage(elem: xml.Elem) extends Client
	case class Home(details: xml.Elem) extends Client
}