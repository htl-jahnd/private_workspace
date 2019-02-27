package pkgData

import java.time.format.DateTimeFormatter
import java.time.Instant
import java.sql.Timestamp

data class Entry(
	var entryId:Int,
	var activity: Activity,
	var user: User,
	var timestampStart: Timestamp,
	var timestampEnd: Timestamp,
	var title:String,
	var message: String = ""
) {

}