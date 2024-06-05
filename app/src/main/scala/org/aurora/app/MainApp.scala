package org.aurora.app

import com.raquo.laminar.api.L.{*,given}
import org.scalajs.dom

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
  val staticContent =
    div (
      // modifiers
      styleAttr := "color: blue",
      p("Hello world!"),
      p("Hello Beans 🫘🫘🫘🫘🫘🫘"),
      p("Hello world!"),
      p("Hello Beans 🫘🫘🫘🫘🫘"),
      p("Hello world!"),
      p("Hello Beans 🫘🫘🫘🫘"),
      p("Hello world!"),
      p("Hello Beans 🫘🫘🫘")


    )
