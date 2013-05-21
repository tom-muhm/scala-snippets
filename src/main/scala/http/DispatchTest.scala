package http

import dispatch._, Defaults._

object DispatchTest {

  def start(page: String) = {
    val svc = url(page)
    val country = Http(svc)
    val headers = country().getHeaders()
    //    val c = country()
    //    println(country)
    println(headers)

  }

}