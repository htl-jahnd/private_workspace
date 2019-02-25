package pkgData

import java.time.format.DateTimeFormatter
import java.time.Instant

data class Entry(val activityId: Activity, val Duration: Long, val Message: String = "") {
	var timestamp:String = DateTimeFormatter.ISO_INSTANT.format(Instant.now());
}