import http.DispatchTest

object Main {
  def main(args: Array[String]) {
    println("starting dispatch test")
    
    1 to 100 foreach (i => {
      time("dispatch " + i)(DispatchTest.start("http://api.hostip.info/country.php"));
    })
  }

  def time[A](name: String)(f: => A) = {
    val s = System.nanoTime
    val ret = f
    println(name + " - time: " + (System.nanoTime - s) / 1e6 + "ms")
    ret
  }
}
