package gui.count

import scala.collection.mutable

class SetUpStrategy (strategy: CountStrategy) {
  def startCountVotes(source: String, candidates: mutable.MutableList[Candidate]) : mutable.MutableList[Candidate] = {
    strategy.countVotes(source, candidates)
  }
}