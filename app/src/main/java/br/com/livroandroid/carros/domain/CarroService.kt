package br.com.livroandroid.carros.domain

import android.content.Context

class CarroService {
    companion object {
        // Busca os carros por tipo (clássicos, esportivos ou luxo)
        fun getCarros(context: Context, tipo: TipoCarro): List<Carro> {
            val tipoString = context.getString(tipo.string)

            // Cria um array vazio de carros
            val carros = mutableListOf<Carro>()
            // Cria 20 carros
            for (i in 1..20) {
                val c = Carro()
                // Nome do carro dinâmico para brincar
                c.nome = "Carro $tipoString: $i"
                c.desc = "Desc $i"
                // URL da fixa por enquanto
                c.urlFoto = "http://livroandroid.com.br/livro/carros/esportivos/Ferrari_FF.png"
                carros.add(c)
            }

            return carros
        }
    }
}