package android.learn.utils

sealed class State

class Error: State()

class Progress: State()

class Result<out T>(val result: T): State()