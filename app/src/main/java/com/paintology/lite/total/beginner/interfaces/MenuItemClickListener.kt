package com.paintology.lite.total.beginner.interfaces

import android.view.View
import com.paintology.lite.total.beginner.painting.PaintItem

interface MenuItemClickListener {
    fun onEditClick(item: PaintItem?, position: Int)
    fun onDeleteClick(item: PaintItem?, position: Int)
    fun onShareClick(item: PaintItem?, position: Int)
    fun onPostClick(item: PaintItem?, position: Int)
    fun onSubMenuClick(view: View, item: PaintItem?, position: Int)
}