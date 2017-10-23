package com.alexvit.simpleshopping.features.addedit

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import com.alexvit.simpleshopping.R
import com.alexvit.simpleshopping.base.BaseActivity
import com.alexvit.simpleshopping.data.models.Item
import com.alexvit.simpleshopping.startDbUploadJob
import kotlinx.android.synthetic.main.activity_add_edit.*

class AddEditActivity : BaseActivity<AddEditViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        title = getString(R.string.label_add_item)

        et_item_title.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                addItem()
                true
            } else {
                false
            }
        }
    }

    override fun getViewModelFromComponent(): AddEditViewModel = component.addEditViewModel()

    override fun bind(viewModel: AddEditViewModel) {
        subscribe(viewModel.saved(), {
            startDbUploadJob()
            finish()
        })
    }

    private fun addItem() {
        val item = Item(title = et_item_title.text.toString())
        viewModel.insert(item)
    }
}
