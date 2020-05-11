package com.booklist.app.domain.repository

import com.booklist.app.domain.model.Book
import io.reactivex.Completable
import io.reactivex.Flowable

interface BookRepository {

    fun clearBookList(): Completable

    fun saveBookList(bookList: List<Book>): Completable

    fun getBookList(): Flowable<List<Book>>
}