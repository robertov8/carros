package br.com.livroandroid.carros.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.livroandroid.carros.R
import br.com.livroandroid.carros.domain.Carro
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapaFragment : BaseFragment(), OnMapReadyCallback {
    // Objeto que controla o Google Maps
    private var map: GoogleMap? = null
    val carro: Carro by lazy { arguments.getParcelable<Carro>("carro") }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_mapa, container, false)
        // Inicia o mapa
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment)
                as SupportMapFragment
        mapFragment.getMapAsync(this)
        return view
    }

    // o método onMapReady(map) é chamado quando a inicialização do mapa estiver Ok
    override fun onMapReady(map: GoogleMap) {
        this.map = map
        // Cria o objeto lat/lng com a coordenada da fábrica
        val location = LatLng(carro.latitude.toDouble(), carro.longitude.toDouble())
        // Posiciona o mapa na coordenada da fábrica (zoom = 13)
        val update = CameraUpdateFactory.newLatLngZoom(location, 13f)
        map.moveCamera(update)
        // Marcador no local da fábrica
        map.addMarker(MarkerOptions()
                .title(carro.nome)
                .snippet(carro.desc)
                .position(location))
        // Tipo do mapa: normal, satélite, terreno ou híbrido
        map.mapType = GoogleMap.MAP_TYPE_NORMAL
    }
}