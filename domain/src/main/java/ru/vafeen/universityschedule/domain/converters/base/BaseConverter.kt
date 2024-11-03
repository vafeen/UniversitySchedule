package ru.vafeen.universityschedule.domain.converters.base


internal interface BaseConverter<A, B> {
    fun convertAB(a: A): B
    fun convertBA(b: B): A
    fun convertABList(listD: Iterable<A>): Iterable<B> = listD.map {
        convertAB(it)
    }

    fun convertBAList(listM: Iterable<B>): Iterable<A> = listM.map {
        convertBA(it)
    }
}