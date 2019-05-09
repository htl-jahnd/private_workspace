package pkgData

data class Activity(
	var activityId: Int,
	var name: String
) {
	override fun toString(): String = name;
}