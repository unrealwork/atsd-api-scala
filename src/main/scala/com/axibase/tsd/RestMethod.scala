package com.axibase.tsd

import com.axibase.tsd.exceptions.UnauthorizedException
import com.softwaremill.sttp.{Request, Response, StatusCodes, Uri}
import org.slf4j.LoggerFactory

import scala.util.{Failure, Try}

abstract class RestMethod[T](val path: String, val method: Function[Uri, Request[String, Nothing]]) {
  private val log = LoggerFactory.getLogger(classOf[RestMethod[T]])

  def toUri(baseUrl: String): Uri

  def transform(response: Response[String]): T

  def extract(response: Response[String]): Try[T] = {
    log.debug(s"$response")
    if (StatusCodes.isSuccess(response.code)) {
      Try(response).map(this.transform)
    } else {
      response.code match {
        case StatusCodes.Unauthorized => Failure(new UnauthorizedException())
        case _ => Failure(new Exception())
      }
    }
  }
}
