package com.booklist.app.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.booklist.app.domain.model.Book
import com.booklist.app.presentation.data.Resource
import com.booklist.app.usecase.GetBookListUseCase
import io.reactivex.subscribers.DisposableSubscriber

class BookViewModel(
    private val getBookListUseCase: GetBookListUseCase
) : ViewModel() {

    val bookList = MutableLiveData<Resource<List<Book>>>()

    override fun onCleared() {
        getBookListUseCase.dispose()
        super.onCleared()
    }

    fun fetchBookList() {
        bookList.postValue(
            Resource.loading()
        )
        getBookListUseCase.execute(BookSubscriber())
    }

    private inner class BookSubscriber : DisposableSubscriber<List<Book>>() {
        override fun onComplete() {

        }

        override fun onNext(t: List<Book>?) {
            bookList.postValue(
                Resource.success(t)
            )
        }

        override fun onError(t: Throwable?) {
            bookList.postValue(
                Resource.error(t)
            )
        }
    }
}