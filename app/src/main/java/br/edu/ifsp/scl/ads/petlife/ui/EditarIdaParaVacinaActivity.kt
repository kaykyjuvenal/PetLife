package br.edu.ifsp.scl.ads.petlife.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityEditarIdaParaVacinaBinding
import br.edu.ifsp.scl.ads.petlife.model.Constant.PET
import br.edu.ifsp.scl.ads.petlife.model.Constant.VIEW_MODE
import br.edu.ifsp.scl.ads.petlife.model.Pet

class EditarIdaParaVacinaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditarIdaParaVacinaBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarIdaParaVacinaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewMode = intent.getBooleanExtra(VIEW_MODE, false)
        val receivedPet = intent.getParcelableExtra<Pet>(PET)
        val position = intent.getIntExtra("position",-1)

        if(receivedPet != null && position != -1){
            receivedPet?.let { pet ->
                with(binding) {
                    with(pet) {
                        dataVacinaEt.setText(ultimaIdaPetShop)
                        dataVacinaEt.isEnabled = !viewMode

                        salvarBt.visibility = if (viewMode) android.view.View.GONE else android.view.View.VISIBLE
                    }
                }
            }
        }

        binding.toolbarIn.toolbar.let {
            it.subtitle = "Editar ida para vacina"
            setSupportActionBar(it)
        }
        binding.run {
            binding.salvarBt.setOnClickListener {
                if (receivedPet != null) {
                    Pet(
                        receivedPet.nome,
                        receivedPet.dataNascimento,
                        receivedPet.tipo,
                        receivedPet.cor,
                        receivedPet.porte,
                        receivedPet.ultimaIdaPetShop,
                        receivedPet.ultimaIdaVeterinario,
                        dataVacinaEt.toString(),
                        receivedPet.telefoneConsultorio,
                        receivedPet.siteConsultorio

                    ).let { pet ->
                        Intent().apply {
                            putExtra(PET, pet)
                            putExtra("position",position)
                            setResult(RESULT_OK, this)
                            finish()
                        }
                    }
                }
            }
        }
    }
}