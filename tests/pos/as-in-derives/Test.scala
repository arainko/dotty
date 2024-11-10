trait Typeclass[A] {
  def someValue: String
}

object Typeclass {
  def derived[A]: Typeclass[A] = new {
    def someValue: String = "someValue"
  }
}

case class Test(a: Int, b: Int) derives Typeclass as costam, Typeclass as costam2

object Test {
  export costam.someValue
}

@main def main = {
  println(Test.someValue)
}
