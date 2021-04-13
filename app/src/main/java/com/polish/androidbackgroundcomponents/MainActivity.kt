package com.polish.androidbackgroundcomponents

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    val TAG = "Main_Activity"

    lateinit var flow: Flow<Int>
    lateinit var myButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // initialize the button
        myButton = findViewById(R.id.activity_main_btn)
        setupFlow()
        setupClicks()
    }

    // setup flow:: this is the producer
    fun setupFlow(){
        flow = flow {
            Log.d(TAG, "Start flow")
            (0..10).forEach{
                // Emit items with 500 milliseconds delay
                delay(500)
                Log.d(TAG, "Emitting $it")
                emit(it)
            }
        }.flowOn(Dispatchers.Default)
    }

    // this is the consumer
    private fun setupClicks(){
        myButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                flow.collect {
                    Log.d(TAG, it.toString())
                }
            }
        }
    }

}