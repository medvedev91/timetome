package me.timeto.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.timeto.app.*

private val menuTopPadding = 10.dp
private val menuBottomPadding = menuTopPadding.goldenRatioUp()

@Composable
fun EventsView() {

    VStack(
        modifier = Modifier
            .fillMaxSize(),
    ) {

        EventsListView(
            modifier = Modifier
                .weight(1f),
        )

        DividerBg(
            modifier = Modifier
                .padding(start = H_PADDING, end = TasksView__PADDING_END),
        )

        HStack(
            modifier = Modifier
                .padding(top = menuTopPadding, bottom = menuBottomPadding),
        ) {

            ModeButton(
                text = "Calendar",
                modifier = Modifier
                    .padding(start = H_PADDING - halfDp),
                isActive = true,
                onClick = {
                    // todo
                },
            )

            ModeButton(
                text = "List",
                modifier = Modifier
                    .padding(start = 8.dp),
                isActive = false,
                onClick = {
                    // todo
                },
            )
        }
    }
}

//
// Mode Button

private val modeButtonShape = SquircleShape(len = 50f)

@Composable
private fun ModeButton(
    text: String,
    modifier: Modifier,
    isActive: Boolean,
    onClick: () -> Unit,
) {

    Text(
        text = text,
        modifier = modifier
            .clip(shape = modeButtonShape)
            .clickable {
                onClick()
            }
            .background(if (isActive) c.blue else c.transparent)
            .padding(horizontal = 6.dp)
            .padding(top = halfDp, bottom = 1.dp),
        color = if (isActive) c.white else c.text,
        fontSize = 14.sp,
    )
}
