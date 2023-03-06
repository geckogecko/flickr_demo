package at.steinbacher.flickrdemo.feature.home.di

import at.steinbacher.flickrdemo.feature.home.repository.HomeRepository
import at.steinbacher.flickrdemo.feature.home.repository.IHomeRepository
import at.steinbacher.flickrdemo.feature.home.viewmodel.HomeViewModel
import at.steinbacher.flickrdemo.feature.home.viewmodel.ImageDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    single<IHomeRepository>{ HomeRepository(get()) }

    viewModel { HomeViewModel(get()) }
    viewModel { ImageDetailViewModel() }
}