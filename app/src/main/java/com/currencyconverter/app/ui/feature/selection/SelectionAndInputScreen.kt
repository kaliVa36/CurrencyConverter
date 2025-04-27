package com.currencyconverter.app.ui.feature.selection

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.IntSize
import com.currencyconverter.app.R
import com.currencyconverter.app.ui.PaddingValues

@Composable
fun SelectionAndInputScreen(
    data: SelectionAndInputData,
    onValueChanged: (String) -> Unit,
    onContinueClicked: () -> Unit,
    onItemFromCurrencySelected: (String) -> Unit = {},
    onItemToCurrencySelected: (String) -> Unit = {},
    resetError: () -> Unit,
) {
    var parentSize by remember { mutableStateOf(IntSize.Zero) }
    var expandedFromCurrency by remember { mutableStateOf(false) }
    var expandedToCurrency by remember { mutableStateOf(false) }

    val context = LocalContext.current
    if (data.error.isNotEmpty()) {
        Toast.makeText(context, data.error, Toast.LENGTH_SHORT).show()
        resetError()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues.padding_16)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        parentSize = coordinates.size
                    },
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "${stringResource(R.string.from)} ${data.selectedFromCurrency}",
                    modifier = Modifier.clickable { expandedFromCurrency = !expandedFromCurrency }
                )

                Spacer(Modifier.width(PaddingValues.padding_16))

                Text(
                    text = "${stringResource(R.string.to)} ${data.selectedToCurrency}",
                    modifier = Modifier.clickable {  expandedToCurrency = !expandedToCurrency }
                )
            }

            DropdownMenu(
                modifier = Modifier.fillMaxWidth().width(with(LocalDensity.current) { parentSize.width.toDp() }),
                expanded = expandedFromCurrency || expandedToCurrency,
                onDismissRequest = { expandedFromCurrency = false }
            ) {
                data.currencies.forEach {
                    val item = it
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            if (expandedFromCurrency) {
                                onItemFromCurrencySelected(it)
                                expandedFromCurrency = false
                            }
                            if (expandedToCurrency) {
                                onItemToCurrencySelected(it)
                                expandedToCurrency = false
                            }
                        }
                    )
                }
            }
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
            onClick = { onContinueClicked() }
        ) {
            Text(stringResource(R.string.continue_btn))
        }
    }
}
