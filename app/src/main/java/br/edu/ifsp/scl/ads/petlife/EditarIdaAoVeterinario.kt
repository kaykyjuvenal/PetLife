package br.edu.ifsp.scl.ads.petlife

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
            val nomeCachorro = binding.nomeCachorroEt.text.toString()
            val novoNomeCachorro = binding.novoNomeCachorroEt.text.toString()
            val novaDataNascimento = binding.novaDataNascimentoEt.text.toString()
            val novoTipo = binding.novoTipoEt.text.toString()
            val novaCor = binding.novaCorEt.text.toString()
            val novoPorte = binding.novoPorteEt.text.toString()

            if (nomeCachorro.isNotEmpty() && novoNomeCachorro.isNotEmpty() && novaDataNascimento &&
                novoTipo.isNotEmpty() && novaCor.isNotEmpty() && novoPorte.isNotEmpty()) {
                val resultIntent = intent.apply {
                    putExtra("nomeCachorro", nomeCachorro)
                    putExtra("novoNomeCachorro", novoNomeCachorro)
                    putExtra("novaDataNascimento", novaDataNascimento)
                    putExtra("novoTipo", novoTipo)
                    putExtra("novaCor", novaCor)
                    putExtra("novoPorte", novoPorte)

                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}
