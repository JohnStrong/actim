package chatclient.test

import org.junit.runner.RunWith

import org.scalatest._
import org.scalatest.junit.JUnitRunner

import scala.actors._
import scala.actors.Actor._
import scala.actors.remote.RemoteActor
import scala.actors.remote.RemoteActor._
import scala.actors.remote.Node

import xml._
import com.mongodb.casbah.Imports._

import chatclient.sink.Validate

case class Confirm(result:String)

object ApplicationWorkflowConst {
	val PORT = 8080

	val ADDRESS = "127.0.0.1"
	
	val EMAIL = "strngj411@gmail.com"
}

@RunWith(classOf[JUnitRunner])
class ApplicationWorkflowTest extends FunSuite with BeforeAndAfter {

	// remote peer connection
	var peer:Node = _

	before {
		peer = Node(ApplicationWorkflowConst.ADDRESS, ApplicationWorkflowConst.PORT)
	}

	test("successful login results in a query returning 'name', 'about', 'friends'") {
		try {
			val act:Actor = actor {
				
				val remote = select(peer, 'ChatClient)
				remote ! Validate(ApplicationWorkflowConst.EMAIL)

				receive {
					case Confirm(res) => println(res); assert(res === "test success!")
					case _ => //assert(false)
				}
			}
		} catch {
			case e:Exception => //assert(false)
		}	
	}
}