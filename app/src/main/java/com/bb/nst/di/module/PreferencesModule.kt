package com.bb.nst.di.module

import android.content.Context
import android.content.SharedPreferences
import com.bb.nst.utils.common.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * SharedPreferences Module
 * Using 'Singleton' we only created an object once.
 */
@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {
    /**
     * Created SharedPreferences
     */
    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences =
        appContext.getSharedPreferences(Constants.PACKAGE_NAME, Context.MODE_PRIVATE)
}