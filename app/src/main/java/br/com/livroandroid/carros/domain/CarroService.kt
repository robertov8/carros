package br.com.livroandroid.carros.domain

import br.com.livroandroid.carros.extensions.fromJson
import br.com.livroandroid.carros.extensions.toJson
import br.com.livroandroid.carros.utils.HttpHelper

object CarroService {
    private val BASE_URL = "http://livrowebservices.com.br/rest/carros"

    // Busca os carros por tipo (clássicos, esportivos ou luxo)
    fun getCarros(tipo: TipoCarro): List<Carro> {
        val url = "$BASE_URL/tipo/${tipo.name}"
        val json = HttpHelper.get(url)
        return fromJson<List<Carro>>(json)
    }

    // Salva um carro
    fun save(carro: Carro): Response {
        // Faz POST do JSON carro
        val json = HttpHelper.post(BASE_URL, carro.toJson())
        return fromJson<Response>(json)
    }

    // Deleta um carro
    fun delete(carro: Carro): Response {
        val url = "$BASE_URL/${carro.id}"
        val json = HttpHelper.delete(url)
        return fromJson<Response>(json)
    }
}