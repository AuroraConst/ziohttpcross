package org.aurora.app

import com.raquo.laminar.api.L.{*,given}
import org.scalajs.dom
import scala.util.Try
import com.raquo.airstream.ownership.OneTimeOwner

object MainApp {
  def main(args:Array[String]):Unit = {
    val containerNode  = dom.document.querySelector("#app") //# refers to the ID name of the html element

    render(
        containerNode,
        Tutorial.staticContent
    )
  }
}


object Tutorial:
  val ticks = EventStream.periodic(1000)  
  
  val staticContent =
    div (
      // modifiers
      styleAttr := "color: blue",
      p("Hello world!"),
      p("Hello Beans 🫘🫘🫘🫘"),
      p("Hello world!"),
      p("Hello Beans 🫘🫘"),
      div(span("Time updated: "),
      child <-- ticks.map(_.toString()))
    )
    
    // timeupdated


  val timeupdated = 
    div (
      span("Time since updated:"),
      child <-- ticks.map(_.toString())
    )




