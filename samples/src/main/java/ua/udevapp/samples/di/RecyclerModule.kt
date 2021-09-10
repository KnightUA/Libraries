package ua.udevapp.samples.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ua.udevapp.samples.data.repository.items.ItemRepository
import ua.udevapp.samples.data.repository.items.ItemTestRepository

@Module
@InstallIn(ViewModelComponent::class)
object RecyclerModule {

    @Provides
    fun provideItemRepository(): ItemRepository = ItemTestRepository()
}