package org.aurora.laminar

import org.scalatest._, wordspec._, matchers._
import com.raquo.laminar.api.L.{*, given}
import com.raquo.airstream.ownership.OneTimeOwner
import zio.json._
import org.aurora.shared._, dto._


class FetchStreamTest extends FixtureAnyWordSpec  :
  case class FixtureParam(fetchStream: EventStream[String],resultstream: EventStream[Option[List[Patient]]] ) 
  override def withFixture(test: OneArgTest) = {
    val fetchstream = FetchStream.get("http://localhost:8080/patientsjson")
    val resultstream = fetchstream.map(s => s.fromJson[List[Patient]]).mapToOption
    info("now wait for the timer *********************")

    
    
    val testParam = FixtureParam(fetchstream,resultstream)
      
    try {
      withFixture(test.toNoArgTest(testParam))

    }
    finally {
    }
  }

  "this" should {
    "work" in { testParam => 
      info("here is the testing part!!!!")
      import com.raquo.airstream.ownership.ManualOwner 
      val owner:ManualOwner = new ManualOwner() {
        // override def killSubscriptions(): Unit = super.killSubscriptions()
      }
      val subscription = testParam
        .resultstream.map(x => x.getOrElse(Nil))
        .foreach(x => x.take(1).foreach(x => info(s"result is $x")))(owner)

      val resultVar = Var[List[Patient]]  (Nil)
      resultVar.signal.foreach(x => info(s"var!!!!! $x"))(owner)
      testParam.resultstream.map(x => x.getOrElse(Nil)) --> resultVar.writer

      val tempVar : Var[String] = Var[String]("hello")
      tempVar.signal.foreach(x => s"tempVar: $x")(owner)
      tempVar.set("a")
      tempVar.set("b")
      tempVar.set("c")
    


      
      
      scala.scalajs.js.timers.setTimeout(4000) {
        owner.killSubscriptions()
        info("timer is up")
      }

      // val result = Var[String]("")
      // testParam.fetchStream --> result.writer
      
      // result.signal.map(x => info(s"$x"))


 

    }

  }
