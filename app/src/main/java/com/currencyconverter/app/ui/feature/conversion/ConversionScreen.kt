package com.currencyconverter.app.ui.feature.conversion

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.currencyconverter.app.R
import com.currencyconverter.app.ui.PaddingValues

@Composable
fun ConversionScreen(
    data: ConversionData,
    onValueChanged: (String) -> Unit,
    onConvertClicked: () -> Unit,
    onSwitchClicked: () -> Unit,
    onBack: () -> Unit,
    resetError: () -> Unit,
) {
    BackHandler { onBack() }

    val context = LocalContext.current
    if (data.error.isNotEmpty()) {
        Toast.makeText(context, data.error, Toast.LENGTH_SHORT).show()
        resetError()
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues.padding_16)
            .verticalScroll(rememberScrollState()),
    ) {
        val (topBar, content) = createRefs()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(topBar) { top.linkTo(parent.top) }
        ) {
            Image(
                painter = painterResource(R.drawable.baseline_arrow_back_24),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onBack() }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(content) {
                    top.linkTo(topBar.bottom)
                    bottom.linkTo(parent.bottom)
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = "${stringResource(R.string.from)} ${data.currencyFrom}")

                Spacer(Modifier.width(PaddingValues.padding_16))

                Text(text = "${stringResource(R.string.to)} ${data.currencyTo}")
            }

            Spacer(Modifier.height(PaddingValues.padding_16))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onSwitchClicked() },
            ) {
                Text(stringResource(R.string.switch_btn))
            }

            Spacer(Modifier.height(PaddingValues.padding_16))

            TextField(
                value = data.amount,
                onValueChange = { value -> onValueChanged(value) },
                label = { Text(stringResource(R.string.amount_to_convert)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                maxLines = 1,
            )

            Spacer(Modifier.height(PaddingValues.padding_16))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onConvertClicked() },
                enabled = data.amount.isNotEmpty() && data.amount != "0"
            ) {
                Text(stringResource(R.string.convert))
            }

            Spacer(Modifier.height(PaddingValues.padding_16))

            Text(text = "${stringResource(R.string.converted_value)} ${data.convertedAmount}")
        }
    }
}
