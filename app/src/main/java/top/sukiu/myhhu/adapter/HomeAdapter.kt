package top.sukiu.myhhu.adapter


import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import top.sukiu.myhhu.R
import top.sukiu.myhhu.bean.HomeEntity


class HomeAdapter(data: MutableList<HomeEntity>) :
    BaseSectionQuickAdapter<HomeEntity, BaseViewHolder>(R.layout.def_section_head, R.layout.home_item_view, data) {

    override fun convert(holder: BaseViewHolder, item: HomeEntity) {

        holder.setText(R.id.text, item.name)
        holder.setImageResource(R.id.icon, item.imageResource)
    }

    override fun convertHeader(helper: BaseViewHolder, item: HomeEntity) {
        //helper.setGone(R.id.show_grade, true)
        helper.setText(R.id.header, item.headerTitle)
    }
}