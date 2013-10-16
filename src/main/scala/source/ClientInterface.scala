package chatclient.source

import swing._
import swing.event._

/**
* This object creates the UI that a cient will interact with
* UI events will be passed to the backend as messages to be processed and evaluated
**/
object ClientInterface extends SimpleSwingApplication{

	private val client = Client
	def top = LoginView
}

object LoginView extends Frame {
	
	private val textField = new TextField {
		text = "example@gmail.com"
	}

	private val loginButton = new Button {
		text = "login"
	}

	title = "Akka Instant Messager Login"

	resizable = false

	// view contents
	contents = new BoxPanel(Orientation.Horizontal) {
		contents += textField
		contents += loginButton
	}

	listenTo(loginButton)
	reactions += {
		case ButtonClicked(b) =>
			Client.login(textField.text)
	}
}

object ChatView extends Frame {
	
	private val textField = new TextField {
		text = ""
	}

	private val loginButton = new Button {
		text = "send"
	}

	title = "Akka Chatroom"

	resizable = false

	// view contents
	contents = new BoxPanel(Orientation.Horizontal) {
		contents += textField
		contents += loginButton
	}

	listenTo(loginButton)
	reactions += {
		case ButtonClicked(b) =>
			Client.sendMessage(textField.text)
	}
}