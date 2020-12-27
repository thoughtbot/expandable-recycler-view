package com.thoughtbot.expandablerecyclerview.sample.multicheck

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thoughtbot.expandablerecyclerview.sample.GenreDataFactory
import com.thoughtbot.expandablerecyclerview.sample.R

class MultiCheckActivity : AppCompatActivity() {
    private var adapter: MultiCheckGenreAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_check)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = javaClass.simpleName
        val recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView
        val layoutManager = LinearLayoutManager(this)
        adapter = MultiCheckGenreAdapter(GenreDataFactory.makeMultiCheckGenres())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        val clear = findViewById<View>(R.id.clear_button) as Button
        clear.setOnClickListener { adapter!!.clearChoices() }
        val check = findViewById<View>(R.id.check_first_child) as Button
        check.setOnClickListener { adapter!!.checkChild(true, 0, 3) }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        adapter!!.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        adapter!!.onRestoreInstanceState(savedInstanceState)
    }
}