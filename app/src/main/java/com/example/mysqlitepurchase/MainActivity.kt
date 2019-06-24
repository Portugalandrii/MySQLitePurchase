package com.example.mysqlitepurchase

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.item.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        var dbHandler = DatabaseHandler(applicationContext)
        base.layoutManager = LinearLayoutManager(applicationContext)
        base.adapter = ProductsAdapter(dbHandler!!.getProducts(),this)

        var sumString =dbHandler.sumAll().toString()

        val textView = findViewById<TextView>(R.id.all_sum)
        textView.text = "СУММА = $sumString грн"

        fab.setOnClickListener { view ->
            val i = Intent(applicationContext, AddActivity::class.java)
           // i.putExtra("key",1)
            startActivity(i)
        }

        fab_delete.setOnClickListener {
            dbHandler.deleteAllTasks()
            val i = Intent(applicationContext, MainActivity::class.java)
            startActivity(i)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
