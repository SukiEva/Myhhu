package github.sukieva.hhu.data.bean

import java.math.BigInteger
import java.security.MessageDigest

data class LoginData(
    var username: String,
    var password: String,
    var captcha: String
) {
    init { // md5 加密(小写字母+数字)
        val md = MessageDigest.getInstance("MD5")
        md.update(password.toByteArray())
        this.password = BigInteger(1, md.digest()).toString(16)
    }
}
