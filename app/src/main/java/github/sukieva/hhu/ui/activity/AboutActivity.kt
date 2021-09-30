package github.sukieva.hhu.ui.activity

import android.widget.ImageView
import android.widget.TextView
import com.drakeet.about.*
import github.sukieva.hhu.BuildConfig
import github.sukieva.hhu.R

class AboutActivity : AbsAboutActivity() {

    override fun onCreateHeader(icon: ImageView, slogan: TextView, version: TextView) {
        icon.setImageResource(R.mipmap.ic_launcher_round)
        slogan.setText(R.string.app_name)
        version.text = String.format("Version: %s", BuildConfig.VERSION_NAME)
    }

    override fun onItemsCreated(items: MutableList<Any>) {
        items.apply {
            add(Category("What's this"))
            add(Card(getString(R.string.about_info)))
            add(Category("Developers"))
            add(Contributor(R.drawable.avatar, "SukiEva", "Developer & designer", "https://github.com/SukiEva"))
            add(Category("Open Source Licenses"))
            add(License("MultiType", "drakeet", License.APACHE_2, "https://github.com/drakeet/MultiType"))
            add(License("about-page", "drakeet", License.APACHE_2, "https://github.com/drakeet/about-page"))
        }

    }
}

