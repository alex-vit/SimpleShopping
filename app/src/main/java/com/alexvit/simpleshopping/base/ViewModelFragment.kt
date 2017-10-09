package com.alexvit.simpleshopping.base

import android.support.v4.app.Fragment

/**
 * Created by Aleksandrs Vitjukovs on 10/8/2017.
 */

class ViewModelFragment<ViewModel> : Fragment() where ViewModel : BaseViewModel {
    var viewmodel: ViewModel? = null
}