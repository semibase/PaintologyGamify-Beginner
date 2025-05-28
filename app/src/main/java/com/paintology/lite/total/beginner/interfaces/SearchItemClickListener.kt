package com.paintology.lite.total.beginner.interfaces

import com.paintology.lite.total.beginner.Enums.SearchResultType

interface SearchItemClickListener {
    fun selectItem(pos: Int, type: SearchResultType)
}