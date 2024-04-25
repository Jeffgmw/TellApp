
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun EditScannedDataScreen(navController: NavController, qrCode: String) {

    // Assuming the scanned data is in the format "key1:value1,key2:value2,..."
    val scannedData = qrCode.split(",").associate {
        val (key, value) = it.split(":")
        key to value
    }

    // Create a mutable state for each key-value pair
    var formData by remember { mutableStateOf(scannedData.toMutableMap()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Transaction Data",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Create an OutlinedTextField for each key-value pair
        formData.entries.forEachIndexed { index, entry ->
            val (key, value) = entry
            var fieldValue by remember { mutableStateOf(value) }

            Text(
                text = key,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = fieldValue,
                onValueChange = { newValue ->
                    fieldValue = newValue
                    formData[key] = newValue
                },
                label = { Text("") },
                modifier = Modifier.fillMaxWidth().height(60.dp),  // Set the height of the TextField
                enabled = index >= 6,  // Disable the six three text fields
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.DarkGray,
                    unfocusedBorderColor = Color.Gray
                ),
                shape = RoundedCornerShape(10.dp)
            )
        }

        Button(
            onClick = {
                // Combine the edited values with their keys
                val editedData = formData.map { "${it.key}:${it.value}" }.joinToString(",")
                // Navigate back and pass the edited data
                navController.previousBackStackEntry?.arguments?.putString("editedData", editedData)
                navController.popBackStack()
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
        ) {
            Text("Submit")
        }
    }
}



