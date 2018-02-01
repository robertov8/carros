package br.com.livroandroid.carros.domain

import android.util.Log
import br.com.livroandroid.carros.extensions.fromJson
import java.net.URL

object CarroService {
    private val TAG = "livro"

    // Busca os carros por tipo (clássicos, esportivos ou luxo)
    fun getCarros(tipo: TipoCarro): List<Carro> {
        val url = "http://livrowebservices.com.br/rest/carros/tipo/${tipo.name}"
        Log.d(TAG, url)
        val json = URL(url).readText()
        val carros = fromJson<List<Carro>>(json)
        Log.d(TAG, "${carros.size} carros encontrados.")
        return carros
    }
}