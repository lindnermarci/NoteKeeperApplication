package com.example.notekeeper

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.activity_note_list.*
import kotlinx.android.synthetic.main.content_note_list.*
import kotlinx.android.synthetic.main.item_note_list.*

class NoteListActivity : AppCompatActivity() {

    private var mAdapter: NoteRecycleAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            val activityIntent = Intent(this, NoteActivity::class.java)
            startActivity(activityIntent)
        }


        listItems.layoutManager = LinearLayoutManager(this)

        mAdapter =  NoteRecycleAdapter(this, DataManager.notes)
        listItems.adapter = mAdapter
    }

    override fun onResume() {
        super.onResume()
        restart()
        listItems.adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.men_list, menu)
        // Associate searchable configuration with the SearchView
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setQueryHint("Search View Hint")

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(query: String): Boolean {
                mAdapter!!.filter.filter(query)
                restart()
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                mAdapter!!.filter.filter(query)
                restart()
                 return false
            }

        })

        return true
    }

    fun restart(){
        listItems.layoutManager = LinearLayoutManager(this)

        mAdapter =  NoteRecycleAdapter(this, DataManager.notes)
        listItems.adapter = mAdapter
    }

}
