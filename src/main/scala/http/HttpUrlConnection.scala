package http

import java.net.URL
import java.net.HttpURLConnection
import java.io.{BufferedReader, InputStream, InputStreamReader}

object HttpUrlConnectionTest extends App {

  1 to 100 foreach (i => {

    val url = new URL("https://btc-e.com/api/2/ltc_btc/depth")

    val conn: HttpURLConnection = url.openConnection().asInstanceOf[HttpURLConnection]
    conn.setRequestProperty("Connection", "Keep-Alive")
    conn.setRequestProperty("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31")

    val start = System.currentTimeMillis()
    conn.connect()

    val in = new BufferedReader(new InputStreamReader(conn.getInputStream))
    val html = new StringBuffer
    var line: String = in.readLine
    while (line != null) {
      html.append(line)
      line = in.readLine
    }
    in.close()
    conn.disconnect()

    println(System.currentTimeMillis() - start)

  })

}