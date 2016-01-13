package gui.count

import scala.collection.mutable
import scala.io.Source

class RangeVoteCount extends CountStrategy{
  override def countVotes(source: String,  candidates: mutable.MutableList[Candidate]): mutable.MutableList[Candidate] = {
    var candidatesTmp = candidates
    var name=""
    val lines = Source.fromFile(source, "UTF-8").getLines().toList
    for (i <- 7 until 18) {
       val point = lines(i).takeRight(2).take(1).toInt
        name = Counter.findName(lines(i))
        candidatesTmp = CandidateListOperations.addVote(candidatesTmp, name, point)
      }
    candidatesTmp
  }
}
