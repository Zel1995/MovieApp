package com.example.movieapp

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.snack(msg:R.string){
    Snackbar.make(this,msg.toString(),Snackbar.LENGTH_SHORT).show()
}
fun View.snack(msg:String){
    Snackbar.make(this,msg,Snackbar.LENGTH_SHORT).show()
}