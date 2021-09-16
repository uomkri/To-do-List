package ru.uomkri.simbirtest.screens.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.uomkri.simbirtest.R
import ru.uomkri.simbirtest.databinding.HourContainerBinding
import ru.uomkri.simbirtest.utils.HourItem

/*class TasksAdapter(private val data: List<HourItem>) : RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    private lateinit var layoutInflater: LayoutInflater
    private lateinit var mParent: ViewGroup

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        layoutInflater = LayoutInflater.from(parent.context)
        mParent = parent
        val view = layoutInflater.inflate(R.layout.hour_container, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        binding.time.text = data[position].hourFormatted
        if (!data[position].tasks.isNullOrEmpty()) {
            for (i in data[position].tasks!!) {
                val view = layoutInflater.inflate(R.layout.list_item, mParent, false)
                val textView = view.findViewById<TextView>(R.id.name)
                textView.text = i.name
                binding.tasksContainer.addView(view)

                view.setOnClickListener {
                    Log.e("view", i.id.toString())
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = HourContainerBinding.bind(itemView)
    }
}*/