package org.aurora

import org.scalatest._, wordspec._, matchers._
import com.raquo.laminar.api.L.{*, given}
import scala.util.Try
import com.raquo.airstream.ownership.OneTimeOwner

class FetchStreamTest extends AnyWordSpec :
  "this" should {
    "work" in {
      val result = FetchStream.get("http://localhost:8080/patientsjson")
      val subscription = result.addObserver( new Observer[String] {
        override def onError(err: Throwable): Unit = info(fail(err.getMessage()))
        override def onTry(nextValue: Try[String]): Unit = ()
        override def onNext(nextValue: String):Unit =
          import org.aurora.shared._, dto._
          import zio.json._
          nextValue.fromJson[List[Patient]].foreach( p => info(s"$p"))
          // info(nextValue)
      })(new OneTimeOwner(() => ()))


      //wait 1 second and then kill the owner.  wait for FetchStream to process
      scala.scalajs.js.timers.setTimeout(3000) {
        subscription.kill() 
      }


    


    }
  }
