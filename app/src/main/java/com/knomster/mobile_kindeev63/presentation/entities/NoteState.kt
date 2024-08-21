package com.knomster.mobile_kindeev63.presentation.entities

import androidx.compose.ui.text.input.TextFieldValue
import java.io.Serializable

data class NoteState(
    val title: TextFieldValue,
    val text: TextFieldValue,
    val date: Long?,
    val pinnedPlaceId: String?
): Serializable {
    companion object {
        val empty = NoteState(
            TextFieldValue(),
            TextFieldValue(),
            null,
            null
        )
    }
}
