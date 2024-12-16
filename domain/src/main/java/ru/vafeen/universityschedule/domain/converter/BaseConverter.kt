package ru.vafeen.universityschedule.domain.converter

/**
 * Интерфейс для преобразования объектов одного типа в другой и обратно.
 *
 * @param A Тип исходного объекта.
 * @param B Тип целевого объекта.
 */
interface BaseConverter<A, B> {

    /**
     * Преобразует объект типа A в объект типа B.
     *
     * @param a Исходный объект типа A.
     * @return Преобразованный объект типа B.
     */
    fun convertAB(a: A): B

    /**
     * Преобразует объект типа B в объект типа A.
     *
     * @param b Исходный объект типа B.
     * @return Преобразованный объект типа A.
     */
    fun convertBA(b: B): A

    /**
     * Преобразует список объектов типа A в список объектов типа B.
     *
     * @param listD Список объектов типа A.
     * @return Список преобразованных объектов типа B.
     */
    fun convertABList(listD: List<A>): List<B> = listD.map {
        convertAB(it)
    }

    /**
     * Преобразует список объектов типа B в список объектов типа A.
     *
     * @param listM Список объектов типа B.
     * @return Список преобразованных объектов типа A.
     */
    fun convertBAList(listM: List<B>): List<A> = listM.map {
        convertBA(it)
    }
}
