package ru.vafeen.universityschedule.presentation.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import ru.vafeen.universityschedule.resources.R

/**
 * Функция для отправки email на указанный адрес.
 * При отправке письма используется шаблон для темы, в которой указана информация о баг-отчете с названием приложения.
 *
 * @param email Адрес получателя для отправки письма.
 */
internal fun Context.sendEmail(email: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data =
            Uri.parse("mailto:$email?subject=${Uri.encode("BugReport ${getString(R.string.app_name)}")}")
    }
    startActivity(intent)
}

/**
 * Функция для копирования текста в буфер обмена.
 * Текст копируется с указанной меткой (label), которая используется для идентификации данных в буфере обмена.
 *
 * @param label Метка, которая будет присвоена тексту в буфере обмена.
 * @param text Текст, который будет скопирован в буфер обмена.
 */
internal fun Context.copyTextToClipBoard(label: String, text: String) {
    val clipboard =
        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    if (text.isNotEmpty()) clipboard.setPrimaryClip(clip)
}

/**
 * Функция для открытия ссылки в браузере.
 * Принимает строку с URL и открывает ее в браузере по умолчанию.
 *
 * @param link Ссылка, которую необходимо открыть.
 */
internal fun Context.openLink(link: String) {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
}

/**
 * Функция для получения текста из буфера обмена.
 * Если в буфере обмена есть текст, то он возвращается в виде строки.
 *
 * @return Строка с текстом из буфера обмена или null, если текст отсутствует.
 */
fun Context.pasteText(): String? {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    return clipboard.primaryClip?.getItemAt(0)?.text?.toString()
}
