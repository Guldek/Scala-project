package gui.count

import java.io.File
import java.nio.charset.{Charset, StandardCharsets}
import java.nio.file.StandardOpenOption

import scala.collection.mutable
import scala.io.Source

object Counter {
  val probe = "./src/main/resources/card examples/card1.txt"
  val ballotsDir = "./src/main/resources/ballots"
  var candidates = makeCandidateList(probe,mutable.MutableList.empty[Candidate])

  def main(args: Array[String]) = {
    val files = findAllVotes(ballotsDir)
    for (f <- files) candidates = GetVote.chooseStrategy(f.toString, candidates)
    findWinner()
  }

  def countFiles(file: String) = {
    Option(new File(file).list).map(_.count(_.endsWith(".txt"))).getOrElse(0)
  }

  def findWinner(): Unit = {
    candidates = candidates.sortWith(_.votes > _.votes)
    createResultFile()
  }

  def createResultFile(): Unit = {
    import java.nio.file.{Files,Path,Paths}
    val utf8 = StandardCharsets.UTF_8
    val result = Paths.get("results.txt")
    if(Files.exists(result)) Files.delete(result)
    for (c <- candidates) Files.write(result,(c.showCandidate+"\n").getBytes(utf8),StandardOpenOption.CREATE, StandardOpenOption.APPEND)
    Files.write(result,("\nThe winner is: " + candidates.head.name).getBytes(utf8),StandardOpenOption.CREATE, StandardOpenOption.APPEND)
    Files.write(result,("\nNumber of valid votes: " + countFiles(ballotsDir).toString()).getBytes(utf8),StandardOpenOption.CREATE, StandardOpenOption.APPEND)
  }

  def makeCandidateList(source: String, candidates: mutable.MutableList[Candidate]): mutable.MutableList[Candidate] = {
    val lines = Source.fromFile(source, "UTF-8").getLines().toList
    for (i <- 7 until 18) {
      val name = findName(lines(i))
      val candidate = new Candidate(0, name)
      candidates += candidate
    }
    candidates
  }

  def findName (line: String): String = {
    val name =cutString(line)
    if (name.charAt(1).isDigit) return name.takeRight(name.length - 3)
    name.takeRight(name.length - 2)
  }

  def cutString (lines: String): String ={
    var spaces=0
    for (j <- lines.indices ) {
      if (lines.charAt(j) == ' ' || lines.charAt(j) == '\t') spaces+=1
      if (spaces == 2) {
        return lines.take(j)
      }
    }
    null
  }
  def findAllVotes(source: String): Array[File] = {
    new File(source).listFiles.filter(_.getName.endsWith(".txt"))
  }
}
