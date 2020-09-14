package optimization.lib.shift.scheduling

case class Term(
  id:              Term.Id,
  attendanceNeeds: Int
)

object Term {
  type Id = Long
}
