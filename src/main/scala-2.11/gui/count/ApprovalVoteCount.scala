package gui.count
import scala.collection.mutable
import scala.io.Source

class ApprovalVoteCount extends CountStrategy{
  override def countVotes(source: String,  candidates: mutable.MutableList[Candidate]): mutable.MutableList[Candidate] = {
    var candidatesTmp = candidates
    var name=""
      val lines = Source.fromFile(source, "UTF-8").getLines().toList
      for (i <- 7 until 18) {
        if (lines(i).takeRight(2).take(1).equalsIgnoreCase("X")) {
          name = Counter.findName(lines(i))
          candidatesTmp = CandidateListOperations.addVote(candidatesTmp, name, 1)
        }
      }
    candidatesTmp
  }
}
