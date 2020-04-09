package com.example.notekeeper

object DataManager {
    val notes = ArrayList<Note>()
    val priorityOptions = ArrayList<Priority>()
    val originalNotes = ArrayList<Note>()

    init{
        initialise()
        originalNotes.addAll(notes)
    }

    private fun initialise(){
        var note = Note(0,"Kivinni a szemetet", Priority.Low, "Lorem ipsum dolor sit amet")
        notes.add(note)
        note = Note(1,"Megenni az ebédet", Priority.Low, "consectetur adipiscing elit")
        notes.add(note)
        note = Note(2,"Mobilszám", Priority.Medium, "123456789")
        notes.add(note)
        note = Note(3,"Kismacska", Priority.High, "et dolore magna aliqua")
        notes.add(note)
        note = Note(4,"Új jegyzet", Priority.Low, "Ut enim ad minim veniam")
        notes.add(note)

        priorityOptions.add(Priority.Low)
        priorityOptions.add(Priority.Medium)
        priorityOptions.add(Priority.High)
    }
}