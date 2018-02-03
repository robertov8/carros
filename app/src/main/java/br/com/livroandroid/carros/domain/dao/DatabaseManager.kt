package br.com.livroandroid.carros.domain.dao

import android.arch.persistence.room.Room
import br.com.livroandroid.carros.CarrosApplication

object DatabaseManager {
    // Singleton do Room: banco de dados
    private var dbInstance: CarrosDatabase

    init {
        val appContext = CarrosApplication.getInstance().applicationContext
         // Configura o Room
        dbInstance = Room.databaseBuilder(
                appContext, // Context global
                CarrosDatabase::class.java, // Referência da classe de Application
                "carros.sqlite").build() // Nome do arquivo do banco de dados
    }

    fun getCarroDAO(): CarroDAO {
        return dbInstance.carroDAO()
    }
}