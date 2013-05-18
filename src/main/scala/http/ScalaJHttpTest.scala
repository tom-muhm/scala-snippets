package http

import scalaj.http.Http
import scalaj.http.HttpOptions

object ScalaJHttpTest {

  val options = HttpOptions.connTimeout(5000) ::
    HttpOptions.readTimeout(5000) ::
    HttpOptions.allowUnsafeSSL ::
    Nil
    
  val headers = ("content-type", "application/json") :: 
    ("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31") ::
    Nil

  def start(url: String) {
    val result = Http.post(url).headers(headers).options(options).asString
    println(result)
  }

}