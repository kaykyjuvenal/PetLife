package br.edu.ifsp.scl.ads.petlife.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityEditarPetBinding
import br.edu.ifsp.scl.ads.petlife.model.Constant.PET
import br.edu.ifsp.scl.ads.petlife.model.Constant.VIEW_MODE
import br.edu.ifsp.scl.ads.petlife.model.Pet

class EditarPetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditarPetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarPetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewMode = intent.getBooleanExtra(VIEW_MODE, false)
        val receivedPet = intent.getParcelableExtra<Pet>(PET)
        val position = intent.getIntExtra("position",-1)

        if(receivedPet != null && position != -1){
            receivedPet?.let { pet ->
                with(binding) {
                    with(pet) {
                        novaDataNascimentoEt.setText(dataNascimento)
                        novoTipoEt.setText(tipo)
                        novaCorEt.setText(cor)
                        novoPorteEt.setText(porte)

                        novaDataNascimentoEt.isEnabled = !viewMode
                        novoTipoEt.isEnabled = !viewMode
                        novaCorEt.isEnabled = !viewMode
                        novoPorteEt.isEnabled = !viewMode
                        salvarBt.visibility = if (viewMode) GONE else VISIBLE
                    }
                }
            }

        }

        binding.toolbarIn.toolbar.let {
            it.subtitle = "Edit pet"
            setSupportActionBar(it)
        }
        binding.run {
            binding.salvarBt.setOnClickListener {
                if (receivedPet != null) {
                    Pet(
                        receivedPet.nome,
                        novaDataNascimentoEt.text.toString(),
                        novoTipoEt.text.toString(),
                        novaCorEt.text.toString(),
                        novoPorteEt.text.toString(),
                        receivedPet.ultimaIdaPetShop,
                        receivedPet.ultimaIdaVeterinario,
                        receivedPet.ultimaIdaVacina,
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