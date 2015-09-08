package formatters

import models.{Person, Name}
import play.api.libs.json.Reads._
import play.api.libs.json.{Format, JsPath, Reads}
import play.api.libs.functional.syntax._

object PersonFormatter {

  implicit val nameFormatter: Format[Name] = (
    (JsPath \ "first").format[String](minLength[String](1)) and
      (JsPath \ "last").format[String](minLength[String](1))
    )(Name.apply _, unlift(Name.unapply _))

  implicit val personFormatter: Format[Person] = (
    (JsPath \ "name").format[Name] and
      (JsPath \ "age").format[Int](min(0) keepAnd max(100)) and
      (JsPath \ "blood").formatNullable[String] and
      (JsPath \ "mynumber").format[Seq[Int]]
    )(Person.apply _, unlift(Person.unapply _))
}
