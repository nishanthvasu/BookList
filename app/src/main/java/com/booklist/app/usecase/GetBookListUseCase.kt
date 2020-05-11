package com.booklist.app.usecase

import com.booklist.app.domain.base.FlowableUseCase
import com.booklist.app.domain.model.Book
import com.booklist.app.domain.repository.BookRepository
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class GetBookListUseCase(
    private val bookRepository: BookRepository,
    processScheduler: Scheduler = Schedulers.io(),
    mainScheduler: Scheduler = AndroidSchedulers.mainThread()
) : FlowableUseCase<List<Book>, Void?>(processScheduler, mainScheduler) {

    override fun buildUseCaseObservable(params: Void?): Flowable<List<Book>> {
        return bookRepository.getBookList()
    }
}