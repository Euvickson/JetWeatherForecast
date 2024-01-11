package br.com.euvickson.jetweatherforecast.data

class DataOrException<T, B: Boolean, E: Exception>(
    var data: T? = null,
    var loading: B? = null,
    var e: E? = null
) {
}