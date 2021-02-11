package top.sukiu.myhhu.bean

import com.chad.library.adapter.base.entity.SectionEntity

data class HomeEntity(
    val name: String = "",
    val activity: Class<*>? = null,
    val imageResource: Int = 0,
    val headerTitle: String = ""

) : SectionEntity {
    override val isHeader: Boolean
        get() = headerTitle.isNotBlank()
}