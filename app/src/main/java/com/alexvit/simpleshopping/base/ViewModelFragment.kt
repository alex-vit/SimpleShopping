package com.alexvit.simpleshopping.base

import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Created by Aleksandrs Vitjukovs on 10/8/2017.
 */

class ViewModelFragment<ViewModel> : Fragment() where ViewModel : BaseViewModel {

    @Suppress("unused", "PrivatePropertyName")
    private val TAG = ViewModelFragment::class.java.simpleName

    var viewModel: ViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
    }

    override fun onDestroy() {
        super.onDestroy()

        viewModel?.onDestroy()
    }
}