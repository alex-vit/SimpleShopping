package com.alexvit.simpleshopping.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alexvit.simpleshopping.appComponent
import com.alexvit.simpleshopping.di.components.DaggerActivityComponent
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Aleksandrs Vitjukovs on 10/8/2017.
 */

abstract class BaseActivity<ViewModel> : AppCompatActivity() where ViewModel : BaseViewModel {

    companion object {
        const val TAG_VM_FRAGMENT = "TAG_VM_FRAGMENT"
    }

    protected val component by lazy {
        DaggerActivityComponent.builder()
                .appComponent(appComponent())
                .build()
    }
    protected val compositeSub = CompositeDisposable()

    protected lateinit var viewModelFragment: ViewModelFragment<ViewModel>

    protected lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModelFragment = getOrCreateVmFragment()
        viewModel = getViewModel(viewModelFragment)
    }

    override fun onResume() {
        super.onResume()

        compositeSub.clear()
        bind(viewModel)
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeSub.clear()
        if (isFinishing) {
            viewModel.onDestroy()
        }
    }

    protected fun <T> subscribe(
            observable: Observable<T>,
            onNext: (T) -> Unit,
            onError: (Throwable) -> Unit = {},
            onComplete: () -> Unit = {}) {

        val sub = observable.subscribe(onNext, onError, onComplete)
        compositeSub.add(sub)
    }

    abstract protected fun getViewModelFromComponent(): ViewModel

    abstract protected fun bind(viewModel: ViewModel)

    private fun getOrCreateVmFragment(): ViewModelFragment<ViewModel> {
        @Suppress("UNCHECKED_CAST")
        var vmFragment: ViewModelFragment<ViewModel>? = supportFragmentManager
                .findFragmentByTag(TAG_VM_FRAGMENT) as? ViewModelFragment<ViewModel>
        if (vmFragment == null) {
            vmFragment = ViewModelFragment()
            supportFragmentManager.beginTransaction()
                    .add(vmFragment, TAG_VM_FRAGMENT)
                    .commit()
        }
        return vmFragment
    }

    private fun getViewModel(fragment: ViewModelFragment<ViewModel>): ViewModel {
        if (fragment.viewmodel == null) {
            val vm = getViewModelFromComponent()
            fragment.viewmodel = vm
            return vm
        } else {
            return fragment.viewmodel as ViewModel
        }
    }
}