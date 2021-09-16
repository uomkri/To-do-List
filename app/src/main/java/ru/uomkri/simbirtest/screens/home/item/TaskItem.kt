package ru.uomkri.simbirtest.screens.home.item

import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import com.xwray.groupie.viewbinding.BindableItem
import ru.uomkri.simbirtest.R
import ru.uomkri.simbirtest.databinding.ListItemBinding
import ru.uomkri.simbirtest.screens.home.HomeFragmentDirections

open class TaskItem(
    private val text: String,
    private val time: String,
    private val id: Int
): BindableItem<ListItemBinding>() {
    override fun bind(viewBinding: ListItemBinding, position: Int) {
        viewBinding.name.text = text
        viewBinding.time.text = time
        viewBinding.root.setOnClickListener {
            it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(id))
        }
    }

    override fun getLayout(): Int = R.layout.list_item

    override fun initializeViewBinding(view: View): ListItemBinding = ListItemBinding.bind(view)

}