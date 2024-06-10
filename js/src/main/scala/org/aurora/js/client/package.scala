package org.aurora.js

package object client :
  import com.raquo.laminar.api.L.{*, given}
  import com.raquo.airstream.ownership.OneTimeOwner

  import zio.json._
  import org.aurora.shared._, dto._


  def patients = 
    FetchStream.get("http://localhost:8080/patientsjson").
      map(s => s.fromJson[List[Patient]]).mapToOption

