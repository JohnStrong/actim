package chatclient.source

import swing._
import swing.event._

/**
* This object creates the UI that a cient will interact with
* UI events will be passed to the backend as messages to be processed and evaluated
**/
object ClientInterface extends SimpleSwingApplication{

	val clientEvtHandler = Client

	// loaded with default login values
	lazy val mainInput = new TextField {
		text = "example@gmail.com"
	}

	lazy val mainEvtListener = new Button {
		text = "Login"
	}

	
	// flow content body of the UI
	lazy val contentMain = new FlowPanel {

		contents += mainInput
		contents += mainEvtListener

		listenTo(mainEvtListener)
		reactions += {
			case ButtonClicked(b) =>
               	clientEvtHandler.login(mainInput.text)
		}
	}

	// top level main frame of the application
	def top = new MainFrame {

		title = "Akka instant messenger"

		contents = contentMain

		resizable = false
	}
}