package com.mydemoapplication

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mydemoapplication.adapter.NotesAdapter
import com.mydemoapplication.db.Note
import com.mydemoapplication.db.NoteDatabase
import kotlinx.android.synthetic.main.activity_note_listing.*


class NoteListingActivity : AppCompatActivity() {


    private var recyclerView: RecyclerView? = null
    private var noteDatabase: NoteDatabase? = null
    private var notes: MutableList<Note>? = null
    private var notesAdapter: NotesAdapter? = null
    private var getNoteList : GetNoteListTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_listing)
        setSupportActionBar(toolbar)

        // initialize database instance
        noteDatabase = NoteDatabase.getInstance(this)
        // fetch list of notes in background thread

        fab.setOnClickListener { view ->
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivityForResult(intent,100)
        }

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView?.setLayoutManager(LinearLayoutManager(this, RecyclerView.VERTICAL ,false));

        recyclerView?.addItemDecoration(DividerItemDecoration(this,RecyclerView.VERTICAL))

        getNoteList = GetNoteListTask()
        getNoteList!!.execute(null as Void?)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == 1) {
            getNoteList = GetNoteListTask()
            getNoteList!!.execute(null as Void?)
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    inner class GetNoteListTask internal constructor() :
        AsyncTask<Void, Void, MutableList<Note>?>() {

        override fun doInBackground(vararg params: Void): MutableList<Note>? {
            // TODO: attempt authentication against a network service.

            // fetch data

            return noteDatabase?.getNoteDao()?.all;
        }

        override fun onPostExecute(success: MutableList<Note>?) {
            getNoteList = null

            if(success?.size!! > 0)
            {
                notes = success
                // create and set the adapter on RecyclerView instance to display list
                notesAdapter = NotesAdapter(notes,applicationContext)
                recyclerView?.setAdapter(notesAdapter)
            }
        }

        override fun onCancelled() {
            getNoteList = null
        }
    }

}
