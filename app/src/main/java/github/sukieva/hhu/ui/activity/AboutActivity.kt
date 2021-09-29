package github.sukieva.hhu.ui.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.drakeet.about.*
import github.sukieva.hhu.BuildConfig
import github.sukieva.hhu.R

class AboutActivity : AbsAboutActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateHeader(icon: ImageView, slogan: TextView, version: TextView) {
        icon.setImageResource(R.mipmap.ic_launcher_round)
        slogan.text = R.string.app_name.toString()
        version.text = String.format("Version: %s", BuildConfig.VERSION_NAME)
    }

    override fun onItemsCreated(items: MutableList<Any>) {
        items.apply {
            add(Category("What's this"))
            add(Card(R.string.about_info.toString()))
            add(Category("Developers"))
            add(Contributor(R.drawable.ic_launcher_foreground, "SukiEva", "Developer & designer", "https://github.com/SukiEva"))
            add(Category("Open Source Licenses"))
            add(License("MultiType", "drakeet", License.APACHE_2, "https://github.com/drakeet/MultiType"))
            add(License("about-page", "drakeet", License.APACHE_2, "https://github.com/drakeet/about-page"))
        }

    }
}

