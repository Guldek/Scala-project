package gui.count

import scala.collection.mutable
import scala.io.Source

object GetVote {
  def chooseStrategy(source: String, candidates: mutable.MutableList[Candidate]):  mutable.MutableList[Candidate] = {
    getSystem (source) match {
        case 1 =>
          val result: SetUpStrategy = new SetUpStrategy (new SingleVoteCount())
          return result.startCountVotes(source, candidates)
        case 2 =>
          val result: SetUpStrategy = new SetUpStrategy (new RangeVoteCount())
          return result.startCountVotes(source, candidates)
        case 3 =>
          val result: SetUpStrategy = new SetUpStrategy (new ApprovalVoteCount ())
          return result.startCountVotes(source, candidates)
        case _ => return null
      }
    null
  }
  def getSystem(source: String): Int = {
    val lines = Source.fromFile(source, "UTF-8").getLines().take(1).mkString
    lines.takeRight(1).toInt
  }
}
