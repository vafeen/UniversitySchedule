package ru.vafeen.universityschedule.data.converters

import ru.vafeen.universityschedule.data.network.dto.github_service.ReleaseDTO
import ru.vafeen.universityschedule.domain.converter.BaseConverter
import ru.vafeen.universityschedule.domain.models.Release

internal class ReleaseConverter : BaseConverter<ReleaseDTO, Release> {

    override fun convertAB(a: ReleaseDTO): Release = Release(
        tagName = a.tagName,
        assets = a.assets.map {
            it.name
        }
    )

    override fun convertBA(b: Release): ReleaseDTO {
        throw Exception("ru.vafeen.universityschedule.domain.converters.ReleaseConverterImpl::convertBA body not yet implemented")
    }

}