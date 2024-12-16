package ru.vafeen.universityschedule.data.network.dto.github_service

import com.google.gson.annotations.SerializedName

/**
 * Данные об авторе (uploader) из GitHub API.
 *
 * @property login Логин автора на GitHub.
 * @property id Уникальный идентификатор автора.
 * @property nodeId Идентификатор узла автора.
 * @property avatarUrl URL аватара автора.
 * @property gravatarId Идентификатор Gravatar автора.
 * @property url URL профиля автора на GitHub.
 * @property htmlUrl URL профиля автора в формате HTML.
 * @property followersUrl URL для получения подписчиков автора.
 * @property followingUrl URL для получения пользователей, на которых подписан автор.
 * @property gistsUrl URL для получения гистов автора.
 * @property starredUrl URL для получения звездочек, поставленных автором.
 * @property subscriptionsUrl URL для получения подписок автора.
 * @property organizationsUrl URL для получения организаций, в которых состоит автор.
 * @property reposUrl URL для получения репозиториев автора.
 * @property eventsUrl URL для получения событий, связанных с автором.
 * @property receivedEventsUrl URL для получения событий, полученных автором.
 * @property type Тип пользователя (например, "User" или "Organization").
 * @property siteAdmin Флаг, указывающий, является ли автор администратором сайта GitHub.
 */
internal data class AuthorDTO(
    @SerializedName("login") val login: String,
    @SerializedName("id") val id: Long,
    @SerializedName("node_id") val nodeId: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("gravatar_id") val gravatarId: String,
    @SerializedName("url") val url: String,
    @SerializedName("html_url") val htmlUrl: String,
    @SerializedName("followers_url") val followersUrl: String,
    @SerializedName("following_url") val followingUrl: String,
    @SerializedName("gists_url") val gistsUrl: String,
    @SerializedName("starred_url") val starredUrl: String,
    @SerializedName("subscriptions_url") val subscriptionsUrl: String,
    @SerializedName("organizations_url") val organizationsUrl: String,
    @SerializedName("repos_url") val reposUrl: String,
    @SerializedName("events_url") val eventsUrl: String,
    @SerializedName("received_events_url") val receivedEventsUrl: String,
    @SerializedName("type") val type: String,
    @SerializedName("site_admin") val siteAdmin: Boolean
)
