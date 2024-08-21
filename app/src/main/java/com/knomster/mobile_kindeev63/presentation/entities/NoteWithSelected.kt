package com.knomster.mobile_kindeev63.presentation.entities

import com.knomster.mobile_kindeev63.domain.entities.database.Note
import java.io.Serializable

data class NoteWithSelected(val note: Note, val selected: Boolean) : Serializable