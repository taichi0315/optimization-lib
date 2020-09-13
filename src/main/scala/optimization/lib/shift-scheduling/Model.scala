package optimization.lib.shift.scheduling

import optimus.algebra.Const
import optimus.algebra.Constraint
import optimus.algebra.ConstraintRelation._
import optimus.algebra.AlgebraOps._
import optimus.optimization._
import optimus.optimization.enums.SolverLib
import optimus.optimization.model.{MPFloatVar, MPBinaryVar}

case class Model(
  empSeq:  Seq[Employee],
  termSeq: Seq[Term]
) {
  implicit val model = MPModel(SolverLib.oJSolver)

  def solve(): Unit = {
    addAttendanceNeedsConstraint
    addSmoothingAttendanceConstraint
    start()
  }

  // Variables
  val attendanceVarMap: Map[(Employee.Id, Term.Id), MPBinaryVar] =
    (
      for {
        emp  <- empSeq
        term <- termSeq
      } yield ((emp.id, term.id) -> MPBinaryVar(s"attendanceVar[${emp.id},${term.id}]"))
    ).toMap

  val attendanceTermNumVarMap: Map[Employee.Id, MPFloatVar] =
    empSeq.map(emp =>
      (emp.id -> MPFloatVar.positive(s"attendanceTermNumVar[${emp.id}]"))
    ).toMap

  // Constraints
  def addAttendanceNeedsConstraint(): Unit =
    termSeq.foreach(term => add(
      Constraint(
        sum(empSeq.flatMap(emp => attendanceVarMap.get((emp.id, term.id)))),
        GE,
        Const(term.attendanceNeeds)
      )
    ))

  def addSmoothingAttendanceConstraint(): Unit =
    empSeq.foreach(emp => add(
      Constraint(
        attendanceTermNumVarMap.get(emp.id).get,
        EQ,
        sum(termSeq.flatMap(term => attendanceVarMap.get((emp.id, term.id)))) - Const(3)
      )
    ))

  // Objective
  minimize(
    sum(
      empSeq.flatMap(emp => attendanceTermNumVarMap.get(emp.id).map(_ * Const(3)))
    )
  )
}
