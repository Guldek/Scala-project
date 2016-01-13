package gui.secure

class SetUpStrategy(var strategy: Strategy) {
  def startCheckCard(source: String): Boolean = {
    strategy.checkCard(source)
  }
}