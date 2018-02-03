package br.com.livroandroid.carros.domain

import android.util.Base64
import br.com.livroandroid.carros.domain.dao.DatabaseManager
import br.com.livroandroid.carros.domain.retrofit.CarrosREST
import br.com.livroandroid.carros.extensions.fromJson
import br.com.livroandroid.carros.utils.HttpHelper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

object CarroService {
    private val BASE_URL = "http://livrowebservices.com.br/rest/carros/"
    private var service: CarrosREST

    init {
        val retrofit = Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        service = retrofit.create(CarrosREST::class.java)
    }

    // Busca os carros por tipo (clássicos, esportivos ou luxo)
    fun getCarros(tipo: TipoCarro): List<Carro> {
        val call = service.getCarros(tipo.name)
        return call.execute().body()
    }

    // Salva um carro
    fun save(carro: Carro): Response {
        // Faz POST do JSON carro
        val call = service.save(carro)
        return call.execute().body()
    }

    // Deleta um carro
    fun delete(carro: Carro): Response {
        val call = service.delete(carro.id)
        val response = call.execute().body()
        if (response.isOk()) {
            // Se removeu do servidor, remove dos favoritos
            val dao = DatabaseManager.getCarroDAO()
            dao.delete(carro)
        }
        return response
    }

    fun postFoto(file: File): Response {
        val url = "${BASE_URL}postFotoBase64"
        // Converte para Base64
        val bytes = file.readBytes()
        val base64 = Base64.encodeToString(bytes, Base64.NO_WRAP)
        val params = mapOf("fileName" to file.name, "base64" to base64)
        val json = HttpHelper.postForm(url, params)
        return fromJson<Response>(json)
    }
}