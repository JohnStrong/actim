package chatclient.source

import swing._
import swing.event._

/**
 * This object creates the UI that a cient will interact with
 * UI events will be passed to the backend as messages to be processed and evaluated
 **/
object ClientInterface extends SimpleSwingApplication {

	// setup of Client
	val clientEvtHandler = Client

	// chat history feed where messages are displayed
	val chatFeed = new TextArea(100, 50) {
		
	}

	// loaded with default login values
	val mainInput = new TextField(10) {
		text = "example@google.com"
	}

	// button to handle login and message events
	val mainEvtListener = new Button {
		text = "Login"
	}

	// flow content body of the UI
	private val contentMain = new GridPanel(3, 1) {

		contents += new FlowPanel {
			contents += mainInput
			contents += mainEvtListener
		}
		contents += chatFeed

		listenTo(mainEvtListener)
		reactions += {
			case ButtonClicked(b) if b.text == "Login" => {
				clientEvtHandler.login(mainInput.text)
            }
            case ButtonClicked(b) if b.text == "Send" => {
            	clientEvtHandler.sendMessage(mainInput.text)
            }
		}
	}

	// top level main frame of the application
	def top = new MainFrame {

		title = "Akka instant messenger"

		contents = contentMain

		resizable = true

		centerOnScreen()

		size = new Dimension(200, 500)
	}
}