package ru.vafeen.universityschedule.domain.converter


interface BaseConverter<A, B> {
    fun convertAB(a: A): B
    fun convertBA(b: B): A
    fun convertABList(listD: List<A>): List<B> = listD.map {
        convertAB(it)
    }

    fun convertBAList(listM: List<B>): List<A> = listM.map {
        convertBA(it)
    }
}