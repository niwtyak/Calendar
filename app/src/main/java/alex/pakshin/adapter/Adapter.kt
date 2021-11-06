package alex.pakshin.adapter

import alex.pakshin.Models.Events
import alex.pakshin.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MyListAdapter : RecyclerView.Adapter<MyListAdapter.ViewHolder>() {
    private val eventList: MutableList<Events> = LinkedList()
    private var onEventClickListener: OnEventClickListener? = null

    override fun getItemCount() = eventList.count()

    fun setData(events: List<Events>) {
        eventList.clear()
        eventList.addAll(events)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private var eventTitle: TextView = itemView.findViewById(R.id.event_title)
        private var eventTimeStart: TextView = itemView.findViewById(R.id.event_time_start)
        private var eventTimeEnd: TextView = itemView.findViewById(R.id.event_time_end)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(model: Events) {
            eventTitle.text = model.title
            eventTimeStart.text = model.time_start
            eventTimeEnd.text = model.time_end
        }

        override fun onClick(v: View?) {
            if (v != null) {
                onEventClickListener?.onEventClick(eventList[layoutPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_view,
            parent,
            false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(eventList[position])
        //  holder.eventTitle.setOnClickListener {  Log.v("Adapter", "${eventList[position].title}")}
    }

    fun setOnEventClickListener(clickListener: OnEventClickListener) {
        this.onEventClickListener = clickListener
    }

    interface OnEventClickListener {
        fun onEventClick(event: Events)
    }
}
