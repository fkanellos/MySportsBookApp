package com.example.mysportsbookapp.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var verticalScrollAdapter: VerticalScrollAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecyclerView()
        setUpTouchHelper()

        val observer = Observer<List<SportsResponseItem>> { list ->
            /* setting data in UI by updating the view with the list */
            verticalScrollAdapter.updateView(viewModel.setValuesForExpandableAdapter(list))
        }
        viewModel.obtainResult.observe(this, observer)

    }

    /* set options in the menu bar */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /* show a msg when menu option is clicked */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(this, "" + item, Toast.LENGTH_SHORT).show()
        return super.onOptionsItemSelected(item)
    }

    private fun setUpRecyclerView() = binding.recyclerview.apply {
        verticalScrollAdapter = VerticalScrollAdapter()
        adapter = verticalScrollAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }

    /* set up touch helper so the user can change the order
    * based on preferences*/
    private fun setUpTouchHelper() {
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerview)
    }

    /* use this callback to set starting and ending position of the item */
    private var simpleCallback = object : ItemTouchHelper.SimpleCallback(UP or DOWN, 0) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val startPosition = viewHolder.adapterPosition
            val endPosition = target.adapterPosition

            Collections.swap(verticalScrollAdapter.data, startPosition, endPosition)
            recyclerview.adapter?.notifyItemMoved(startPosition, endPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            //not used
        }
    }
}