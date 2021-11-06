package alex.pakshin.Activity

import alex.pakshin.Activity.MainActivity.RealmUtility.defaultConfig
import alex.pakshin.Models.Events
import alex.pakshin.R
import alex.pakshin.adapter.MyListAdapter
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.vicpin.krealmextensions.RealmConfigStore
import com.vicpin.krealmextensions.query
import com.vicpin.krealmextensions.save
import io.realm.Realm
import io.realm.RealmConfiguration
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var calendarView: CalendarView
    private val mAdapter = MyListAdapter()
    private val addEventCode = 13

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Realm.init(this)
        RealmConfigStore.init(Events::class.java, defaultConfig)

        calendarView = findViewById(R.id.calendarView)
        calendarView.setOnDayClickListener { eventDay -> setEventList(eventDay) }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = mAdapter
        mAdapter.setOnEventClickListener(
            object : MyListAdapter.OnEventClickListener {
                override fun onEventClick(event: Events) {
                    val intent = Intent(applicationContext, Description::class.java)
                    val gson = Gson()
                    intent.putExtra("eventShow", gson.toJson(event))
                    startActivity(intent)
                }
            }
        )

        val button: FloatingActionButton = findViewById(R.id.floatingActionButton)
        button.setOnClickListener { addEvent() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == addEventCode && resultCode == RESULT_OK) {
            val gson = Gson()
            val curEventDay: Events = gson.fromJson(
                data?.getStringExtra("result"),
                Events::class.java
            )
            calendarView.setDate(curEventDay.date)
            curEventDay.save()
        }
    }

    private fun addEvent() {
        val intent = Intent(this, AddEventActivity::class.java)
        startActivityForResult(intent, addEventCode)
    }

    private fun setEventList(eventDay: EventDay?) {
        val chosenDate: Date? = eventDay?.calendar?.time
        val eventsList: List<Events> = query { equalTo("date", chosenDate) }
        mAdapter.setData(eventsList)
    }

    object RealmUtility {
        private const val schemaVNow = 2

        val defaultConfig: RealmConfiguration
            get() = RealmConfiguration.Builder()
                .schemaVersion(schemaVNow.toLong())
                .deleteRealmIfMigrationNeeded()
                .allowWritesOnUiThread(true)
                .build()
    }
}
