import scala.annotation.experimental

@experimental // FIXME ERROR
enum E: // error
  case A
  case B // error

def test: Unit =
  E.A // error
  E.B // error
  val e: E = ??? // error
  ()
