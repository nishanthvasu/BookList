package com.booklist.app.data.source

import com.booklist.app.domain.model.Book
import io.reactivex.Flowable

interface BookRemoteDataSource {

    fun getBookList(): Flowable<List<Book>>
}