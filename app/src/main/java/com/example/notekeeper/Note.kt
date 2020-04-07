package com.example.notekeeper

import android.net.Uri


data class Note(val id:Int? = null,var title: String? = null,var priority: Priority? = null, var text: String? = null, var imageUri: Uri? = null)

enum class Priority{
    High, Medium, Low
}
