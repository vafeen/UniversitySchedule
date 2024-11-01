package ru.vafeen.universityschedule.domain.converters

import android.util.Log
import ru.vafeen.universityschedule.data.network.parcelable.github_service.ReleaseDTO
import ru.vafeen.universityschedule.domain.converters.base.BaseConverter
import ru.vafeen.universityschedule.domain.database.models.Release

internal class ReleaseConverter : BaseConverter<ReleaseDTO, Release> {
    override fun convertEntityDTO(e: ReleaseDTO): Release {
        Log.e("release", "начали3")
        Log.d("release", e.toString())
        return Release(
            tagName = e.tagName,
            assets = e.assets.map {
                it.url
            }
        )
    }

    override fun convertDTOEntity(d: Release): ReleaseDTO {
        throw Exception("ru.vafeen.universityschedule.domain.converters.ReleaseConverter::convertDTOEntity body not yet implemented")
    }

}