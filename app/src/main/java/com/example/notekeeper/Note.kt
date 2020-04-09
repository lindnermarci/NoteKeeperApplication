package com.example.notekeeper

import android.graphics.Bitmap


data class Note(var id:Int? = null,var title: String? = null,var priority: Priority? = null, var text: String? = null, var image: Bitmap? = null)

enum class Priority{
    High, Medium, Low
}
