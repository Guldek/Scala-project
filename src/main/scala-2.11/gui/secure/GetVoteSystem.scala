package gui.secure
import scala.io.Source

object GetVoteSystem {

  def getSystem(source: String): Int = {
    val lines = Source.fromFile(source, "UTF-8").getLines().take(1).mkString
    lines.takeRight(1).toInt
  }

  def cardCorrectness(source: String, system: Int): Boolean = {
    if (system == getSystem(source))
      system match {
        case 1 =>
          val result: SetUpStrategy = new SetUpStrategy(new SingleVoteCheck())
          return result.startCheckCard(source)
        case 2 =>
          val result: SetUpStrategy = new SetUpStrategy(new RangeVoteCheck())
          return result.startCheckCard(source)
        case 3 =>
          val result: SetUpStrategy = new SetUpStrategy(new ApprovalVoteCheck())
          return result.startCheckCard(source)
        case _ => return false
      }
    false
  }
}