package com.edurda77.test_pavel.ui.main_screen

sealed class MainEvent {
    data object OnSearch : MainEvent()
    class SetQuery(val query:String):MainEvent()
}