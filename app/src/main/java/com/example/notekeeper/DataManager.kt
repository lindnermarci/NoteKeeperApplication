package com.example.notekeeper

object DataManager {
    var notes = ArrayList<Note>()
    val prirityOptions = ArrayList<Priority>()
    var originalNotes = ArrayList<Note>()

    init{
        initialise()
        originalNotes = ArrayList(notes)
    }

    private fun initialise(){
        var note = Note(0,"1This is my note", Priority.Low, "Hello my friends, this is my note")
        notes.add(note)
        note = Note(1,"2Hello", Priority.Low, "Hello my ewr, this is my note")
        notes.add(note)
        note = Note(2,"3Szia", Priority.Medium, "Hello my dsfg, this is my note")
        notes.add(note)
        note = Note(3,"4Mi a helyzet? ", Priority.High, "Hello my sadf, this is my note")
        notes.add(note)
        note = Note(4,"5Kis macska", Priority.Low, "Hello my asdf, this is my note")
        notes.add(note)

        prirityOptions.add(Priority.Low)
        prirityOptions.add(Priority.Medium)
        prirityOptions.add(Priority.High)
    }
}