package top.sukiu.myhhu.util

import java.math.BigInteger
import java.security.MessageDigest

/**
 * 对字符串md5加密(小写字母+数字)
 */
fun getMD5(str: String): String {

    val md = MessageDigest.getInstance("MD5")
    md.update(str.toByteArray())
    return BigInteger(1, md.digest()).toString(16)
}