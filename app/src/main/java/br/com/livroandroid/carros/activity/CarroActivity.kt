package br.com.livroandroid.carros.activity

import android.content.res.ColorStateList
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import br.com.livroandroid.carros.R
import br.com.livroandroid.carros.domain.Carro
import br.com.livroandroid.carros.domain.CarroService
import br.com.livroandroid.carros.domain.FavoritosService
import br.com.livroandroid.carros.extensions.loadUrl
import br.com.livroandroid.carros.extensions.setupToolbar
import kotlinx.android.synthetic.main.activity_carro.*
import kotlinx.android.synthetic.main.activity_carro_contents.*
import org.jetbrains.anko.*

class CarroActivity : BaseActivity() {
    val carro by lazy { intent.getParcelableExtra<Carro>("carro") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carro)

        // Seta o nome do carro como título na ToolBar
        setupToolbar(R.id.toolbar, carro.nome, true)
        // Atualiza os dados do carro na tela
        initViews()
        // Variável gerada automaticamente pelo Kotlin Extensions
        fab.setOnClickListener { onClickFavoritar(carro) }
    }

    private fun initViews() {
        // Variáveis geradas automaticamente pelo Kotlin Extensions (veja import)
        tDesc.text = carro.desc
        appBarImg.loadUrl(carro.urlFoto)

        doAsync {
            val favorito = FavoritosService.isFavorito(carro)
            uiThread {
                setFavoriteColor(favorito)
            }
        }
    }

    // Adiciona as opções de Salvar e Deletar no menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_carro, menu)
        return true
    }

    // Tratar os eventos do menu
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_editar -> {
                startActivity<CarroFormActivity>("carro" to carro)
                finish()
            }
            R.id.action_deletar -> {
                alert(R.string.msg_confirma_excluir_carro, R.string.app_name) {
                    positiveButton(R.string.sim) {
                         // Confirmar o excluir
                        taskExcluir()
                    }
                    negativeButton(R.string.nao) {
                         // Não confirmou...
                    }
                }.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Excluir um carro do servidor
    private fun taskExcluir() {
        doAsync {
            val response = CarroService.delete(carro)

            uiThread {
                toast(response.msg)
                finish()
            }
        }
    }

    // Adiciona ou Remove o carro dos favoritos
    private fun onClickFavoritar(carro: Carro) {
        doAsync {
            val favoritado = FavoritosService.favoritar(carro)

            uiThread {
                // Alerta de sucesso
                val msg = if (favoritado) {
                    setFavoriteColor(favoritado)
                    R.string.msg_carro_favoritado
                } else {
                    setFavoriteColor(favoritado)
                    R.string.msg_carro_desfavoritado
                }

                toast(msg)
            }
        }
    }

    // Desenha a cor do FAB conforme está favoritado ou não
    private fun setFavoriteColor(favorito: Boolean) {
        // Troca a cor conforme o status do favoritos
        val fundo = ContextCompat.getColor(this, if (favorito)
                R.color.favorito_on
            else
                R.color.favorito_off)

        val cor = ContextCompat.getColor(this,
                if (favorito)
                    R.color.yellow
                else
                    R.color.favorito_on)
        fab.backgroundTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(fundo))
        fab.setColorFilter(cor)
    }
}
