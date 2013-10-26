package chatclient.source

import swing._
import swing.event._

/**
 * TODO: remove Horizontal ScrollBar in UI
 */

/**
 * This object creates the UI that a cient will interact with
 * UI events will be passed to the backend as messages to be processed and evaluated
 */
object ClientInterface extends SimpleSwingApplication {

	// setup of Client
	private val clientEvtHandler = Client

	// builds UI components on start-up
	def top = new MainFrame {

		title = "Akka Chatroom"

		contents = contentMain

		resizable = true

		centerOnScreen()

		size = new Dimension(500, 600)
	}

	// chat history feed where messages are displayed
	val chatFeed = new TextArea(60, 50) {
		wordWrap = true
	}

	// loaded with default login values
	val mainInput = new TextField(20) {
		text = "example@google.com"
	}

	// button to handle login and message events
	val mainEvtListener = new Button {
		text = "Login"
	}

	// flow content body of the UI
	private val contentMain = new BoxPanel(Orientation.Vertical) {

		contents += new BoxPanel(Orientation.Horizontal) {

			contents += mainInput
			contents += mainEvtListener
		}

		contents += new ScrollPane(chatFeed)

		// register button component and handle events
		listenTo(mainEvtListener)
		reactions += {

			case ButtonClicked(b) if b.text == "Login" =>
				clientEvtHandler.login(mainInput.text)

            case ButtonClicked(b) if b.text == "Send" => 
            	clientEvtHandler.sendMessage(mainInput.text)
		}
	}
}