trait Example[F[+x]]

class Invariant[A]
class Contravariant[-A]
class Covariant[+A]

object test {
  new Example[Contravariant] {}
}
