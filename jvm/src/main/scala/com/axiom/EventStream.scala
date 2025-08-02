package com.axiom
import zio._
import zio.http._
import zio.schema.codec.JsonCodec.zioJsonBinaryCodec
// import zio.schema.codec.JsonCodec.schemaBasedBinaryCodec                                                                                                                              
// import zio.schema.codec.JsonCodec.zioJsonBinaryCodec                                                                                                                                  
import os.*
import zio.stream.ZStream
object eventstream :

  // Simple Server-Sent Events endpoint
  def events(req: Request) = 
    val sseHeaders = Headers(
      Header.ContentType(MediaType.text.`event-stream`),
      Header.CacheControl.NoCache,
      Header.Custom("Connection", "keep-alive")
    )
    
    // Create a stream that sends events every 2 seconds
    val eventStream = ZStream.fromSchedule(Schedule.spaced(2.seconds))
      .map { tick =>
        val timestamp = java.time.Instant.now()
        s"data: {\"message\": \"Hello from server!\", \"timestamp\": \"$timestamp\", \"tick\": $tick}\n\n"
      }
    
    Response(body = Body.fromStream(eventStream)).setHeaders(sseHeaders)

  def timeevents(req:Request) = 
    val sseHeaders = Headers(
      Header.ContentType(MediaType.text.`event-stream`),
      Header.CacheControl.NoCache,
      Header.Custom("Connection", "keep-alive")
    )

    val timeStream = ZStream.fromSchedule(Schedule.spaced(1.second))
      .map { _ =>
        val now = java.time.LocalDateTime.now()
        s"data: {\"time\": \"$now\"}\n\n"
      }
    
    Response(body = Body.fromStream(timeStream)).setHeaders(sseHeaders)