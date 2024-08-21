package com.knomster.mobile_kindeev63.presentation.navigation

sealed class Screen(
    val route: String
) {

    data object BottomNavigation: Screen(ROUTE_BOTTOM_NAVIGATION_SCREEN)
    data object PlacesListScreen: Screen(ROUTE_PLACES_LIST_SCREEN)
    data object NotesListScreen: Screen(ROUTE_NOTES_LIST_SCREEN)
    data object AccountScreen: Screen(ROUTE_ACCOUNT_SCREEN)
    data object PlaceDetailsScreen: Screen(ROUTE_PLACE_DETAILS_SCREEN) {
        private const val ROUTE_FOR_ARGS = "place_details_screen"

        fun getRouteWithArgs(placeId: String) = "$ROUTE_FOR_ARGS/$placeId"
    }
    data object NoteEditScreen: Screen(ROUTE_NOTE_EDIT_SCREEN) {
        private const val ROUTE_FOR_ARGS = "note_edit_screen"

        fun getRouteWithArgs(noteId: Int?) = "$ROUTE_FOR_ARGS/$noteId"
    }

    private companion object {
        const val ROUTE_BOTTOM_NAVIGATION_SCREEN = "bottom_navigation_screen"
        const val ROUTE_PLACES_LIST_SCREEN = "places_list_screen"
        const val ROUTE_ACCOUNT_SCREEN = "account_screen"
        const val ROUTE_NOTES_LIST_SCREEN = "notes_list_screen"
        const val ROUTE_PLACE_DETAILS_SCREEN = "place_details_screen/{placeId}"
        const val ROUTE_NOTE_EDIT_SCREEN = "note_edit_screen/{noteId}"
    }
}
