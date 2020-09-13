package optimization.lib.shift.scheduling

import optimus.algebra.Const
import optimus.algebra.Constraint
import optimus.algebra.ConstraintRelation._
import optimus.algebra.AlgebraOps._
import optimus.optimization._
import optimus.optimization.enums.SolverLib
import optimus.optimization.model.MPBinaryVar

case class Model(
  empSeq:  Seq[String],
  termSeq: Seq[Int]
) {
  implicit val model = MPModel(SolverLib.oJSolver)

  def solve(): Unit = {
    addStaffingNeedsConstraint
    start()
    println(model.getStatus)
  }

  // Variables
  val attendanceVarMap: Map[(String, Int), MPBinaryVar] =
    ( for {
        emp  <- empSeq
        term <- termSeq
      } yield ((emp, term) -> MPBinaryVar(s"attendanceVar[${emp},${term}]"))
    ).toMap

  def addStaffingNeedsConstraint(): Unit =
    termSeq.foreach(term => add(
      Constraint(
        sum(empSeq.flatMap(emp => attendanceVarMap.get((emp, term)))),
        GE,
        Const(4)
      )
    ))
}
