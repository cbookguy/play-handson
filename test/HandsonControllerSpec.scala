import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner._
import org.specs2.specification.BeforeEach
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._

/**
 * Created by k-urano on 2015/09/04.
 */
@RunWith(classOf[JUnitRunner])
class HandsonControllerSpec extends Specification with BeforeEach{

  val endpoint = "/v2/normal"

  "HandsonController#normal respose is name.first" in new WithApplication {

    val json = Json.obj(
      "name" -> Json.obj(
        "first" -> "foo",
        "last" -> "bar"
      ),
      "age" -> 37,
      "blood" -> "O",
      "mynumber" -> Json.arr(1, 2, 3)
    )

    val Some(result) = route(FakeRequest(POST, endpoint, FakeHeaders(), json))
    status(result) must equalTo(OK)
    contentType(result) must beSome.which(_ == "application/json")
    contentAsString(result) must contain("foo")
  }

  "HandsonController#normal request age max value is 100" in new WithApplication {

    val json = Json.obj(
      "name" -> Json.obj(
        "first" -> "foo",
        "last" -> "bar"
      ),
      "age" -> 101,
      "mynumber" -> Json.arr(1, 2, 3)
    )

    val Some(result) = route(FakeRequest(POST, endpoint, FakeHeaders(), json))
    status(result) must equalTo(BAD_REQUEST)
    contentAsString(result) must contain("error.max")
  }

  "HandsonController#normal request blood value's minLength is 1" in new WithApplication {

    val json = Json.obj(
      "name" -> Json.obj(
        "first" -> "foo",
        "last" -> "bar"
      ),
      "age" -> 99,
      "blood" -> "",
      "mynumber" -> Json.arr(1, 2, 3)
    )

    val Some(result) = route(FakeRequest(POST, endpoint, FakeHeaders(), json))
    status(result) must equalTo(BAD_REQUEST)
    contentAsString(result) must contain("error.minLength")
  }

  "HandsonController#normal request mynumber is array" in new WithApplication {

    val json = Json.obj(
      "name" -> Json.obj(
        "first" -> "foo",
        "last" -> "bar"
      ),
      "age" -> 99,
      "blood" -> "O",
      "mynumber" -> 777
    )

    val Some(result) = route(FakeRequest(POST, endpoint, FakeHeaders(), json))
    status(result) must equalTo(BAD_REQUEST)
    contentAsString(result) must contain("error.expected.jsarray")
  }

  override protected def before: Any = {
    println(">>> (^_^)///")
  }
}
