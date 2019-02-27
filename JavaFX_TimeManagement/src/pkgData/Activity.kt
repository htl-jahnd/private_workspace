package pkgData

data class Activity(
	val activityId: Int,
	val name: String
) {
	override fun toString(): String = name;
}