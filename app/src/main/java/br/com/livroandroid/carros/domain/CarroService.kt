package br.com.livroandroid.carros.domain

import android.content.Context
import br.com.livroandroid.carros.R
import br.com.livroandroid.carros.extensions.fromJson

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
            // Converte o JSON para List<Carro>
            return fromJson<List<Carro>>(json)
        }
    }

    // Retorna o arquivo que tempo que ler para o tipo informado
    fun getArquivoRaw(tipo: TipoCarro) = when(tipo) {
        TipoCarro.classicos -> R.raw.carros_classicos
        TipoCarro.esportivos -> R.raw.carros_esportivos
        else -> R.raw.carros_luxo
    }
}