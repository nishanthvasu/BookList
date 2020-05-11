package com.booklist.app.data.repository

import com.booklist.app.data.source.BookCacheDataSource
import com.booklist.app.data.source.BookRemoteDataSource
import com.booklist.app.domain.model.Book
import com.booklist.app.domain.repository.BookRepository
import io.reactivex.Completable
import io.reactivex.Flowable

class BookRepositoryImpl(
    private val bookCacheDataSource: BookCacheDataSource,
    private val bookRemoteDataSource: BookRemoteDataSource
) : BookRepository {

    override fun clearBookList(): Completable {
        return bookCacheDataSource.clearBookList()
    }

    override fun saveBookList(bookList: List<Book>): Completable {
        return bookCacheDataSource.saveBookList(bookList)
            .doOnComplete {
                bookCacheDataSource.setLastCacheTime(System.currentTimeMillis())
            }
    }

    override fun getBookList(): Flowable<List<Book>> {
        return bookCacheDataSource.isCached()
            .flatMapPublisher { isCached ->
                if (isCached && !bookCacheDataSource.isExpired()) {
                    bookCacheDataSource.getBookList()
                } else {
                    bookRemoteDataSource.getBookList()
                }
            }
            .flatMap {
                saveBookList(it)
                    .toSingle { it }
                    .toFlowable()
            }
    }
}