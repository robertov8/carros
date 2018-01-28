package br.com.livroandroid.carros.activity.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.widget.TextView
import br.com.livroandroid.carros.R

class AboutDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Cria o HTML com o texto de sobre o aplicativo
        val aboutBody = SpannableStringBuilder()

        // Versão do aplicativo
        val versionName = getAppVersionName()

        // Converte o texto do string.xml para HTML
        val html = Html.fromHtml(getString(R.string.about_dialog_text, versionName))
        aboutBody.append(html)

        // infla o layout
        val inflater = LayoutInflater.from(activity)
        val view = inflater.inflate(R.layout.dialog_about, null) as TextView
        view.text = aboutBody
        view.movementMethod = LinkMovementMethod()

        // Cria o dialog customizado
        return AlertDialog.Builder(activity)
                .setTitle(R.string.about_dialog_title)
                .setView(view)
                .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
                .create()
    }

    private fun getAppVersionName(): String {
        val pm = activity.packageManager
        val packageName = activity.packageName

        return try {
            val info = pm.getPackageInfo(packageName, 0)
            info.versionName
        } catch (ex: PackageManager.NameNotFoundException) {
            "N/A"
        }
    }

    companion object {
        // Método utilitário para mostrar o dialog
        fun showAbout(fm: FragmentManager) {
            val ft = fm.beginTransaction()
            val prev  = fm.findFragmentByTag("about_dialog")
            if (prev != null) {
                ft.remove(prev)
            }
            ft.addToBackStack(null)
            AboutDialog().show(ft, "about_dialog")
        }
    }
}