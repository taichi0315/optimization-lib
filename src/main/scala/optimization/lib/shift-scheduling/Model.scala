package optimization.lib.shift.scheduling

import optimus.algebra.Const
import optimus.algebra.Constraint
import optimus.algebra.ConstraintRelation._
import optimus.algebra.AlgebraOps._
import optimus.optimization._
import optimus.optimization.enums.SolverLib
import optimus.optimization.model.{MPFloatVar, MPBinaryVar}

case class Model(
  empSeq:  Seq[String],
  termSeq: Seq[Int]
) {
  implicit val model = MPModel(SolverLib.oJSolver)

  def solve(): Unit = {
    addAttendanceNeedsConstraint
    addSmoothingAttendanceConstraint
    start()
  }

  // Variables
  val attendanceVarMap: Map[(String, Int), MPBinaryVar] =
    (
      for {
        emp  <- empSeq
        term <- termSeq
      } yield ((emp, term) -> MPBinaryVar(s"attendanceVar[${emp},${term}]"))
    ).toMap

  val attendanceTermNumVarMap: Map[String, MPFloatVar] =
    empSeq.map(emp =>
      (emp -> MPFloatVar.positive(s"attendanceTermNumVar[${emp}]"))
    ).toMap

  // Constraints
  def addAttendanceNeedsConstraint(): Unit =
    termSeq.foreach(term => add(
      Constraint(
        sum(empSeq.flatMap(emp => attendanceVarMap.get((emp, term)))),
        GE,
        Const(3)
      )
    ))

  def addSmoothingAttendanceConstraint(): Unit =
    empSeq.foreach(emp => add(
      Constraint(
        attendanceTermNumVarMap.get(emp).get,
        EQ,
        sum(termSeq.flatMap(term => attendanceVarMap.get((emp, term)))) - Const(3)
      )
    ))

  // Objective
  minimize(
    sum(
      empSeq.flatMap(emp => attendanceTermNumVarMap.get(emp).map(_ * Const(3)))
    )
  )
}
