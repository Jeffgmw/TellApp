package com.teller.tellapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FinacleScriptForm(
    initialData: FinacleScriptData,
    onCancel: () -> Unit,
    onSubmit: (FinacleScriptData) -> Unit
) {
    var finacleScriptData by remember { mutableStateOf(initialData.copy()) }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FinacleScriptDataFields(finacleScriptData, onFieldChange = { field, value ->
            finacleScriptData = when (field) {
                FinacleScriptField.RequestID -> finacleScriptData.copy(requestId = value)
                FinacleScriptField.CreditTrantype -> finacleScriptData.copy(creditTrantype = value)
                FinacleScriptField.SolId -> finacleScriptData.copy(solId = value)
                FinacleScriptField.Debitcrncy -> finacleScriptData.copy(debitcrncy = value)
                FinacleScriptField.DebitPartls -> finacleScriptData.copy(debitPartls = value)
                FinacleScriptField.TransubType -> finacleScriptData.copy(transubType = value)
                FinacleScriptField.CreditPartls -> finacleScriptData.copy(creditPartls = value)
                FinacleScriptField.TranType -> finacleScriptData.copy(tranType = value)
                FinacleScriptField.Drforacid -> finacleScriptData.copy(drforacid = value)
                FinacleScriptField.DebitTrantype -> finacleScriptData.copy(debitTrantype = value)
                FinacleScriptField.Gldate -> finacleScriptData.copy(gldate = value)
                FinacleScriptField.TranDate -> finacleScriptData.copy(tranDate = value)
                FinacleScriptField.CreditAmt -> finacleScriptData.copy(creditAmt = value)
                FinacleScriptField.Valuedate -> finacleScriptData.copy(valuedate = value)
                FinacleScriptField.DebitAmt -> finacleScriptData.copy(debitAmt = value)
                FinacleScriptField.Creditcrncy -> finacleScriptData.copy(creditcrncy = value)
                FinacleScriptField.Crforacid -> finacleScriptData.copy(crforacid = value)
            }
        })

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(onClick = onCancel) {
                Text("Cancel")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { onSubmit(finacleScriptData) }) {
                Text("Submit")
            }
        }
    }
}

@Composable
fun FinacleScriptDataFields(data: FinacleScriptData, onFieldChange: (FinacleScriptField, String) -> Unit) {
    FinacleScriptField.values().forEach { field ->
        OutlinedTextField(
            value = getFieldData(data, field),
            onValueChange = { onFieldChange(field, it) },
            label = { Text(field.label) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

fun getFieldData(data: FinacleScriptData, field: FinacleScriptField): String {
    return when (field) {
        FinacleScriptField.RequestID -> data.requestId
        FinacleScriptField.CreditTrantype -> data.creditTrantype
        FinacleScriptField.SolId -> data.solId
        FinacleScriptField.Debitcrncy -> data.debitcrncy
        FinacleScriptField.DebitPartls -> data.debitPartls
        FinacleScriptField.TransubType -> data.transubType
        FinacleScriptField.CreditPartls -> data.creditPartls
        FinacleScriptField.TranType -> data.tranType
        FinacleScriptField.Drforacid -> data.drforacid
        FinacleScriptField.DebitTrantype -> data.debitTrantype
        FinacleScriptField.Gldate -> data.gldate
        FinacleScriptField.TranDate -> data.tranDate
        FinacleScriptField.CreditAmt -> data.creditAmt
        FinacleScriptField.Valuedate -> data.valuedate
        FinacleScriptField.DebitAmt -> data.debitAmt
        FinacleScriptField.Creditcrncy -> data.creditcrncy
        FinacleScriptField.Crforacid -> data.crforacid
    }
}

enum class FinacleScriptField(val label: String) {
    RequestID("Request ID"),
    CreditTrantype("Credit Transaction Type"),
    SolId("Sol ID"),
    Debitcrncy("Debit Currency"),
    DebitPartls("Debit Particulars"),
    TransubType("Transaction Sub Type"),
    CreditPartls("Credit Particulars"),
    TranType("Transaction Type"),
    Drforacid("Dr for Acid"),
    DebitTrantype("Debit Transaction Type"),
    Gldate("GL Date"),
    TranDate("Transaction Date"),
    CreditAmt("Credit Amount"),
    Valuedate("Value Date"),
    DebitAmt("Debit Amount"),
    Creditcrncy("Credit Currency"),
    Crforacid("Cr for Acid")
}

@Preview
@Composable
fun PreviewFinacleScriptForm() {
    FinacleScriptForm(
        initialData = FinacleScriptData(
            requestId = "HTM_ARIF_SRV_FI.scr",
            creditTrantype = "C",
            solId = "001",
            debitcrncy = "KES",
            debitPartls = "Debiting Fi",
            transubType = "BI",
            creditPartls = "Creditng Fi",
            tranType = "T",
            drforacid = "2009128015",
            debitTrantype = "D",
            gldate = "01-12-2023",
            tranDate = "01-12-2023",
            creditAmt = "100",
            valuedate = "01-12-2023",
            debitAmt = "100",
            creditcrncy = "KES",
            crforacid = "2000790014"
        ),
        onCancel = {},
        onSubmit = {}
    )
}

data class FinacleScriptData(
    val requestId: String,
    val creditTrantype: String,
    val solId: String,
    val debitcrncy: String,
    val debitPartls: String,
    val transubType: String,
    val creditPartls: String,
    val tranType: String,
    val drforacid: String,
    val debitTrantype: String,
    val gldate: String,
    val tranDate: String,
    val creditAmt: String,
    val valuedate: String,
    val debitAmt: String,
    val creditcrncy: String,
    val crforacid: String
)
