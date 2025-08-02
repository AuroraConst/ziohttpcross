package com.axiom
import os.*
import zio.http._

object directorybrowser {
    def root = (req: Request) =>
        val devPath = os.root / "dev"
        if (os.exists(devPath) && os.isDir(devPath)) {
          val files = os.list(devPath).map(_.last).mkString("<br>")
          val htmlContent = s""" 
            <html>
              <head><title>Directory Listing: /dev</title></head>
              <meta charset="UTF-8"> <!-- Ensure proper character encoding , and allows emoji use-->
              <body>
                <h1>Directory Listing: /dev</h1>
                <p>$files</p>
              </body>
            </html>
          """
          Response.text(htmlContent)
            .addHeader(Header.ContentType(MediaType.text.html))
        } else {
          Response.badRequest( "Directory not found" ) // Return a 404 Not Found response if the directory does not exist
        }

    def browse = (subpath: String, req: Request) =>
        val targetPath = os.root / "dev" / subpath
        if (os.exists(targetPath) && os.isDir(targetPath)) {
          val files = os.list(targetPath).map { path =>
            val name = path.last
            val isDir = os.isDir(path)
            if (isDir) s"📁 <a href='/dev/browse/$subpath/$name'>$name/</a>"
            else s"📄 <a href='/dev/$subpath/$name'>$name</a>"
          }.mkString("<br>")
          val htmlContent = s""" 
            <html>
              <head><title>Directory Listing: /dev/$subpath</title></head> 
              <meta charset="UTF-8"> <!-- Ensure proper character encoding , and allows emoji use-->
              <body>
                <h1>Directory Listing: /dev/$subpath</h1>
                <p><a href='/dev/browse'>⬆️ Back to /dev</a></p>
                <p>$files</p>
              </body>
            </html>
          """
          Response.text(htmlContent)
            .addHeader(Header.ContentType(MediaType.text.html))
        } else {
          Response.badRequest("Directory not found")
        }

}