import scala.deriving.*

import scala.compiletime.*

// =============== Typeclass definition
trait YesNoEnum[A] {
  extension (self: A) def toBoolean: Boolean

  def apply(bool: Boolean): A
}

object YesNoEnum {

  inline def derived[A <: scala.reflect.Enum](using
    A: Mirror.SumOf[A],
    ev1: A.MirroredElemLabels <:< ("Yes", "No")
  ): YesNoEnum[A] =
    new {
      private val instances = summonInstances[A.MirroredElemTypes, A]
      private val yes = instances(0)
      private val no = instances(1)

      extension (self: A) def toBoolean: Boolean = A.ordinal(self) == 0

      def apply(bool: Boolean): A =
        if bool then yes else no
      
    }

  private inline def summonInstances[A <: Tuple, Parent <: scala.reflect.Enum]: List[Parent] =
    inline erasedValue[A] match {
      case _: (h *: t) =>
        inline valueOf[h] match {
          case parent: Parent => parent :: summonInstances[t, Parent]
        }

      case _: EmptyTuple => Nil
    }
}


// =============== usage (current) ===============

enum DecisionCurrent derives YesNoEnum {
  case Yes, No
}

object DecisionCurrent {
  export derived$YesNoEnum.{apply as fromBoolean}
}


// =============== usage (new) ===============

enum Decision derives YesNoEnum as fromBoolean  {
  case Yes, No
}

enum DecisionAlternate derives YesNoEnum as yesNoEnum {
  case Yes, No
}

object DecisionAlternate {
  export yesNoEnum.{apply as fromBoolean}
}

@main def test = {
  Decision.fromBoolean(true) // Yes
  Decision.fromBoolean(false) // No

  DecisionAlternate.fromBoolean(true) // Yes
  DecisionAlternate.fromBoolean(false) // No
}
