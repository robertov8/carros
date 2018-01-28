package br.com.livroandroid.carros.fragments

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.livroandroid.carros.R
import br.com.livroandroid.carros.adapter.CarroAdapter
import br.com.livroandroid.carros.domain.Carro
import br.com.livroandroid.carros.domain.CarroService
import br.com.livroandroid.carros.domain.TipoCarro
import br.com.livroandroid.carros.extensions.toast

class CarrosFragment : BaseFragment() {
    private var tipo: TipoCarro = TipoCarro.classicos
    private var carros = listOf<Carro>()
    var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Lê o parâmetro tipo enviado (clássicos, esportivos ou luxo)
        tipo = arguments.getSerializable("tipo") as TipoCarro
    }

    // Cria a view do fragment
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_carros, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Views
        recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.setHasFixedSize(true)
    }

    override fun onResume() {
        super.onResume()
        taskCarros()
    }

    private fun taskCarros() {
        // Busca os carros
        this.carros = CarroService.getCarros(context, tipo)
        // Atualiza a lista
        recyclerView?.adapter = CarroAdapter(carros, {
            carro: Carro -> toast("@Clicou no carro ${carro.nome}")
        })
    }
}
