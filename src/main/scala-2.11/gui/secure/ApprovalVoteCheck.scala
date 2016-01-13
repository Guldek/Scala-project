package gui.secure
import scala.io.Source

class ApprovalVoteCheck extends Strategy {
  override def checkCard(source: String): Boolean = {
    var vote = false
    val lines = Source.fromFile(source, "UTF-8").getLines().toList
    if(lines.length <= 18) return false
    for (i <- 7 until 18) {
      if (lines(i).takeRight(2).charAt(0).isDigit) return false
      if (lines(i).takeRight(2).take(1).equalsIgnoreCase("X"))
        if (!vote) vote = true
    }
    vote
  }
}
