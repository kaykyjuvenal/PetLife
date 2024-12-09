package br.edu.ifsp.scl.ads.petlife.ui

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityEditarIdaAoVeterinarioBinding

class EditarIdaAoVeterinarioActivity : AppCompatActivity() {


    private lateinit var binding: ActivityEditarIdaAoVeterinarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarIdaAoVeterinarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.salvarBt.setOnClickListener {
            val nomePet = binding.nomePetEt.text.toString()
            val novaData = binding.dataVeterinarioEt.text.toString()
            val novoTelefone = binding.telefoneConsultorio.text.toString()
            val novoSite = binding.siteConsultorio.text.toString()

            if (nomePet.isNotEmpty() && novaData.isNotEmpty() && novoTelefone.isNotEmpty()
                && novoSite.isNotEmpty()) {
                val resultIntent = intent.apply {
                    putExtra("nomePet", nomePet)
                    putExtra("novaDataVeterinario", novaData)
                    putExtra("novoTelefoneConsultorio", novoTelefone)
                    putExtra("novoSiteConsultorio", novoSite)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}
