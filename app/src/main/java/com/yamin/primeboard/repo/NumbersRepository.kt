package com.yamin.primeboard.repo

import androidx.lifecycle.LiveData
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.yamin.primeboard.model.NumberItem
import com.yamin.primeboard.utils.getPrimeFactors
import io.reactivex.Single
import java.util.concurrent.Executor

class NumbersRepository(
    private val dataFactory: NumbersDataSourceFactory,
    private val fetchExecutor: Executor
) {
    fun getNumberItems(pageSize: Int): LiveData<PagedList<NumberItem>> {
        return dataFactory.toLiveData(
            fetchExecutor = fetchExecutor,
            config = Config(
                pageSize = pageSize,
                enablePlaceholders = false,
                initialLoadSizeHint = pageSize
            )
        )
    }

    fun setSelectedNumber(selectedNumber: NumberItem?, initialNumber: Int) {
        dataFactory.setSelectedNumber(selectedNumber, initialNumber)
    }

    fun getFactors(number: Int): Single<Pair<Int, List<Int>>> {
        return Single.create { emitter ->
            val factors = number.getPrimeFactors()
            emitter.onSuccess(Pair(number, factors))
        }
    }
}