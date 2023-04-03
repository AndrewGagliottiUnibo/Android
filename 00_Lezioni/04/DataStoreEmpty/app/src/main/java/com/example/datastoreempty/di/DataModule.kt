package com.example.datastore.di

import android.content.Context
import com.example.datastoreempty.data.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
// va indicato dove devono essere usati
@InstallIn(SingletonComponent::class)
object DataModule {
    // crea il repository e passa il contesto della applicazione: si sfrutta un singleton.
    // la funzione è etichettata come qualcosa che viene fornito
    @Singleton
    @Provides
    fun providesSettingsRepository(@ApplicationContext context: Context) =
        SettingsRepository(context)
}
