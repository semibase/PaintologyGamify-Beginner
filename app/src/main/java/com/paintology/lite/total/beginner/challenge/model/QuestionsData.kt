package com.paintology.lite.total.beginner.challenge.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class QuestionsData(

    @SerializedName("quiz_type")
    @Expose
    var quiz_type: String? = null,

    )
