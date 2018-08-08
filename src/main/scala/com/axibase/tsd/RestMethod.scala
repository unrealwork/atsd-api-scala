package com.axibase.tsd

import com.softwaremill.sttp.{Request, Response, Uri}

import scala.util.Try

abstract class RestMethod[T](val path: String, val method: Function[Uri, Request[String, Nothing]], val clz: Class[T]) {
  def toUri(baseUrl: String): Uri
  def execute(response: Response[String]): Try[T]
}
