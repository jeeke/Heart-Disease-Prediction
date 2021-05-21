package me.gyanesh.hdp.util

import android.graphics.drawable.ColorDrawable
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import me.gyanesh.hdp.R
import me.gyanesh.hdp.HDPApp

@BindingAdapter("app:unstyled_html")
fun setUnStyledHtmlText(view: TextView, content: String?) {
    val html = (content ?: "").replace("<img.+?>".toRegex(), "")
    val text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
    view.text = text.replace('\n', ' ').trim()
}

@BindingAdapter("app:styled_html")
fun setStyledHtmlText(view: TextView, content: String?) {
    setStyledHtmlTextWithoutClick(view, content)
    view.movementMethod = LinkMovementMethod.getInstance()
}

@BindingAdapter("app:styled_html_without_click")
fun setStyledHtmlTextWithoutClick(view: TextView, content: String?) {
    val imageGetter = GlideImageGetter(view)
    val html = HtmlCompat.fromHtml(
        content ?: "",
        HtmlCompat.FROM_HTML_MODE_LEGACY,
        imageGetter,
        null
    )
    view.text = html
}

@BindingAdapter("app:image_url")
fun loadImage(view: ImageView, url: String?) {
    val d = ColorDrawable(HDPApp.getInstance().resources.getColor(R.color.color_divider))
    try {
        Glide.with(view)
            .load(url)
            .placeholder(d)
            .into(view)
    } catch (e: Exception) {
        view.setImageDrawable(d)
    }
}

@BindingAdapter("app:visibility")
fun setVisibility(view: View, v: Boolean?) {
    view.show(v == true)
}