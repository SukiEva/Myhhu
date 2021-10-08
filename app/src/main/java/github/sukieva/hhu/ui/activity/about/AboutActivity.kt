package github.sukieva.hhu.ui.activity.about

import android.widget.ImageView
import android.widget.TextView
import com.drakeet.about.*
import github.sukieva.hhu.BuildConfig
import github.sukieva.hhu.R

class AboutActivity : AbsAboutActivity() {

    override fun onCreateHeader(icon: ImageView, slogan: TextView, version: TextView) {
        icon.setImageResource(R.drawable.ic_launcher)
        slogan.setText(R.string.app_name)
        version.text = String.format("Version: %s", BuildConfig.VERSION_NAME)
    }

    override fun onItemsCreated(items: MutableList<Any>) {
        items.apply {
            add(Category("What's this"))
            add(Card(getString(R.string.about_info)))
            add(Category("Developers"))
            add(Contributor(R.drawable.avatar, "SukiEva", "Developer & designer", "https://github.com/SukiEva"))
            add(
                Contributor(
                    R.drawable.ic_github,
                    "Source Code",
                    "https://github.com/SukiEva/Myhhu",
                    "https://github.com/SukiEva/Myhhu"
                )
            )
            add(Category("Open Source Licenses"))
            add(
                License(
                    "kotlin",
                    "JetBrains",
                    License.APACHE_2,
                    "https://github.com/JetBrains/kotlin"
                )
            )
            add(
                License(
                    "AndroidX",
                    "Google",
                    License.APACHE_2,
                    "https://source.google.com"
                )
            )
            add(
                License(
                    "Android Jetpack",
                    "Google",
                    License.APACHE_2,
                    "https://source.google.com"
                )
            )
            add(
                License(
                    "Material-components-android",
                    "Google",
                    License.APACHE_2,
                    "https://github.com/material-components/material-components-android"
                )
            )
            add(
                License(
                    "Accompanist",
                    "Google",
                    License.APACHE_2,
                    "https://github.com/google/accompanist"
                )
            )
            add(
                License(
                    "OkHttp",
                    "Square",
                    License.APACHE_2,
                    "https://github.com/square/okhttp"
                )
            )
            add(
                License(
                    "Retrofit",
                    "Square",
                    License.APACHE_2,
                    "https://github.com/square/retrofit"
                )
            )
            add(
                License(
                    "Toasty",
                    "GrenderG",
                    License.GPL_V3,
                    "https://github.com/GrenderG/Toasty"
                )
            )
            add(
                License(
                    "Jsoup",
                    "jhy",
                    License.MIT,
                    "https://github.com/jhy/jsoup"
                )
            )
            add(
                License(
                    "MultiType",
                    "drakeet",
                    License.APACHE_2,
                    "https://github.com/drakeet/MultiType"
                )
            )
            add(
                License(
                    "About-page",
                    "drakeet",
                    License.APACHE_2,
                    "https://github.com/drakeet/about-page"
                )
            )
        }

    }
}

