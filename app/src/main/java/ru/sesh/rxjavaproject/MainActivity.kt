package ru.sesh.rxjavaproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import ru.sesh.rxjavaproject.lore.FlowableHandling.launchFlowable

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.text).apply {
            setOnClickListener {
                launchFlowable()
            }
        }
    }
}