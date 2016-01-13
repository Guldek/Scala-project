package gui

import gui.javagui.ElectionHandlingGUI

object VotingModel{
  val systems = Array("1. First-ran-the-post", "2. Range Voting" , "3. Approval Voting")
  val singleton: VotingModel = new VotingModel(systems)

  def apply() = {
    singleton
  }
}

class VotingModel private (val systems: Array[String]){
  var path: String = null
  var system: Int = 1
  var systemAfterSecuring: Int = system
  var beingSecured: Boolean = false
  var beingCounted: Boolean = false
  var wereVotesSecured: Boolean = false
  var numberOfFiles: Int = 0

  def updateSystem(option: String): Unit = {
    for(i <- 1 to systems.length) if(option eq systems(i-1)) system = i
  }

  def secureVotes() = {
    this.synchronized {
      import secure.CheckControlSum
      beingSecured = true
      ElectionHandlingGUI.setText("Securing lasts.")
      val args = Array[String](path, system.toString)
      val t = new Thread(new Runnable {
        override def run(): Unit = {CheckControlSum.main(args)
          beingSecured = false
          wereVotesSecured = true
          ElectionHandlingGUI.setText("Securing finished.")
          }
        }
      )
      t.start()
    }
  }

  def countVotes() = {
    this.synchronized {
      import count.Counter
      beingCounted = true
      ElectionHandlingGUI.setText("Counting lasts.")
      val t = new Thread(new Runnable {
        override def run(): Unit = {Counter.main(null)
        beingCounted = false
        wereVotesSecured = false
        ElectionHandlingGUI.setText("Counting finished.")
        }
      })
      t.start()
    }
  }
}
