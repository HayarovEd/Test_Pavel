package com.edurda77.test_pavel.ui.main_screen

sealed class MainEvent {
    data object OnSearch : MainEvent()
    class SetQuory(val query:String):MainEvent()
}