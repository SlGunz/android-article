package com.slgunz.root.androidarticle.utils

import android.graphics.drawable.Drawable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.graphics.drawable.DrawableCompat
import android.text.Html
import android.widget.TextView


object ActivityUtils {
    fun addFragmentToActivity(fragmentManager: FragmentManager, fragment: Fragment, frameId: Int) {
        val transaction = fragmentManager.beginTransaction()
        transaction.add(frameId, fragment)
        transaction.commit()
    }

    /**
     * Change the Drawables (if any) to appear to the left of, above, to the right of, and below the text.
     * Subclasses: Button, CheckedTextView, Chronometer, DigitalClock, EditText, TextClock
     */
    fun changeButtonDrawable(textView: TextView, drawable: Drawable) {
        val drawables = textView.compoundDrawables

        for (i in drawables.indices) {
            if (drawables[i] == null) continue
            drawables[i] = DrawableCompat.wrap(drawable)
            textView.setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawables[2], drawables[3])
            break
        }
    }

    fun stripHtml(html: String): String {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            @Suppress("DEPRECATION")
            Html.fromHtml(html).toString()
        }
    }
}