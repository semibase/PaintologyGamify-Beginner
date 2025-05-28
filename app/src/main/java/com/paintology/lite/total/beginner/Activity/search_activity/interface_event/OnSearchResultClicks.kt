package com.paintology.lite.total.beginner.Activity.search_activity.interface_event

import com.paintology.lite.total.beginner.Activity.search_activity.model.SearchResultModel

interface OnSearchResultClicks {

    fun onMenuClick(model: SearchResultModel, position: Int)
}