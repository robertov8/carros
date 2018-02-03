package br.com.livroandroid.carros.extensions

import android.view.View

// Utilizar onClick ao invés de setOnClickListener
fun View.onClick(l: (v: View?) -> Unit) {
    setOnClickListener(l)
}
