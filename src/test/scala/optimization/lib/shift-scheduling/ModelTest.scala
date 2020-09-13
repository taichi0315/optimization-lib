package optimization.lib.shift.scheduling

import org.scalatest.funsuite.AnyFunSuite

class ModelTest extends AnyFunSuite {
  test("test1") {
    val empSeq  = Seq("emp1", "emp2", "emp3")
    val termSeq = Seq(1, 2, 3, 4, 5)

    val ssModel = Model(empSeq, termSeq)
    ssModel.solve

    ssModel.attendanceVarMap.foreach {
      case (key, variable) => println(s"${key}: ${variable.value}")
    }
  }
}
