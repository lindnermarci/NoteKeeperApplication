package com.example.notekeeper

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

class NoteRecycleAdapter(private val context: Context, private val notes: List<Note>):
    RecyclerView.Adapter<NoteRecycleAdapter.ViewHolder>(),
    Filterable
{

    private val layOutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layOutInflater.inflate(R.layout.item_note_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        val priority = note.priority
        holder.textPriority?.text = priority.toString()
        holder.textTitle?.text = note.title
        holder.notePosition = position
        if(priority == Priority.High){
            holder.imageView.setBackgroundColor(Color.parseColor("#e6493e"))
        }else if(priority == Priority.Medium){
            holder.imageView.setBackgroundColor(Color.parseColor("#3eabe6"))
        }else{
            holder.imageView.setBackgroundColor(Color.parseColor("#7af593"))
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textPriority = itemView.findViewById<TextView?>(R.id.textPriority)
        val textTitle = itemView.findViewById<TextView?>(R.id.textTitle)
        val imageView: ImageView = itemView.findViewById<ImageView>(R.id.imageView_note_list_item)
        private val button: Button? = itemView.findViewById<Button>(R.id.button_remove_item)
        var notePosition = 0
        init{
            button?.setOnClickListener {
                val note = DataManager.notes[notePosition]
                DataManager.notes.removeAt(notePosition)
                DataManager.originalNotes.remove(note)
                notifyDataSetChanged()

            }

            textTitle?.setOnClickListener{
                val intent = Intent(context, NoteActivity::class.java)
                intent.putExtra(NOTE_POSITION, notePosition)
                context.startActivity(intent)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    DataManager.notes.clear()
                    DataManager.notes.addAll(DataManager.originalNotes)
                } else {
                    val filteredList = ArrayList<Note>()
                    for (row in DataManager.originalNotes) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.text!!.toLowerCase().contains(charString.toLowerCase()) || row.title!!.toLowerCase().contains(charSequence)) {
                            filteredList.add(row)
                        }
                    }

                    DataManager.notes.clear()
                    DataManager.notes.addAll(filteredList)
                }

                val filterResults = FilterResults()
                filterResults.values = ArrayList<Note>(DataManager.notes)

                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                DataManager.notes.clear()
                DataManager.notes.addAll(filterResults.values as ArrayList<Note>)
                notifyDataSetChanged()
            }
        }
    }
}