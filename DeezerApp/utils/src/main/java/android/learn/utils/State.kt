package android.learn.utils

sealed class State<T>

class Error<T>: State<T>()

class Progress<T>: State<T>()

class Result<T>(val result: T): State<T>()