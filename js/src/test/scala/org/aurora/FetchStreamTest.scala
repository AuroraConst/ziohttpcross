package org.aurora

import org.scalatest._, wordspec._, matchers._
import com.raquo.laminar.api.L.{*, given}
import scala.util.Try
import com.raquo.airstream.ownership.OneTimeOwner

class FetchStreamTest extends AnyWordSpec :
  "this" should {
    "work" in {
      val  resultString = Var[String]("")
      val result = FetchStream.get("http://localhost:8080/patientsjson")
      val subscription = result.addObserver( new Observer[String] {
        override def onError(err: Throwable): Unit = ()
        override def onTry(nextValue: Try[String]): Unit = ()
        override def onNext(nextValue: String):Unit =
          info(nextValue)
      })(new OneTimeOwner(() => ()))


      //wait 1 second and then kill the owner.  wait for FetchStream to process
      scala.scalajs.js.timers.setTimeout(1000) {
        subscription.kill() 
      }


    


    }
  }
