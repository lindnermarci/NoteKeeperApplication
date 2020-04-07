package com.example.notekeeper

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.app.Activity
import android.graphics.drawable.BitmapDrawable
import android.graphics.Bitmap
import android.provider.ContactsContract
import kotlin.random.Random


class NoteActivity : AppCompatActivity() {
    private var notePosition = POSITION_NOT_SET
    var originalNoteIndex = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        val adapterCourses = ArrayAdapter<Priority>(this,
            android.R.layout.simple_spinner_item,
            DataManager.prirityOptions)
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerPriorities.adapter = adapterCourses

        notePosition = savedInstanceState?.getInt(NOTE_POSITION, POSITION_NOT_SET) ?:
            intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET)

        if(notePosition != POSITION_NOT_SET){
            displayNote()
        }
        else{
            DataManager.originalNotes.add(Note())
            notePosition = DataManager.originalNotes.lastIndex
            originalNoteIndex = DataManager.originalNotes.lastIndex
        }

        button_add_image.setOnClickListener { view ->
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE)
        }

        button_remove.setOnClickListener { view ->
            DataManager.notes[notePosition].id?.let { removeNode(it) }
            val activityIntent = Intent(this, NoteListActivity::class.java)
            startActivity(activityIntent)
        }



    }

    private fun removeNode(id: Int) {
        for(node in DataManager.notes){
            if(node.id == id){
                DataManager.notes.removeAt(DataManager.notes.indexOf(node))
            }
        }
        for(node in DataManager.originalNotes){
            if(node.id == id){
                DataManager.notes.removeAt(DataManager.notes.indexOf(node))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == SELECT_IMAGE){
            imageView_note.setImageURI(data?.data) // handle chosen image
            var bitmap = (imageView_note.drawable as BitmapDrawable).bitmap
            saveNote(bitmap)
        }
    }

    private fun displayNote() {
        val note = DataManager.notes[notePosition]
        textNoteTitle.setText(note.title)
        textNoteText.setText(note.text)
        if(note.image != null){
            imageView_note.setImageBitmap(note.image)
        }

        val coursePosition = DataManager.prirityOptions.indexOf(note.priority);
        spinnerPriorities.setSelection(coursePosition)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(NOTE_POSITION, notePosition)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        // Associate searchable configuration with the SearchView
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_next -> {
                moveNext()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun moveNext() {
        notePosition++
        displayNote()
        invalidateOptionsMenu()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if(notePosition >= DataManager.notes.lastIndex){
            val menuItem = menu?.findItem(R.id.action_next);
            if(menuItem != null){
                menuItem.icon = getDrawable(R.drawable.ic_block_white_24dp)
                menuItem.isEnabled = false
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        saveNote()
    }

    private fun saveNote(image: Bitmap? = null) {
        if(originalNoteIndex == -1){ //modositas
            val note = DataManager.notes[notePosition]
            note.title = textNoteTitle.text.toString()
            note.text = textNoteText.text.toString()
            note.priority = spinnerPriorities.selectedItem as Priority
            if(image != null){
                note.image = image
            }
        }else{ //uj elem hozzadas
            val note = DataManager.originalNotes[originalNoteIndex]
            val id = Random.nextInt(1, Int.MAX_VALUE)
            note.id = id
            note.title = textNoteTitle.text.toString()
            note.text = textNoteText.text.toString()
            note.priority = spinnerPriorities.selectedItem as Priority

            var exists = false
            for (n in DataManager.notes){
                if(note.id == n.id) exists = true
            }
            if(!exists)
            DataManager.notes.add(note)
            if(image != null){
                note.image = image
            }
        }

    }

}

