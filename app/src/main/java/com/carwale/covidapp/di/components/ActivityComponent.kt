package com.carwale.covidapp.di.components

import com.carwale.covidapp.di.annotations.PerActivity
import com.carwale.covidapp.BaseApp
import dagger.Subcomponent

@PerActivity
@Subcomponent
interface ActivityComponent {

    fun inject(BaseApp: BaseApp)

    @Subcomponent.Builder
    interface Builder {

        fun build(): ActivityComponent
    }

}