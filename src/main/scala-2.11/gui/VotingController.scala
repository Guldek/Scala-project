package gui

import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing._
import javagui._
import java.io._

object VotingController{
  def emptyBallotsDir() {
    ElectionHandlingGUI.errorMessage("Ballots directory is empty.")
  }

  def countFiles(file: String) = {
    Option(new File(file).list).map(_.count(_.endsWith(".txt"))).getOrElse(0)
  }
}

class VotingController(view: ElectionHandlingGUI,model: VotingModel) {
  var isDirectoryChosen: Boolean = false

  def control() {
    val secureListener: ActionListener = new ActionListener() {
      def actionPerformed(actionEvent: ActionEvent) {
        if (isDirectoryChosen && !model.beingSecured) {
          model.secureVotes()
        }
        else if (!isDirectoryChosen) {
          ElectionHandlingGUI.errorMessage("No directory was given.")
        }
        else if (model.beingSecured) {
          ElectionHandlingGUI.errorMessage("Votes are being secured now.")
        }
      }
    }
    view.getSecureButton.addActionListener(secureListener)
    val countListener: ActionListener = new ActionListener() {
      def actionPerformed(e: ActionEvent) {
        if (model.wereVotesSecured) {
          model.countVotes()
        }
        else if (model.beingSecured) {
          ElectionHandlingGUI.errorMessage("Securing lasts.")
        }
        else if (model.beingCounted) {
          ElectionHandlingGUI.errorMessage("Counting lasts.")
        }
        else if (!model.wereVotesSecured) {
          ElectionHandlingGUI.errorMessage("Votes hasn't been secured yet.")
        }
        else if (!isDirectoryChosen) {
          ElectionHandlingGUI.errorMessage("Directory hasn't been chosen.")
        }
      }
    }
    view.getCountButton.addActionListener(countListener)
    val findBox: ActionListener = new ActionListener() {
      def actionPerformed(e: ActionEvent) {
        model.path = view.findBox
        if (!(model.path == "-1")) {
          val files = gui.VotingController.countFiles(model.path)
          if (files == 0) gui.VotingController.emptyBallotsDir()
          else {
            model.numberOfFiles = files
            isDirectoryChosen = true
            ElectionHandlingGUI.setText("You have picked a directory. Now pick a voting system.")
          }
        }
      }
    }
    view.getDirectoryButton.addActionListener(findBox)
    val systemPicker: ActionListener = new ActionListener() {
      def actionPerformed(e: ActionEvent) {
        val cb: JComboBox[_] = view.getVotingSystems
        model.updateSystem(cb.getSelectedItem.asInstanceOf[String])
        ElectionHandlingGUI.setText("You have picked the voting system. Now secure and then count votes.")
      }
    }
    view.getVotingSystems.addActionListener(systemPicker)
  }
}
