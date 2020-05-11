package com.booklist.app

import androidx.room.Room
import com.booklist.app.cache.BookCacheDataSourceImpl
import com.booklist.app.cache.db.BookDatabase
import com.booklist.app.cache.preference.BookPreference
import com.booklist.app.data.repository.BookRepositoryImpl
import com.booklist.app.data.source.BookCacheDataSource
import com.booklist.app.data.source.BookRemoteDataSource
import com.booklist.app.domain.repository.BookRepository
import com.booklist.app.presentation.viewmodel.BookViewModel
import com.booklist.app.remote.BookRemoteDataSourceImpl
import com.booklist.app.remote.service.BookServiceFactory
import com.booklist.app.usecase.GetBookListUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

}

val cacheModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            BookDatabase::class.java,
            "book.db"
        ).build()
    }

    factory { get<BookDatabase>().bookDao() }

    single { BookPreference(get()) }

    factory<BookCacheDataSource> { BookCacheDataSourceImpl(get(), get()) }
}

val dataModule = module {

    factory<BookRepository> { BookRepositoryImpl(get(), get()) }
}

val domainModule = module {

}

val presentationModule = module {

    viewModel { BookViewModel(get()) }
}

val remoteModule = module {

    single { BookServiceFactory.makeBookService(BuildConfig.DEBUG) }

    factory<BookRemoteDataSource> { BookRemoteDataSourceImpl(get()) }
}

val useCaseModule = module {

    single { GetBookListUseCase(get()) }
}

val mobileUiModule = module {

}