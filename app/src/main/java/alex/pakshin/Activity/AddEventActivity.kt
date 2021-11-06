package alex.pakshin.Activity

import alex.pakshin.Models.Events
import alex.pakshin.R
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.applandeo.materialcalendarview.CalendarView
import com.google.gson.Gson

class AddEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_event_view)

        val datePicker: CalendarView = findViewById(R.id.calendarView)
        val button: Button = findViewById(R.id.button)
        val title: EditText = findViewById(R.id.edit_event_title)
        val timeStart: EditText = findViewById(R.id.edit_event_time_start)
        val timeEnd: EditText = findViewById(R.id.edit_event_time_end)
        val description: EditText = findViewById(R.id.edit_event_description)

        button.setOnClickListener {
            val returnIntent = Intent()
            val newEvent = Events(
                null,
                title.text.toString(),
                datePicker.firstSelectedDate.time,
                timeStart.text.toString(),
                timeEnd.text.toString(),
                description.text.toString()
            )
            val gson = Gson()
            returnIntent.putExtra("result", gson.toJson(newEvent))
            setResult(RESULT_OK, returnIntent)
            finish()
        }
    }
}
