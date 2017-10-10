package com.alexvit.simpleshopping.features.list

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.alexvit.simpleshopping.R
import com.alexvit.simpleshopping.base.BaseActivity
import com.alexvit.simpleshopping.data.models.Item
import com.alexvit.simpleshopping.features.addedit.AddEditActivity
import com.dropbox.core.android.Auth
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : BaseActivity<ListViewModel>(), ListAdapter.ItemClickListener {

    val adapter by lazy { ListAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        initRecycler()
        fab_add_item.setOnClickListener { launchAddEditItem() }
        btn_sign_into_dropbox.setOnClickListener { launchDropboxAuth() }
    }

    override fun bind(viewModel: ListViewModel) {
        subscribe(viewModel.items(), adapter::addAll)
        subscribe(viewModel.itemUpdates(), { (old, new) -> adapter.update(old, new) })
        subscribe(viewModel.showSignIntoDropbox(), this::showSignInButton)
    }

    override fun getViewModelFromComponent(): ListViewModel = component.listViewModel()

    override fun onChecked(item: Item, checked: Boolean) {
        viewModel.check(item, checked)
    }

    override fun onDelete(item: Item) {
        viewModel.delete(item)
    }

    private fun initRecycler() {
        rv_items.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_items.adapter = adapter
    }

    private fun showSignInButton(show: Boolean) {
        val visibility = if (show) View.VISIBLE
        else View.GONE

        btn_sign_into_dropbox.visibility = visibility
    }

    private fun launchAddEditItem() {
        val intent = Intent(this, AddEditActivity::class.java)
        startActivity(intent)
    }

    private fun launchDropboxAuth() {
        Auth.startOAuth2Authentication(this, resources.getString(R.string.dropbox_app_key))
    }

}
