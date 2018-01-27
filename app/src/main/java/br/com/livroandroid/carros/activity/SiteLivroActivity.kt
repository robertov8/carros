package br.com.livroandroid.carros.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import br.com.livroandroid.carros.R
import br.com.livroandroid.carros.extensions.setupToolbar

class SiteLivroActivity : BaseActivity() {
    private val URL_SOBRE = "http://livroandroid.com.br/sobre.htm"
    var webview: WebView? = null
    var progress: ProgressBar? = null
    var swipeToRefresh: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site_livro)

        // Toolbar
        val actionBar = setupToolbar(R.id.toolbar, "Site do Livro")
        actionBar.setDisplayHomeAsUpEnabled(true)

        // Views
        webview = findViewById<WebView>(R.id.webview)
        progress = findViewById<ProgressBar>(R.id.progress)

        // Carrega a página
        setWebViewClient(webview)
        webview?.loadUrl(URL_SOBRE)

        // Swipe to Refresh
        swipeToRefresh = findViewById<SwipeRefreshLayout>(R.id.swipeToRefresh)
        swipeToRefresh?.setOnRefreshListener { webview?.reload() }

        // Cores de animação
        swipeToRefresh?.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3)
    }

    private fun setWebViewClient(webview: WebView?) {
        webview?.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                // Liga o progress
                progress?.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                // Desliga o progress
                progress?.visibility = View.INVISIBLE
                swipeToRefresh?.isRefreshing = false
            }
        }
    }
}
