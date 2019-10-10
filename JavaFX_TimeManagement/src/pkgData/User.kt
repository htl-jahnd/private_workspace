package pkgData

data class User(var username: String, var password: String, var salt: String, var isAdmin: Boolean) {
	override fun toString(): String = username;
}