package br.com.livroandroid.carros.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import br.com.livroandroid.carros.R
import br.com.livroandroid.carros.domain.Carro
import br.com.livroandroid.carros.extensions.loadUrl
import br.com.livroandroid.carros.extensions.setupToolbar
import kotlinx.android.synthetic.main.activity_carro.*
import kotlinx.android.synthetic.main.activity_carro_contents.*
import org.jetbrains.anko.startActivity

class CarroActivity : BaseActivity() {
    val carro by lazy { intent.getParcelableExtra<Carro>("carro") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carro)

        // Seta o nome do carro como título na ToolBar
        setupToolbar(R.id.toolbar, carro.nome, true)
        // Atualiza os dados do carro na tela
        initViews()
    }

    private fun initViews() {
        // Variáveis geradas automaticamente pelo Kotlin Extensions (veja import)
        tDesc.text = carro.desc
        appBarImg.loadUrl(carro.urlFoto)
    }

    // Adiciona as opções de Salvar e Deletar no menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_carro, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_editar -> {
                startActivity<CarroFormActivity>("carro" to carro)
                finish()
            }
            R.id.action_deletar -> {
                TODO("deletar o carro")
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
