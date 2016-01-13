package gui.count

import scala.collection.mutable

object CandidateListOperations {
  def addVote(candidates: mutable.MutableList[Candidate], name: String, point: Int): mutable.MutableList[Candidate] = {
    for (c <- candidates) if (c.name.equals(name)) c.increaseVotes(point)
    candidates
  }
}
