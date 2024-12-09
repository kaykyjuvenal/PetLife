package br.edu.ifsp.scl.ads.petlife.ui

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityEditarPetBinding

class EditarPetActivity : AppCompatActivity(){
    private lateinit var binding: ActivityEditarPetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarPetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.salvarBt.setOnClickListener {
            val nomePet = binding.nomePetEt.text.toString()
            val novoNomePet = binding.novoNomePetEt.text.toString()
            val novaData = binding.novaDataNascimentoEt.text.toString()
            val novoTipo = binding.novoTipoEt.text.toString()
            val novaCor = binding.novaCorEt.text.toString()
            val novoPorte = binding.novoPorteEt.text.toString()


            if (nomePet.isNotEmpty() && novaData.isNotEmpty()) {
                val resultIntent = intent.apply {
                    putExtra("nomePet", nomePet)
                    putExtra("novoNomePet", novoNomePet)
                    putExtra("novaDataNascimento", novaData)
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