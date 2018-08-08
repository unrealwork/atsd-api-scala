package com.axibase.tsd

import org.slf4j.LoggerFactory

object Hello extends App {
    private val log = LoggerFactory.getLogger(this.getClass)
    val credentials = new Credentials("igor.shmagrinsky", "newLife2018_")
    val config = new Config("nur.axibase.com", 443, credentials)
    val client = new Client(config)
    val res =client.execute(PingMethod)
      .filter(isOk => isOk)
      .map(_ => "ok")
      .getOrElse("error")
    log.info("Server status is {}", res)
}
