import org.scalatest.FlatSpec
import gui.secure._
class SetUpStrategySpec extends FlatSpec{
  "SetUpStrategy object" should "be able to have changed the strategy after being created" in {
    val s = new SetUpStrategy(new SingleVoteCheck)
    assert(s.strategy.isInstanceOf[SingleVoteCheck])
    s.strategy = new RangeVoteCheck
    assert(s.strategy.isInstanceOf[RangeVoteCheck])
    s.strategy = new ApprovalVoteCheck
    assert(s.strategy.isInstanceOf[ApprovalVoteCheck])
  }
}
