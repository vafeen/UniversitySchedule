package ru.vafeen.universityschedule.domain.converters.base


internal interface BaseConverter<Entity, DTO> {
    fun convertEntityDTO(e: Entity): DTO
    fun convertDTOEntity(d: DTO): Entity
    fun convertEntityDTOList(listD: Iterable<Entity>): Iterable<DTO> = listD.map {
        convertEntityDTO(it)
    }

    fun convertDTOEntityList(listM: Iterable<DTO>): Iterable<Entity> = listM.map {
        convertDTOEntity(it)
    }
}