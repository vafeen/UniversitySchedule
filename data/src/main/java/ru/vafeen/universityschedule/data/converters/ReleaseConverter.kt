package ru.vafeen.universityschedule.data.converters

import ru.vafeen.universityschedule.data.network.dto.github_service.ReleaseDTO
import ru.vafeen.universityschedule.domain.converter.BaseConverter
import ru.vafeen.universityschedule.domain.models.Release

/**
 * Конвертер для преобразования [ReleaseDTO] в [Release].
 *
 * Данный конвертер предназначен для преобразования данных о релизах,
 * полученных с сервера, в доменную модель, используемую в приложении.
 * Обратное преобразование не реализовано, так как это не предусмотрено логикой приложения.
 */
internal class ReleaseConverter : BaseConverter<ReleaseDTO?, Release?> {

    /**
     * Преобразует [ReleaseDTO] в [Release].
     *
     * @param a Экземпляр [ReleaseDTO], полученный из сетевого слоя.
     * @return Экземпляр [Release], используемый в доменном уровне приложения.
     */
    override fun convertAB(a: ReleaseDTO?): Release? {
        val apk = a?.assets?.find {
            it.browserDownloadUrl.contains(".apk")
        }
        return if (a != null && apk != null) {
            Release(
                tagName = a.tagName,
                apkUrl = apk.browserDownloadUrl,
                size = apk.size,
                body = a.body
            )
        } else null
    }

    /**
     * Преобразование [Release] в [ReleaseDTO] не реализовано.
     *
     * @param b Экземпляр [Release].
     * @throws Exception Всегда выбрасывает исключение, так как обратное преобразование
     * недоступно из-за особенностей логики приложения.
     */
    override fun convertBA(b: Release?): ReleaseDTO? {
        throw Exception("ru.vafeen.universityschedule.domain.converters.ReleaseConverterImpl::convertBA body not yet implemented")
    }
}
