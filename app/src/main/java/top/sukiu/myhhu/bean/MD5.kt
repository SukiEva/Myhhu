package top.sukiu.myhhu.bean

import java.math.BigInteger
import java.security.MessageDigest


class MD5 {
    /**
     * 对字符串md5加密(小写字母+数字)
     */
    public fun getMD5(str: String): String {

        val md = MessageDigest.getInstance("MD5")
        md.update(str.toByteArray())
        return BigInteger(1, md.digest()).toString(16)
    }
}

