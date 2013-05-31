package http

import java.net.{InetSocketAddress, URL, HttpURLConnection, Proxy}
import java.io.{BufferedReader, InputStream, InputStreamReader}
import javax.net.ssl._
import javax.security.cert.X509Certificate
import java.security.SecureRandom
import javax.security.cert
import java.security
import scala.collection.parallel.ForkJoinTaskSupport

object HttpUrlConnectionTest extends App {

  val numOfRequests = 1000
  val numOfParRequests = 30

  val parList = (1 to numOfRequests).toList.par
  parList.tasksupport = new ForkJoinTaskSupport(new scala.concurrent.forkjoin.ForkJoinPool(numOfParRequests))

  System.setProperty("http.maxConnections", "100");

  val proxies = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 8080)) :: Proxy.NO_PROXY :: Nil

  val results = proxies map (proxy => {

    val start = System.nanoTime

    val sum = (parList map (i => {

      val conn = new URL("https://btc-e.com/api/2/ltc_btc/depth").openConnection(proxy).asInstanceOf[HttpURLConnection]

      conn.setRequestProperty("Connection", "Keep-Alive")
      conn.setRequestProperty("Keep-Alive", "timeout=1800")
      conn.setRequestProperty("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31")

      val start = System.nanoTime
      conn.connect()

      val in = new BufferedReader(new InputStreamReader(conn.getInputStream))
      val html = new StringBuffer
      var line: String = in.readLine
      while (line != null) {
        html.append(line)
        line = in.readLine
      }
      in.close()

      //      println(conn.getHeaderFields)

      //          println(html)

      val time = System.nanoTime - start
      //      totalTime = totalTime + time
      println((time / 1e6 + "ms"))
      //      totalTime
      time
    })).sum
    (proxy.`type`(), sum, System.nanoTime - start)
  })

  results foreach (result => {
    println(result._1 + ": average for " + numOfRequests + " requests = " + (result._2 / numOfRequests / 1e6) + "ms - total time " + (result._3 / 1e6) + "ms")
  })


}