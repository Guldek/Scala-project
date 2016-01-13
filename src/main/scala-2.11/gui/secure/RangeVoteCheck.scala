package gui.secure
import scala.io.Source

class RangeVoteCheck extends Strategy {
  override def checkCard(source: String): Boolean = {
    val lines = Source.fromFile(source, "UTF-8").getLines().toList
    if(lines.length <= 18) return false
    for (i <- 7 until 18) {
      var tmp = lines(i).takeRight(3).take(1)
      if (!tmp.equals("[") && !tmp.equals("-")) return false
      if (lines(i).takeRight(3).charAt(1).isLetter) return false
      tmp = ""
    }
    true
  }
}
