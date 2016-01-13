import org.scalatest._
import gui.VotingModel

class VotingModelSpec extends FlatSpec{
  "The Voting Model" should "always represent the same object" in {
    val model1 = VotingModel()
    val model2 = VotingModel()
    assert(model1 equals model2)
    assert(model1 == model2)
    assert(model1 eq model2)
  }

  it should "Update field system with the same parameter as given in updateSystem() method" in {
    val model = VotingModel()
    for( (i,s) <- VotingModel.systems.indices zip VotingModel.systems ){
      model.updateSystem(s)
      assert(model.system == i+1)
    }

  }
}
