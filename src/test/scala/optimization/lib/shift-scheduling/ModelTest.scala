package optimization.lib.shift.scheduling

import org.scalatest.funsuite.AnyFunSuite

class ModelTest extends AnyFunSuite {
  test("test1") {
    val empSeq  = Seq(
      Employee(1),
      Employee(2),
      Employee(3)
    )
    val termSeq = Seq(
      Term(1, 3),
      Term(2, 2),
      Term(3, 3),
      Term(4, 2),
      Term(5, 2)
    )

    val ssModel = Model(empSeq, termSeq)
    ssModel.solve

    ssModel.attendanceVarMap.foreach {
      case (key, variable) => println(s"${key}: ${variable.value}")
    }

    ssModel.attendanceTermNumVarMap.foreach {
      case (key, variable) => println(s"${key}: ${variable.value}")
    }

    println(ssModel.model.objectiveValue)
  }
}
