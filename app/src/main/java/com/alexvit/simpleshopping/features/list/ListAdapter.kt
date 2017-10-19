package com.alexvit.simpleshopping.features.list

import android.support.v7.util.SortedList
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexvit.simpleshopping.R
import com.alexvit.simpleshopping.data.models.Item
import kotlinx.android.synthetic.main.item_item.view.*

/**
 * Created by Aleksandrs Vitjukovs on 10/8/2017.
 */

class ListAdapter(val itemClickListener: ItemClickListener) : RecyclerView.Adapter<ListAdapter.ListItemViewHolder>() {

    val itemsSortedList = SortedList<Item>(Item::class.java, SortedListCallback(this))

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ListItemViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_item, parent, false)
        return ListItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListItemViewHolder?, position: Int) {
        val item = itemsSortedList.get(position)

        holder?.itemView?.checkbox_complete?.setOnCheckedChangeListener(null)
        holder?.itemView?.checkbox_complete?.isChecked = item.checked
        holder?.itemView?.checkbox_complete?.setOnCheckedChangeListener { _, bool ->
            itemClickListener.onChecked(item, bool)
        }

        holder?.itemView?.tv_title?.text = item.title

        holder?.itemView?.btn_delete?.setOnClickListener { itemClickListener.onDelete(item) }
    }

    override fun getItemCount(): Int = itemsSortedList.size()

    fun update(item: Item, newItem: Item) {
        val i = itemsSortedList.indexOf(item)
        if (i == SortedList.INVALID_POSITION) itemsSortedList.add(newItem)
        else itemsSortedList.updateItemAt(i, newItem)
    }

    fun addAll(items: List<Item>) {
        val set = items.toMutableSet()

        itemsSortedList.beginBatchedUpdates()

        for (i in itemsSortedList.size() - 1 downTo 0) {
            val existingItem = itemsSortedList.get(i)
            val updatedItem = set.find { it.id == existingItem.id }
            if (updatedItem != null) {
                itemsSortedList.updateItemAt(i, updatedItem)
                set.remove(updatedItem)
            } else {
                itemsSortedList.remove(existingItem)
            }
        }
        itemsSortedList.addAll(set)

        itemsSortedList.endBatchedUpdates()
    }

    interface ItemClickListener {
        fun onChecked(item: Item, checked: Boolean)
        fun onDelete(item: Item)
    }

    class ListItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)

    class SortedListCallback(val rv: RecyclerView.Adapter<ListItemViewHolder>) : SortedList.Callback<Item>() {

        private val comparator: Comparator<Item> = Comparator { a, b ->
            val compareChecked = a.checked.compareTo(b.checked)
            val compareTitle = (a.title ?: "").compareTo(b.title ?: "")
            val compareId = (a.id ?: 0).compareTo(b.id ?: 0)

            listOf(compareChecked, compareTitle, compareId).find { it != 0 } ?: 0
        }

        override fun onChanged(position: Int, count: Int) {
            rv.notifyItemRangeChanged(position, count)
        }

        override fun onRemoved(position: Int, count: Int) {
            rv.notifyItemRangeRemoved(position, count)
        }

        override fun areContentsTheSame(oldItem: Item?, newItem: Item?) =
                oldItem == newItem

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            rv.notifyItemMoved(fromPosition, toPosition)
        }

        override fun compare(o1: Item?, o2: Item?): Int =
                comparator.compare(o1, o2)

        override fun areItemsTheSame(item1: Item?, item2: Item?) = item1!!.id == item2!!.id

        override fun onInserted(position: Int, count: Int) {
            rv.notifyItemRangeInserted(position, count)
        }
    }
}