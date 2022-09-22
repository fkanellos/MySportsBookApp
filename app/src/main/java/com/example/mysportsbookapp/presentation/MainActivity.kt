package com.example.mysportsbookapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mysportsbookapp.R
import com.example.mysportsbookapp.adapters.VerticalScrollAdapter
import com.example.mysportsbookapp.data.api.dao.SportsResponseItem
import com.example.mysportsbookapp.databinding.ActivityMainBinding
import com.example.mysportsbookapp.domain.viewModels.MainViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var verticalScrollAdapter: VerticalScrollAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        val observer = Observer<List<SportsResponseItem>> { list ->
            //set data in UI
            verticalScrollAdapter.updateView(viewModel.prepareDataForExpandableAdapter(list))
        }
        viewModel.obtainResult.observe(this, observer)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(this, "" + item, Toast.LENGTH_SHORT).show()
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecyclerView() = binding.recyclerview.apply {
        verticalScrollAdapter = VerticalScrollAdapter()
        adapter = verticalScrollAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }

}