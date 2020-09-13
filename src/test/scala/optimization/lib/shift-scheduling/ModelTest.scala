package optimization.lib.shift.scheduling

import org.scalatest.funsuite.AnyFunSuite

class ModelTest extends AnyFunSuite {
  test("test1") {
    val empSeq  = Seq(
      Employee(id = 1),
      Employee(id = 2),
      Employee(id = 3)
    )
    val termSeq = Seq(
      Term(id = 1, attendanceNeeds = 2),
      Term(id = 2, attendanceNeeds = 2),
      Term(id = 3, attendanceNeeds = 2),
      Term(id = 4, attendanceNeeds = 2),
      Term(id = 5, attendanceNeeds = 2)
    )

    val leaveSeq = Seq(
      Leave(eid = 1, tid = 2),
      Leave(eid = 1, tid = 3),
    ) 

    val ssModel = Model(empSeq, termSeq, leaveSeq)
    ssModel.solve

    ssModel.attendanceVarMap.foreach {
      case (key, variable) => println(s"${key}: ${variable.value}")
    }

    ssModel.attendanceTermNumVarMap.foreach {
      case (key, variable) => println(s"${key}: ${variable.value}")
    }
  }
}
