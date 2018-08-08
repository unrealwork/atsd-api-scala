package com.axibase.tsd

import com.softwaremill.sttp._

object PingMethod extends RestMethod[Boolean](path = "ping", method = sttp.get) {
  override def toUri(baseUrl: String): Uri = {
    uri"$baseUrl$path"
  }

  override def transform(response: Response[String]): Boolean = {
    true
  }
}
