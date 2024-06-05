package org.aurora.app

import org.raquo.laminar.api.L.{*,given}
import org.scalajs.dom

object MainApp {
  def main(args:Array[String]):Unit = {
    val containerNode  = dom.document.queryselector("#app") //# refers to the ID name of the html element

    render(
        containerNode,
        Tutorial.staticContent
    )
  }
}


object Tutorial:
  val staticContent =
    div (
      // modifiers
      styleAttr := "color: red",
      p("Hello world!"),
      p("Hello Beans 🫘")
    )
