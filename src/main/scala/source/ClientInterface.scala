package chatclient.source

import swing._
import swing.event._

/**
 *	TODO: remove Horizontal ScrollBar in UI
 *	TODO: add window listener to listening for window close evts
 */

object ClientInterface extends SimpleSwingApplication {

	// get a handle to the Client message wrapper
	private val clientEvtHandler = Client

	// top level method, builds the UI on start-up
	def top = new MainFrame {

		title = "Akka Chatroom"

		contents = contentMain

		resizable = true

		centerOnScreen()

		size = new Dimension(500, 600)
	}

	val chatFeed = new TextArea(60, 50) {
		wordWrap = true
	}

	val mainInput = new TextField(20) {
		text = "example@google.com"
	}

	val mainEvtListener = new Button {
		text = "Login"
	}

	/**
	 *	@return
	 *		Swing Component containing:
	 *			Text Field for user input
	 *			Button for sending the input
	 *			Text Area for displaying Chat History
	 */
	private val contentMain = new BoxPanel(Orientation.Vertical) {

		contents += new BoxPanel(Orientation.Horizontal) {

			contents += mainInput
			contents += mainEvtListener
		}

		contents += new ScrollPane(chatFeed)

		listenTo(mainEvtListener)
		reactions += {

			case ButtonClicked(b) if b.text == "Login" =>
				clientEvtHandler.login(mainInput.text)

            case ButtonClicked(b) if b.text == "Send" => 
            	clientEvtHandler.sendMessage(mainInput.text)
		}
	}
}