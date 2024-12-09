package br.edu.ifsp.scl.ads.petlife.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityEditarIdaAoPetshopBinding
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityEditarPetBinding
import br.edu.ifsp.scl.ads.petlife.model.Constant.PET
import br.edu.ifsp.scl.ads.petlife.model.Constant.VIEW_MODE
import br.edu.ifsp.scl.ads.petlife.model.Pet


class EditarIdaAoPetshopActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditarIdaAoPetshopBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarIdaAoPetshopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewMode = intent.getBooleanExtra(VIEW_MODE, false)
        val receivedPet = intent.getParcelableExtra<Pet>(PET)
        val position = intent.getIntExtra("position",-1)

        if(receivedPet != null && position != -1){
            receivedPet?.let { pet ->
                with(binding) {
                    with(pet) {
                        dataPetshopEt.setText(ultimaIdaPetShop)
                        dataPetshopEt.isEnabled = !viewMode

                        salvarBt.visibility = if (viewMode) GONE else VISIBLE
                    }
                }
            }
        }
        binding.toolbarIn.toolbar.let {
            it.subtitle = "Editar ida ao Petshop"
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
                        dataPetshopEt.toString(),
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