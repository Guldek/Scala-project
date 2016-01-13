import gui._

object Main{
  def main(args: Array[String]) {
    import javax.swing.SwingUtilities
    SwingUtilities.invokeLater(new Runnable() {
      def run {
        val model: VotingModel = VotingModel()
        val view: gui.javagui.ElectionHandlingGUI = new javagui.ElectionHandlingGUI("Election")
        val controller: VotingController = new VotingController(view,model)
        javagui.ElectionHandlingGUI.setText("Welcome to the VoteCounter 2.0. Pick a directory.")
        controller.control()
      }
    })
  }
}
