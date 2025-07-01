package com.kabigon.weatherforecast.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults.Container
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kabigon.weatherforecast.ui.theme.Purple80
import com.kabigon.weatherforecast.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String? = null,
    onValueChange: (String) -> Unit,
    onClear: () -> Unit = {},
    placeholder: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent,
        errorSupportingTextColor = Color.Red,
        unfocusedBorderColor = Color.Black,
        focusedBorderColor = Color.Black,
        errorBorderColor = Color.Red,
        disabledBorderColor = Color.Transparent,
        disabledContainerColor = Color.Gray,
        cursorColor = Purple80,
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    BasicTextField(
        value = value ?: "",
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default,
        keyboardActions = KeyboardActions.Default,
        modifier = modifier.fillMaxWidth()
    ) { decoration ->
        OutlinedTextFieldDefaults.DecorationBox(
            value = value ?: "",
            innerTextField = decoration,
            singleLine = true,
            enabled = true,
            placeholder = {
                placeholder?.let { placeholder ->
                    Text(
                        text = placeholder,
                        style = Typography.bodyLarge,
                        color = Color.LightGray,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            },
            contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                start = 8.dp,
                end = 8.dp,
                top = 8.dp,
                bottom = 8.dp
            ),
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = android.R.drawable.ic_search_category_default),
                    contentDescription = ""
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = onClear
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier
                            .background(
                                color = Color.LightGray,
                                shape = CircleShape
                            )
                            .size(16.dp)
                            .padding(2.dp)
                    )
                }
            },
            interactionSource = interactionSource,
            colors = colors,
            visualTransformation = visualTransformation,
            container = {
                Container(
                    enabled = true,
                    isError = false,
                    interactionSource = interactionSource,
                    colors = colors,
                    shape = CircleShape,
                    focusedBorderThickness = 1.dp,
                    unfocusedBorderThickness = 1.dp,
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldPreview() {
    CustomTextField(
        value = "London",
        onValueChange = {},
        placeholder = "Type Here"
    )
}