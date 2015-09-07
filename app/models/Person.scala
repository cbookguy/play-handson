package models

case class Name(first: String, last: String)
case class Person(name : Name, age: Int, blood: Option[String], mynumber: Seq[Int])

