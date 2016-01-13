package gui.count

class Candidate (var votes: Int, val name:String) {

  def this (name: String) {this (0, name)}

  def increaseVotes (points: Int) {
    votes += points
  }
  def showCandidate: String = {
    name + " " + votes
  }

}
