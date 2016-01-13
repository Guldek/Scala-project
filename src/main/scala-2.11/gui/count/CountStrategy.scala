package gui.count

import scala.collection.mutable

trait CountStrategy {
    def countVotes(source: String,  candidates: mutable.MutableList[Candidate]): mutable.MutableList[Candidate]
}
