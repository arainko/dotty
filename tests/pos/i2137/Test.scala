trait Typeclass[A]

object Typeclass {
  def derived[A]: Typeclass[A] = new {}
}

case class Test(a: Int, b: Int) derives Typeclass as costam, Typeclass as costam2

object Test {
  val aaaa: Typeclass[Test] = costam
}
