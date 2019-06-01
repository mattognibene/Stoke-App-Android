package com.stokeapp.stoke.domain.interactor

import io.reactivex.Completable

abstract class CompletableUseCase<in T> : BaseUseCase<T, Completable>()