package http

import dispatch._, Defaults._

object DispatchTest {

  def start(page: String) = {
    val svc = url(page)
    val country = Http(svc OK as.String)
    val c = country()
    println(c)
  }

}