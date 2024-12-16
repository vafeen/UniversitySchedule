package ru.vafeen.universityschedule.presentation.components.bottom_sheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import ru.vafeen.universityschedule.domain.models.Release
import ru.vafeen.universityschedule.domain.network.service.Downloader
import ru.vafeen.universityschedule.presentation.theme.FontSize
import ru.vafeen.universityschedule.presentation.theme.updateAvailableColor
import ru.vafeen.universityschedule.resources.R

/**
 * Компонент нижнего модального окна для обновления приложения.
 *
 * Этот компонент отображает информацию о доступном обновлении и предоставляет
 * возможность загрузки новой версии приложения. Пользователь может закрыть это окно
 * по своему усмотрению.
 *
 * @param release Объект [Release], содержащий информацию о новой версии.
 * @param state Состояние модального окна [SheetState].
 * @param onDismissRequest Функция, вызываемая при закрытии модального окна.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun UpdaterBottomSheet(
    release: Release,
    state: SheetState,
    onDismissRequest: (Boolean) -> Unit
) {
    val context = LocalContext.current // Получение текущего контекста.
    val downloader = koinInject<Downloader>() // Внедрение зависимости Downloader.

    // Отображение модального нижнего окна.
    ModalBottomSheet(
        sheetState = state,
        onDismissRequest = { onDismissRequest(false) },
        containerColor = updateAvailableColor,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize(0.8f)
                .padding(10.dp),
        ) {
            // Заголовок с эмодзи.
            Text(
                text = ":)",
                fontSize = FontSize.gigant,
                modifier = Modifier.align(Alignment.Start),
                color = Color.White
            )
            Spacer(modifier = Modifier.height(10.dp)) // Отступ между элементами.

            // Сообщение о необходимости обновления.
            Text(
                text = stringResource(id = R.string.update_need),
                fontSize = FontSize.huge27, color = Color.White
            )
            Spacer(modifier = Modifier.height(40.dp)) // Отступ между элементами.

            // Инструкция по просмотру релизов.
            Text(
                text = stringResource(id = R.string.view_releases),
                fontSize = FontSize.huge27, color = Color.White
            )
            Spacer(modifier = Modifier.height(40.dp)) // Отступ между элементами.

            // Изображение для загрузки обновления.
            Image(
                painter = painterResource(id = R.drawable.releases_win),
                contentDescription = stringResource(R.string.icon_updating_qr),
                modifier = Modifier
                    .clickable {
                        downloader.downloadApk(
                            url = "vafeen/UniversitySchedule/releases/download/${release.tagName}/${release.assets[0]}",
                        )
                        onDismissRequest(true) // Закрытие окна после начала загрузки.
                    }
                    .size(150.dp)
                    .align(Alignment.CenterHorizontally) // Центрирование изображения.
            )
        }
    }
}