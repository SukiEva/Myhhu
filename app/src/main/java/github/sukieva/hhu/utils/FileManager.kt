package github.sukieva.hhu.utils

import android.content.Context
import github.sukieva.hhu.MyApp
import java.io.*


object FileManager {

    fun save(text: String) {
        try {
            val output = MyApp.context.openFileOutput("log", Context.MODE_PRIVATE)
            val writer = BufferedWriter(OutputStreamWriter(output))
            writer.use {
                it.write(text)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun load(): String {
        val content = StringBuilder()
        try {
            val input = MyApp.context.openFileInput("log")
            val reader = BufferedReader(InputStreamReader(input))
            reader.use {
                reader.forEachLine {
                    content.append(it)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return content.toString()
    }

}