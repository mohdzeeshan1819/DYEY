package com.example.dyey.ExtraClasses

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import com.example.dyey.R  // Replace R with your project's R file

class MyPopupWindow(private val context: Context) {
    private var popupWindow: PopupWindow? = null
    private var onDeleteClickListener: (() -> Unit)? = null
    private var restaurantDetailsListener: (() -> Unit)? = null
    private var restaurantEditDetailsListener: (() -> Unit)? = null



    fun setOnDeleteClickListener(listener: () -> Unit) {
        onDeleteClickListener = listener
    }
    fun restaurantDetails(listener: () -> Unit) {
        restaurantDetailsListener = listener
    }
    fun restaurantEditDetails(listener: () -> Unit) {
        restaurantEditDetailsListener = listener
    }
    companion object {
        fun newInstance(position: Int): YourPopupFragment {
            return YourPopupFragment()
        }
    }

    fun showPopup(anchorView: View) {
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup_layout, null)

        popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        popupWindow?.isFocusable = true

        // Animation
        val anim = TranslateAnimation(500f, 0f, 500f, 0f) // Adjust the Y values as needed
        anim.duration = 0 // Duration in milliseconds
        popupView.startAnimation(anim)

        // Show the popup window at the bottom
        popupWindow?.showAtLocation(
            anchorView,
            Gravity.BOTTOM,
            0,
            300
        )

        val doneTextView: TextView = popupView.findViewById(R.id.done)
        val deleteOffer: LinearLayout = popupView.findViewById(R.id.delete)
        val restaurantDetails: LinearLayout = popupView.findViewById(R.id.restaurantDetails)
        val restaurantEditDetails: LinearLayout = popupView.findViewById(R.id.editDetails)


        deleteOffer.setOnClickListener(){
            onDeleteClickListener?.invoke()
            dismissPopup()        }
        restaurantDetails.setOnClickListener(){
            restaurantDetailsListener?.invoke()
            dismissPopup()        }
        restaurantEditDetails.setOnClickListener(){
            restaurantEditDetailsListener?.invoke()
            dismissPopup()        }

        doneTextView.setOnClickListener {
            dismissPopup()
        }

    }

    fun dismissPopup() {
        popupWindow?.dismiss()
    }

    fun show(anchorView: View) {
        showPopup(anchorView)
    }
}
