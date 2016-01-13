import gui.VotingController
import org.scalatest.FlatSpec

class VotingControllerSpec extends FlatSpec{
  "Object VotingController" should "not find any txt file in src/main/java" in {
    assert(VotingController.countFiles("./src/main/java") == 0)
  }

  it should "also find 3 files in ./src/main/resources/card examples" in {
    assert(VotingController.countFiles("./src/main/resources/card examples")==3)
  }
}
