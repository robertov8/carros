package br.com.livroandroid.carros.domain

import android.content.Context
import android.util.Log
import br.com.livroandroid.carros.R
import org.json.JSONArray

object CarroService {
    private val TAG = "livro"

    // Busca os carros por tipo (clássicos, esportivos ou luxo)
    fun getCarros(context: Context, tipo: TipoCarro): List<Carro> {
        // Este é o arquivo que temos que ler
        val raw = getArquivoRaw(tipo)

        // Abre o arquivo para leitura
        val resources = context.resources
        val inputStream = resources.openRawResource(raw)
        inputStream.bufferedReader().use {
            // Lê o JSON e cria a lista de carros
            val json = it.readText()
            return parseJson(json)
        }
    }

    // Retorna o arquivo que tempo que ler para o tipo informado
    fun getArquivoRaw(tipo: TipoCarro) = when(tipo) {
        TipoCarro.classicos -> R.raw.carros_classicos
        TipoCarro.esportivos -> R.raw.carros_esportivos
        else -> R.raw.carros_luxo
    }

    private fun parseJson(json: String): List<Carro> {
        val carros = mutableListOf<Carro>()
        // Cria um array com ese JSON
        val array = JSONArray(json)
        // Percorre cada carro (JSON)
        for (i in 0..array.length() - 1) {
            // JSON do carro
            val jsonCarro = array.getJSONObject(i)
            val c = Carro()
            // Lê as informações de cada carro
            c.nome = jsonCarro.optString("nome")
            c.desc = jsonCarro.optString("desc")
            c.urlFoto = jsonCarro.optString("url_foto")
            carros.add(c)
        }
        Log.d(TAG, "${carros.size} carros encontrados.")
        return carros
    }
}