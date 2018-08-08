package com.axibase.tsd

import com.softwaremill.sttp._
import org.slf4j.LoggerFactory

import scala.util.Try

class Client(val config: Config) {
  private val logger = LoggerFactory.getLogger(classOf[Client])
  private val apiUrl: String = s"https://${config.server}:${config.port}/api/v1/"
  private implicit val backend: SttpBackend[Id, Nothing] = HttpURLConnectionBackend()

  def execute[T](restMethod: RestMethod[T]): Try[T] = {
    logger.info(s"$apiUrl")
    Option(apiUrl)
      .map(restMethod.toUri)
      .map(restMethod.method)
      .map(request => request.auth.basic(config.credentials.user, config.credentials.password))
      .map(request => request.send())
      .map(response => restMethod.execute(response))
  }

  private def addAuth(request: Request[String, Nothing]): Request[String, Nothing] = {
    val credentials = config.credentials
    request.auth.basic(credentials.user, credentials.password)
  }
}