package me.timeto.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import me.timeto.app.VStack
import me.timeto.app.c
import me.timeto.app.roundedShape
import me.timeto.shared.vm.ui.DaytimePickerUi

@Composable
fun DaytimePickerSheet(
    layer: WrapperView.Layer,
    title: String,
    doneText: String,
    daytimePickerUi: DaytimePickerUi,
    withRemove: Boolean,
    onPick: (DaytimePickerUi) -> Unit,
    onRemove: () -> Unit,
) {

    val selectedHour = remember { mutableIntStateOf(daytimePickerUi.hour) }
    val selectedMinute = remember { mutableIntStateOf(daytimePickerUi.minute) }

    VStack(
        Modifier.background(c.sheetBg)
    ) {

        Sheet.HeaderViewOld(
            onCancel = { layer.close() },
            title = title,
            doneText = doneText,
            isDoneEnabled = true,
            scrollState = null,
        ) {
            val newDaytimePickerUi = DaytimePickerUi(
                hour = selectedHour.intValue,
                minute = selectedMinute.intValue,
            )
            onPick(newDaytimePickerUi)
            layer.close()
        }

        VStack(
            modifier = Modifier
                .padding(top = 12.dp, bottom = 20.dp)
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            DaytimePickerView(
                hour = selectedHour.intValue,
                minute = selectedMinute.intValue,
                onHourChanged = { hour -> selectedHour.intValue = hour },
                onMinuteChanged = { minute -> selectedMinute.intValue = minute },
            )

            if (withRemove) {
                Text(
                    text = "Remove",
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .clip(roundedShape)
                        .clickable {
                            onRemove()
                            layer.close()
                        }
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    color = c.red,
                )
            }
        }
    }
}
