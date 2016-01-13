package gui.generate

import java.io.{FileWriter, BufferedWriter, File}
import java.nio.file.{Paths, Files}
import java.util.{Date, Random}
import java.text.SimpleDateFormat

import scala.io.Source

object GenerateVotes {
  def generateStart(x: Int, votesFolder: String, numberVotes: Int) {
    x match {
      case 1 => generateSingle(votesFolder, numberVotes)
      case 2 => generateRange(votesFolder, numberVotes)
      case 3 => generateApproval(votesFolder, numberVotes)
      case _ =>
    }
  }
  def generateSingle(votesFolder: String, numberVotes: Int) {
    val lines = Source.fromFile("./src/main/resources/card examples/card1.txt", "UTF-8").getLines().toList
    for (j <- 0 until numberVotes) {
      var result = ""
      val x = randInt(7, 17)

      for (i <- lines.indices) {
        if (i != x) result += lines(i) + "\n"
        else result += lines(i).take(lines(i).length - 2) + "[X]" + "\n"
      }
      writeToFile(result, j, votesFolder)
    }
  }

  def generateRange(votesFolder: String, numberVotes: Int) {
    val lines = Source.fromFile("./src/main/resources/card examples/card2.txt", "UTF-8").getLines().toList
    for (j <- 0 until numberVotes) {
      var result = ""

      for (i <- lines.indices) {
        if (i < 7 || i > 17) result += lines(i) + "\n"
        else {
          val x = randInt(-9, 9)
          result += lines(i).take(lines(i).length - 2) + "[" + x + "]" + "\n"
        }
      }
      writeToFile(result, j, votesFolder)
    }
  }
  def generateApproval(votesFolder: String, numberVotes: Int) {
    val lines = Source.fromFile("./src/main/resources/card examples/card3.txt", "UTF-8").getLines().toList
    for (j <- 0 until numberVotes) {
      var result = ""

      for (i <- lines.indices) {
        val x = randInt(7, 17)
        if (i != x) result += lines(i) + "\n"
        else result += lines(i).take(lines(i).length - 2) + "[X]" + "\n"
      }
      writeToFile(result, j, votesFolder)
    }
  }

  def randInt(min: Int, max: Int): Int = {
    val rand: Random = new Random
    rand.nextInt((max - min) + 1) + min
  }

  def writeToFile (result: String, number: Int, votesFolder: String): Unit = {
    val fileName: String = number + "." + new SimpleDateFormat("yyyy.MM.dd.hh.mm.ss'.txt'").format(new Date)
    val file = new File(fileName)
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(result)
    bw.close()
    Files.copy(Paths.get(fileName), Paths.get(votesFolder + "/" + fileName))
    file.delete()
  }
}