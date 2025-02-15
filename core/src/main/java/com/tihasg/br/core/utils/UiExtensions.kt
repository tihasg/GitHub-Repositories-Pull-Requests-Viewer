package com.tihasg.br.core.utils

import android.view.View
import androidx.recyclerview.widget.ListAdapter

fun View.visibleOrGone(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun <T> ListAdapter<T, *>.updateData(data: List<T>?) {
    submitList(data ?: emptyList())
}