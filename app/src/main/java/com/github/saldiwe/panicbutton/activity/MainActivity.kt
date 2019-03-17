package com.github.saldiwe.panicbutton.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.github.saldiwe.panicbutton.InstansiDataAdapter
import com.github.saldiwe.panicbutton.R
import com.github.saldiwe.panicbutton.data.AppDatabase
import com.github.saldiwe.panicbutton.data.Instansi
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread



open class MainActivity : AppCompatActivity() {

    lateinit var db: AppDatabase

    private var listInstansi: ArrayList<Instansi>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val callPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CALL_PHONE
        )

        if (callPermission != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this@MainActivity, "Permission denied to make Phone Call", Toast.LENGTH_SHORT).show()
            makeRequest()
        }

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "panik")
            .fallbackToDestructiveMigration()
            .build()

        val arrayInstansi = listOf(Instansi(1, R.drawable.sos,"SOS","112"),
            Instansi(2, R.drawable.polisi,"Polisi","110"),
            Instansi(3, R.drawable.ambulance,"Ambulance","118"),
            Instansi(4, R.drawable.pemadam,"Pemadam","113"),
            Instansi(5, R.drawable.pln,"PLN","123"))

        insertData(arrayInstansi)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item?.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorite) {
            startActivity(Intent(this, AboutActivity::class.java))
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun insertData(instansi: List<Instansi>){
        doAsync {
            for (i in 0 until instansi.size) {
                db.instansiDAO().insertInstansi(instansi[i])
            }

            uiThread {
                loadData()
            }
        }
    }

    private fun loadData(){
        doAsync {
            listInstansi?.clear()
            listInstansi?.addAll(db.instansiDAO().selectAllInstansi())
            uiThread {
                listInstansi?.let {
                    val adapter = InstansiDataAdapter(it)
                    rv_data.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
                    rv_data.setHasFixedSize(true)
                    rv_data.adapter = adapter
                }

            }
        }
    }

    private fun deleteData(){
        doAsync {
            db.instansiDAO().deleteAllInstansi()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this@MainActivity, "Permission denied to make Phone Call", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }// other 'case' lines to check for other
        // permissions this app might request
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.CALL_PHONE), 1)
    }

}
