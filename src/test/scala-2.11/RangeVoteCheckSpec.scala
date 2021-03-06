import gui.secure.{RangeVoteCheck}
import org.scalatest.FlatSpec

class RangeVoteCheckSpec extends FlatSpec {
  "RangeVoteCheck" should "return false while called checkCard with proper vote but without crossed field" in {
    val s = new RangeVoteCheck()
    assert(!s.checkCard("./src/main/resources/card examples/card2.txt"))
  }

  it should "also return false while called checkCard with address to empty txt file" in {
    val s = new RangeVoteCheck()
    assert(!s.checkCard("./src/test/resources/empty.txt"))
  }

  it should "also return true while called checkCard with address to filled and proper vote" in {
    val s = new RangeVoteCheck()
    assert(s.checkCard("./src/test/resources/card2.txt"))
  }
}
