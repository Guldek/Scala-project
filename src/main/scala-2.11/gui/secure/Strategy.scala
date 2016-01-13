package gui.secure

trait Strategy {
  def checkCard(source: String): Boolean
}

