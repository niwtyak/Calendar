package alex.pakshin.Activity

import alex.pakshin.Models.Events
import alex.pakshin.R
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class Description : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        val intent: Intent? = intent
        val eventDay: TextView = findViewById(R.id.show_date)
        val eventTitle: TextView = findViewById(R.id.show_title)
        val eventTimeStart: TextView = findViewById(R.id.show_time_start)
        val eventTimeEnd: TextView = findViewById(R.id.show_time_end)
        val eventDescription: TextView = findViewById(R.id.show_description)
        if (intent != null) {
            val gson = Gson()
            val curEventDay: Events = gson.fromJson(
                intent.getStringExtra("eventShow"),
                Events::class.java
            )
            eventDay.text = getFormattedDate(curEventDay.date)
            eventTitle.text = curEventDay.title
            eventTimeStart.text = curEventDay.time_start
            eventTimeEnd.text = curEventDay.time_end
            eventDescription.text = curEventDay.description
        }
        return
    }

    private fun getFormattedDate(myDate: Date?): String? {
        val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return simpleDateFormat.format(myDate)
    }
}
