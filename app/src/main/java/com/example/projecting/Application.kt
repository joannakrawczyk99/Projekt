package com.example.projecting

import android.app.Application
import com.example.projecting.network.JsonApi
import com.example.projecting.network.JsonApiService
import com.example.projecting.repo.*
import com.example.projecting.ui.viewModel.*
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class Application : Application() {
    private var list = module {
        single {
            JsonApi(androidContext())
        }
        single {
            provideApiService(get())
        }
        single {
            PostRepo(jsonApiService = get())
        }
        single {
            CommentRepo(jsonApiService = get())
        }
        single {
            UserRepo(jsonApiService = get())
        }
        single {
            AlbumRepo(jsonApiService = get())
        }
        single {
            PhotoRepo(jsonApiService = get())
        }

        viewModel {
            PostViewModel(
                postRepo = get(),
                userRepo = get(),
                commentRepo = get()
            )
        }

        viewModel {
            UserViewModel(userRepo = get())
        }

        viewModel {
            CommentViewModel(
                commentRepo = get(),
                postRepo = get()
            )
        }
        viewModel {
            AlbumViewModel(
                albumRepo = get(),
                userRepo = get()
            )
        }
        viewModel {
            PhotoViewModel(
                photoRepo = get(),
                albumRepo = get()
            )
        }


    }
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(list)
        }
    }
    private fun provideApiService(api: JsonApi) : JsonApiService{
        return api.jsonApiService()
    }
}