package com.carwale.covidapp.utils.extensions


fun Int?.lesserThan(number: Int): Boolean {
    if(this != null) {
        return this < number
    }
    return false
}

fun Int?.greaterThan(number: Int): Boolean {
    if(this != null) {
        return this > number
    }
    return false
}

fun Long?.greaterThan(number: Int): Boolean {
    if(this != null) {
        return this > number
    }
    return false
}

fun Double?.greaterThan(number: Int): Boolean {
    if(this != null) {
        return this > number
    }
    return false
}

fun Double?.greaterThan(number: Long): Boolean {
    if(this != null) {
        return this > number
    }
    return false
}

fun Double?.greaterThan(number: Double): Boolean {
    if(this != null) {
        return this > number
    }
    return false
}