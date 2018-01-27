package br.com.livroandroid.carros.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.livroandroid.carros.R
import br.com.livroandroid.carros.domain.TipoCarro

class CarrosFragment : BaseFragment() {
    private var tipo: TipoCarro = TipoCarro.classicos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Lê o parâmetro tipo enviado (clássicos, esportivos ou luxo)
        tipo = arguments.getSerializable("tipo") as TipoCarro
    }

    // Cria a view do fragment
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_carros, container, false)
        val textView = view?.findViewById<TextView>(R.id.text)

        // Converte o R.string.xxx em texto.
        val tipoString = getString(tipo.string)
        textView?.text = "Carros $tipoString"
        return view
    }
}
