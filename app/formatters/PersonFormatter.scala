package formatters

import models.{Person, Name}
import play.api.libs.json.Reads._
import play.api.libs.json.{JsPath, Reads}
import play.api.libs.functional.syntax._

/**
 * Created by k-urano on 2015/09/07.
 */
object PersonFormatter {

  implicit val nameFormatter: Reads[Name] = (
    (JsPath \ "first").read[String](minLength[String](1)) and
      (JsPath \ "last").read[String](minLength[String](1))
    )(Name.apply _)

  implicit val personFormatter: Reads[Person] = (
    (JsPath \ "name").read[Name] and
      (JsPath \ "age").read[Int](min(0) keepAnd max(100)) and
      (JsPath \ "blood").readNullable[String](minLength(1)) and
      (JsPath \ "mynumber").read[Seq[Int]]
    )(Person.apply _)
}
