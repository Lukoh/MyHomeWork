/*
 * Copyright (C) 2021 Lukoh Nam, goForer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package com.goforer.base.extension

import android.animation.AnimatorListenerAdapter
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.SystemClock
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.goforer.base.view.widget.AspectRatioImageView
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

const val RECYCLER_VIEW_CACHE_SIZE = 20

const val CROSS_FADE_DURATION = 350

inline fun <reified T : ViewGroup.LayoutParams> View.layoutParams(block: T.() -> Unit) {
    if (layoutParams is T) block(layoutParams as T)
}

fun Window.setSystemBarTextWhite() {
    val view = findViewById<View>(android.R.id.content)
    val flags = view.systemUiVisibility
    view.systemUiVisibility = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
}

fun Window.setSystemBarTextDark() {
    val view = findViewById<View>(android.R.id.content)
    val flags = view.systemUiVisibility
    view.systemUiVisibility = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
}

fun View.show(): View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }

    return this
}

/**
 * Hide the view. (visibility = View.INVISIBLE)
 */
fun View.hide(): View {
    if (visibility != View.INVISIBLE) {
        visibility = View.INVISIBLE
    }

    return this
}

/**
 * Remove the view (visibility = View.GONE)
 */
fun View.gone(): View {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }

    return this
}

fun View.upShow(duration: Long = 300) {
    show()
    translationY = height.toFloat()
    animate()
        .setDuration(duration)
        .translationY(0f)
        .setListener(object : AnimatorListenerAdapter() {
        })
        .start()
}

/**
 * Extension method to show a keyboard for View.
 */
fun View.showKeyboard() {
    if (this.requestFocus()) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, 0)
    }
}

fun View.margin(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null) {
    layoutParams<ViewGroup.MarginLayoutParams> {
        left?.let { leftMargin = it }
        top?.let { topMargin = it }
        right?.let { rightMargin = it }
        bottom?.let { bottomMargin = it }
    }
}

/**
 * Try to hide the keyboard and returns whether it worked
 * https://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard
 */
fun View.hideKeyboard(): Boolean {
    runCatching {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }.onFailure { e ->
        e.printStackTrace()
    }

    this.clearFocus()

    return false
}

class ClickListener(private val interval: Long, private val onCLick: (View) -> Unit) :
    OnClickListener {
    private var lastTimeClicked: Long = 0

    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked > interval) {
            lastTimeClicked = SystemClock.elapsedRealtime()
            onCLick(v)
        }
    }
}

fun View.setSecureOnClickListener(interval: Long = 500, onClick: (View) -> Unit) {
    val clickListener = ClickListener(interval = interval) {
        onClick(it)
    }
    setOnClickListener(clickListener)
}

fun AppCompatActivity.setupActionBar(@IdRes toolbarId: Int, action: ActionBar.() -> Unit) {
    setSupportActionBar(findViewById(toolbarId))
    supportActionBar?.run {
        action()
    }
}

fun AppBarLayout.getCollapsedRatio(verticalOffset: Int): Float {
    return abs(verticalOffset.toFloat()) / this.totalScrollRange.toFloat()
}

fun AppBarLayout.getCollapsedRatioWithReference(verticalOffset: Int): Float {
    val collapsingPixelOffset = this.totalScrollRange.toFloat() / 2f
    return (abs(verticalOffset.toFloat()) - collapsingPixelOffset) / (this.totalScrollRange.toFloat() - collapsingPixelOffset)
}

fun AspectRatioImageView.setAspectRatio(width: Int?, height: Int?) {
    if (width != null && height != null) {
        aspectRatio = height.toDouble() / width.toDouble()
    }
}

fun AspectRatioImageView.loadPhotoUrl(
    url: String,
    colorInt: Int? = null,
    colorString: String? = null,
    requestListener: RequestListener<Drawable>? = null
) {
    colorInt?.let { background = ColorDrawable(it) }
    colorString?.let { background = ColorDrawable(Color.parseColor(it)) }
    Glide.with(context)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade(CROSS_FADE_DURATION))
        .addListener(requestListener)
        .into(this)
        .clearOnDetach()
}

fun TextView.setTextAndVisibility(string: String?) {
    isVisible = !string.isNullOrBlank()
    text = string
}

fun EditText.addAfterTextChangedListener(
    filter: InputFilter? = null,
    onTextChanged: (String) -> Unit
) {
    if (filter != null)
        this.filters = arrayOf(filter)

    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            onTextChanged(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

fun Dialog.setDefaultWindowTheme() {
    window?.apply {
        setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        statusBarColor = Color.TRANSPARENT

        setSystemBarTextDark()
        setDimAmount(0.3f)
    }
}
