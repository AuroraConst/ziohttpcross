package com.axiom.shared

package object example :
  import zio.json._

  case class Person(name:String,age:Int)

  object Person :
    implicit val encoder: JsonEncoder[Person] = DeriveJsonEncoder.gen[Person]
    implicit val decoder: JsonDecoder[Person] = DeriveJsonDecoder.gen[Person]

