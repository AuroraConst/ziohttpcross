package com.axiom

import org.scalatest._
import wordspec._
import matchers._



class FirstTestJvm extends AnyWordSpec with should.Matchers{
  "this" should {
    "work" in {
      true should be(true)
    }
  }
}
