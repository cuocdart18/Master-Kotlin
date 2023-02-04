package com.example.myapplication.instant_searching

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GlobalScope.launch {
            binding.svDemo.getQueryTextChangeStateFlow()
                .debounce(500)
                .filter { query ->
                    if (query.isEmpty()) {
                        Log.i(TAG, "text is null")
                        return@filter false
                    } else {
                        return@filter true
                    }
                }
                .distinctUntilChanged()
                .flowOn(Dispatchers.Default)
                .collect { result ->
                    Log.i(TAG, "text is $result")
                }
        }
    }

    private fun SearchView.getQueryTextChangeStateFlow(): StateFlow<String> {
        val query = MutableStateFlow("")
        setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                query.value = newText
                return true
            }
        })
        return query
    }
}