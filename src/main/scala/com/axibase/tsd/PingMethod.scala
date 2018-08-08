package com.axibase.tsd

import com.axibase.tsd.exceptions.UnauthorizedException
import com.softwaremill.sttp.{StatusCodes, _}
import org.slf4j.LoggerFactory

import scala.util.{Failure, Try}

object PingMethod extends RestMethod[Boolean](path = "ping", method = sttp.get, clz = classOf[Boolean]) {
  private val log = LoggerFactory.getLogger(PingMethod.getClass)

  override def toUri(baseUrl: String): Uri = {
    uri"$baseUrl$path"
  }

  override def execute(response: Response[String]): Try[Boolean] = {
    log.debug(s"$response")
    if (StatusCodes.isSuccess(response.code)) {
      Try(true)
    } else {
        if (StatusCodes.isClientError(response.code)) {
          response.code match {
            case StatusCodes.Unauthorized: Failure(new UnauthorizedException);
          }
    }
  }
}
