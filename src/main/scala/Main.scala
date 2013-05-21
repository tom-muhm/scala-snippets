import http._

object Main {
  def main(args: Array[String]) {


    val requests = 10
    
    val dispatch = false
    val scalajHttp = true

    if (dispatch) {
      println("starting dispatch test")
      1 to requests foreach (i => {
        time("dispatch " + i)(DispatchTest.start("https://btc-e.com/api/2/ltc_btc/depth"));
      })
    }

    if (scalajHttp) {
      println("starting scalajTest test")
      1 to requests foreach (i => {
        time("scalajHttp " + i)(ScalaJHttpTest.start("https://btc-e.com/api/2/ltc_btc/depth"));
      })
    }

  }

  def time[A](name: String)(f: => A) = {
    val s = System.nanoTime
    val ret = f
    println(name + " - time: " + (System.nanoTime - s) / 1e6 + "ms")
    ret
  }
}
