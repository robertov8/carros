package br.com.livroandroid.carros.fragments

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.livroandroid.carros.R
import br.com.livroandroid.carros.activity.CarroActivity
import br.com.livroandroid.carros.adapter.CarroAdapter
import br.com.livroandroid.carros.domain.Carro
import br.com.livroandroid.carros.domain.CarroService
import br.com.livroandroid.carros.domain.TipoCarro
import br.com.livroandroid.carros.domain.event.SaveCarroEvent
import br.com.livroandroid.carros.extensions.toast
import br.com.livroandroid.carros.utils.AndroidUtils
import kotlinx.android.synthetic.main.fragment_carros.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread

open class CarrosFragment : BaseFragment() {
    private var tipo: TipoCarro = TipoCarro.classicos
    protected var carros = listOf<Carro>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Lê o parâmetro tipo enviado (clássicos, esportivos ou luxo)
        if (arguments != null) {
            tipo = arguments.getSerializable("tipo") as TipoCarro
        }
        // Registra os eventos do bus
        EventBus.getDefault().register(this)
    }

    // Cria a view do fragment
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_carros, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Views
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setHasFixedSize(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val internetOk = AndroidUtils.isNetworkAvailable(context)

        if (!internetOk) {
            toast("Sem acesso a internet.", Toast.LENGTH_LONG)
        } else {
            taskCarros()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cancela os eventos do bus
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onRefresh(event: SaveCarroEvent) {
        // Recebe p evento do bus
        taskCarros()
        Log.d("event", "receive SaveCarroEvent")
    }

    open fun taskCarros() {
        // Abre uma thread
        doAsync {
            // Busca os carros
            carros = CarroService.getCarros(tipo)
            // Atualiza a lista na UI Thread
            uiThread {
                // Atualiza a lista
                recyclerView.adapter = CarroAdapter(carros) { onClickCarro(it)}
            }
        }
    }

    open fun onClickCarro(carro: Carro) {
        // Ao clicar no carro vamos navegar para a tela de detalhes[
        activity.startActivity<CarroActivity>("carro" to carro)
    }
}
