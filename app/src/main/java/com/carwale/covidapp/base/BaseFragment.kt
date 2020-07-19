package com.apnadukan.helloindia.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.carwale.covidapp.di.components.AppComponent
import com.carwale.covidapp.utils.shared_prefrence.SharedPref
import com.carwale.covidapp.BaseApp
import com.carwale.covidapp.api.UserService
import com.carwale.covidapp.base.BaseActivity
import com.carwale.covidapp.base.BaseViewModel
import com.carwale.covidapp.base.UICallbacks
import com.carwale.covidapp.room.AppDB
import com.carwale.covidapp.room.UserDao

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> : Fragment(),
    UICallbacks<V> {

    protected lateinit var mBinding: T
    protected lateinit var mViewModel: V

    internal lateinit var ctx: Context
    internal lateinit var mSP: SharedPref

    internal lateinit var appDB: AppDB
    internal lateinit var userDao: UserDao

    internal lateinit var userService: UserService

    private lateinit var component: AppComponent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(getBaseActivity()).get(getViewModel())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        activity?.baseContext.let {
            if (it != null) {
                ctx = it
            }
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        component = BaseApp.instance.getAppComponent()

        mSP = SharedPref.getInstance()

        appDB = BaseApp.instance.appDB
        userDao = BaseApp.instance.userDao

        userService = BaseApp.instance.userService

        onBinding()
        viewModelListener()
    }

    protected fun getBaseActivity() = activity as BaseActivity<*, *>


}