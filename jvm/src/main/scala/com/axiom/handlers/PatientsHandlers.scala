package com.axiom

import com.axiom._, dataimport._
import api._
import com.axiom.model.shared._, dto._
import zio.json._

import zio.http._
import zio.http.endpoint._

object PatientsHandlers :
  def patients(req:Request) = 
    Response.text(s"${importpatients.toJson}")