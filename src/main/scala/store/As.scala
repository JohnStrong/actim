package chatclient.store

import com.mongodb.casbah.Imports._

class As(obj: DBObject) {

	// return field from mongo collection as an integer
	def number(field: String):Option[Int] = obj.getAs[Int](field)

	// return field from mongo collection as a string
	def string(field: String):Option[String] = obj.getAs[String](field)

	// return field from mongo collection as a list
	@throws(classOf[java.io.EOFException])
	def list(field: String):List[String] = {
		(List() ++ obj(field).asInstanceOf[BasicDBList]) map {
			_.asInstanceOf[Any].asInstanceOf[DBObject].as[String]("email")
		}
	}
}