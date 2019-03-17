package com.github.saldiwe.panicbutton.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun clear()

    abstract fun onBind(position: Int)
}