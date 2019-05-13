package com.bertan.esocial.extension

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

fun AppCompatActivity.showMessage(message: String) =
        findViewById<View>(android.R.id.content)?.let { rootView ->
            Snackbar
                    .make(
                            rootView,
                            message,
                            Snackbar.LENGTH_SHORT
                    )
                    .show()
        }