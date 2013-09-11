package chatclient.store

import com.mongodb.casbah.Imports._

class As(obj: DBObject) {
	def number(field: String):Option[Int] = obj.getAs[Int](field)

	def string(field: String):Option[String] = obj.getAs[String](field)

	@throws(classOf[java.io.EOFException])
	def list(field: String):List[String] = {
		(List() ++ obj(field).asInstanceOf[BasicDBList]) map {
			_.asInstanceOf[Any].asInstanceOf[DBObject].as[String]("email")
		}
	}
}