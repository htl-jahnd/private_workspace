package pkgData

import java.time.format.DateTimeFormatter
import java.time.LocalDate
import java.time.LocalTime

data class Entry(
	var entryId: Int,
	var activity: Activity,
	var user: User,
	var date: LocalDate,
	var timeStart: LocalTime,
	var timeEnd: LocalTime,
	var title: String,
	var message: String
) {

}