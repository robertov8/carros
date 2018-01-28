package br.com.livroandroid.carros.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import br.com.livroandroid.carros.R
import br.com.livroandroid.carros.domain.Carro
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

// Define o construtor que recebe (carros, onClick)
class CarroAdapter (
    val carros: List<Carro>,
    val onClick: (Carro) -> Unit) : RecyclerView.Adapter<CarroAdapter.CarrosViewHolder>() {

    // ViewHolder com as views
    class CarrosViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Salva as views no ViewHolder
        var tNome: TextView = view.findViewById(R.id.tNome)
        var img: ImageView = view.findViewById(R.id.img)
        var progress: ProgressBar = view.findViewById(R.id.progress)
        var cardView: CardView = view.findViewById(R.id.card_view)
    }

    // Retorna a quantidade de carros na lista
    override fun getItemCount() = this.carros.size

    // Infla o layout do adapter e retorna o ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CarrosViewHolder {
        // Infla a view do adapter
        val view = LayoutInflater
                .from(parent?.context)
                .inflate(R.layout.adapter_carro, parent, false)

        // Retorna o ViewHolter que contém todas as vies
        return CarrosViewHolder(view)
    }

    // Faz o bind para atualizar o valor das views com os dados do Carro
    override fun onBindViewHolder(holder: CarrosViewHolder?, position: Int) {
        val context = holder?.itemView?.context

        // Recupera o objeto carro
        val carro = carros[position]

        // Atualiza os dados do carro
        holder?.tNome?.text = carro.nome
        holder?.progress?.visibility = View.VISIBLE

        // Faz o download da foto e mostra o ProgressBar
        Picasso.with(context).load(carro.urlFoto).fit().into(holder?.img, object : Callback {
            override fun onSuccess() {
                // Download OK
                holder?.progress?.visibility = View.GONE
            }

            override fun onError() {
                holder?.progress?.visibility = View.GONE
            }
        })

        // Adiciona o evento de clique na linha
        holder?.itemView?.setOnClickListener { onClick(carro) }
    }
}