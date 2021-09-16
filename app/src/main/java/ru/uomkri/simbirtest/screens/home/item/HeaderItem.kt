package ru.uomkri.simbirtest.screens.home.item

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import ru.uomkri.simbirtest.R
import ru.uomkri.simbirtest.databinding.ItemHeaderBinding
import ru.uomkri.simbirtest.databinding.ListItemBinding

open class HeaderItem(
    private val time: String
): BindableItem<ItemHeaderBinding>() {
    override fun bind(viewBinding: ItemHeaderBinding, position: Int) {
        viewBinding.textView.text = time
    }

    override fun getLayout(): Int = R.layout.item_header

    override fun initializeViewBinding(view: View): ItemHeaderBinding = ItemHeaderBinding.bind(view)
}