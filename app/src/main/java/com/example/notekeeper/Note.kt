package com.example.notekeeper


data class Note(val id:Int? = null,var title: String? = null,var priority: Priority? = null, var text: String? = null)

enum class Priority{
    High, Medium, Low
}
