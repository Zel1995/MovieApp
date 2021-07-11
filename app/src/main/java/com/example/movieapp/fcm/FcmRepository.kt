package com.example.movieapp.fcm

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class FcmRepository @Inject constructor() {
    suspend fun getToken(): String = suspendCoroutine {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                it.resumeWith(Result.failure(task.exception!!))
                return@OnCompleteListener
            }

            val token = task.result
            it.resumeWith(Result.success(token.orEmpty()))
        })
    }
}