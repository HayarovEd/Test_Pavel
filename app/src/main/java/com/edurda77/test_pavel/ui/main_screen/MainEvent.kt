package com.edurda77.test_pavel.ui.main_screen

sealed class MainEvent {
    class OnSearch(val query:String):MainEvent()
}