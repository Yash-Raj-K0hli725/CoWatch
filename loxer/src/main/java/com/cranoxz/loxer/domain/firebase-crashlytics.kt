package com.cranoxz.loxer.domain

import android.app.Application
import com.google.firebase.FirebaseApp

fun Application.initFirebase() {
    FirebaseApp.initializeApp(this)
}