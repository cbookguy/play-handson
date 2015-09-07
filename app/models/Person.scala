package models

/**
 * Created by k-urano on 2015/09/04.
 */
case class Name(first: String, last: String)
case class Person(name : Name, age: Int, blood: Option[String], mynumber: Seq[Int])

