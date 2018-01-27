package br.com.livroandroid.carros.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import br.com.livroandroid.carros.R
import br.com.livroandroid.carros.domain.TipoCarro
import br.com.livroandroid.carros.extensions.setupToolbar
import br.com.livroandroid.carros.extensions.toast

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar(R.id.toolbar)
        setupNavDrawer()
    }

    // Configura o Navigation Drawer
    private fun setupNavDrawer() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_item_carros_todos -> toast("Clicou em carros")
            R.id.nav_item_carros_classicos -> onClickNavigation(context, TipoCarro.classicos)
            R.id.nav_item_carros_esportivos -> onClickNavigation(context, TipoCarro.esportivos)
            R.id.nav_item_carros_luxo -> onClickNavigation(context, TipoCarro.luxo)
            R.id.nav_item_site_livro -> toast("Clicou em site do livro")
            R.id.nav_item_settings -> toast("Clicou em configurações")
        }

        // Fecha o menu depois de tratar o evento
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun onClickNavigation(context: Context,
                                  tipo: TipoCarro,
                                  activity: Class<CarrosActivity> = CarrosActivity::class.java) {
        val intent = Intent(context, activity)
        intent.putExtra("tipo", tipo)
        startActivity(intent)

    }


}
