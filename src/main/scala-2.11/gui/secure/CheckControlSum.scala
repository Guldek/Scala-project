package gui.secure
import java.io.File
import java.nio.file.{Files, Path, Paths}
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Date

import scala.io.Source

object CheckControlSum {

  def main(args: Array[String]) {
    val ballotsFolder = "./src/main/resources/ballots"
    deleteOldBallots(ballotsFolder)
    findGoodVotes(args, ballotsFolder)
  }

  def deleteOldBallots(source: String) {
    for {
      files <- Option(new File(source).listFiles)
      file <- files
    } file.delete()
  }

  def findGoodVotes(args: Array[String], ballotsFolder: String) {
    val files = findAllVotes(args(0))
    for (i <- files.indices) {
      if (checkControlSum(files(i).toString) && GetVoteSystem.cardCorrectness(files(i).toString, args(1).toInt))
        copyVote(files(i), ballotsFolder, i)
    }
  }

  def findAllVotes(source: String): Array[File] = {
    new File(source).listFiles.filter(_.getName.endsWith(".txt"))
  }

  def checkControlSum(file: String): Boolean = {
    val result = createCheckSum(file)
    val cardGoodCheckSums = Array("906bda36db7cc35c2e2a300411f3b683", "4a398ed2c9568d1b140a22410035124f", "18ba27062b5c5737a03d009ebaee0201")
    for (i <- cardGoodCheckSums.indices) {
      if (cardGoodCheckSums(i).equals(result)) return true
    }
    false
  }

  def createCheckSum(file: String): String = {
    try {
      val md: MessageDigest = MessageDigest.getInstance("MD5")
      var generateString = ""
      val lines = Source.fromFile(file, "UTF-8").getLines().toList
      for (i <- lines.indices) {
        if (i <= 5 || i > 18) generateString += lines(i)
        else generateString += lines(i).take(14).patch(0, "dh;iojfdasf", 8).reverse
      }
      val buffer: Array[Byte] = md.digest(generateString.getBytes("UTF-8"))
      getMD5Checksum(buffer)
    } catch {
      case e: Exception => null
    }
  }

  def getMD5Checksum(b: Array[Byte]): String = {
    var result: String = ""
    for (aB <- b) {
      result += Integer.toString((aB & 0xff) + 0x100, 16).substring(1)
    }
    result
  }

  def copyVote(file: File, ballotsFolder: String, i: Int) = {
    val fileName: String = i + "." + new SimpleDateFormat("yyyy.MM.dd.hh.mm.ss'.txt'").format(new Date)
    val path: Path = Paths.get(ballotsFolder + '/' + fileName)
    Files.copy(file.toPath, path)
  }
}