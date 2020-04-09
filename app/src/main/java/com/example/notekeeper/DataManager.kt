package com.example.notekeeper

object DataManager {
    val filteredNotes = ArrayList<Note>()
    val priorityOptions = ArrayList<Priority>()
    val notes = ArrayList<Note>()

    init{
        initialise()
        notes.addAll(filteredNotes)
    }

    private fun initialise(){
        var note = Note(0,"Kivinni a szemetet", Priority.Low, "Lorem ipsum dolor sit amet")
        filteredNotes.add(note)
        note = Note(1,"Megenni az ebédet", Priority.Low, "consectetur adipiscing elit")
        filteredNotes.add(note)
        note = Note(2,"Mobilszám", Priority.Medium, "123456789")
        filteredNotes.add(note)
        note = Note(3,"Kismacska", Priority.High, "et dolore magna aliqua")
        filteredNotes.add(note)
        note = Note(4,"Új jegyzet", Priority.Low, "Ut enim ad minim veniam")
        filteredNotes.add(note)
        note = Note(4,"Elfelejted", Priority.Low, "Ut evsr ad hsl armun")
        filteredNotes.add(note)

        priorityOptions.add(Priority.Low)
        priorityOptions.add(Priority.Medium)
        priorityOptions.add(Priority.High)
    }
}