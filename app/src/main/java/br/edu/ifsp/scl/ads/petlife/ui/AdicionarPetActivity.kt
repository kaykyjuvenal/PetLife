package br.edu.ifsp.scl.ads.petlife.ui

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import br.edu.ifsp.scl.ads.petlife.R
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityAdicionarPetBinding
import br.edu.ifsp.scl.ads.petlife.model.Constant
import br.edu.ifsp.scl.ads.petlife.model.Constant.PET
import br.edu.ifsp.scl.ads.petlife.model.Constant.VIEW_MODE
import br.edu.ifsp.scl.ads.petlife.model.Pet

class AdicionarPetActivity :  AppCompatActivity() {
    private lateinit var binding: ActivityAdicionarPetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdicionarPetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewMode = intent.getBooleanExtra(VIEW_MODE,false)
        val toolbar : Toolbar = findViewById(R.id.toolbarIn)
        setSupportActionBar(toolbar)
        val receivedPet = intent.getParcelableExtra<Pet>(PET)
        receivedPet?.let { pet ->
            with(binding) {
                with(pet) {
                    nomePetEt.setText(nome)
                    nomePetEt.isEnabled = false
                    dataNascimentoEt.setText(dataNascimento)
                    tipoEt.setText(tipo)
                    corEt.setText(cor)
                    porteEt.setText(porte)
                    ultimaIdaPetShopEt.setText(ultimaIdaPetShop)
                    ultimaIdaVeterinarioEt.setText(ultimaIdaVeterinario)
                    ultimaIdaVacinaEt.setText(ultimaIdaVacina)
                    telefoneConsultorioEt.setText(telefoneConsultorio)
                    siteConsultorioEt.setText(siteConsultorio)

                    dataNascimentoEt.isEnabled =   !viewMode
                    tipoEt.isEnabled = !viewMode
                    corEt.isEnabled = !viewMode
                    porteEt.isEnabled = !viewMode
                    ultimaIdaPetShopEt.isEnabled = !viewMode
                    ultimaIdaVeterinarioEt.isEnabled = !viewMode
                    ultimaIdaVacinaEt.isEnabled = !viewMode
                    telefoneConsultorioEt.isEnabled = !viewMode
                    siteConsultorioEt.isEnabled = !viewMode
                    salvarPetBt.visibility = if (viewMode) GONE else VISIBLE
                }
            }
        }
        binding.toolbarIn.toolbar.let {
            it.subtitle = if(receivedPet == null)
                "New Pet"
            else
                if (viewMode)
                    "Pet Details"
                else
                    "Edit pet"
            setSupportActionBar(it)
        }
        binding.run {
            salvarPetBt.setOnClickListener {
                Pet(
                    nomePetEt.text.toString(),
                    dataNascimentoEt.text.toString(),
                    tipoEt.text.toString(),
                    corEt.text.toString(),
                    porteEt.text.toString(),
                    ultimaIdaPetShopEt.text.toString(),
                    ultimaIdaVeterinarioEt.text.toString(),
                    ultimaIdaVacinaEt.text.toString(),
                    telefoneConsultorioEt.text.toString(),
                    siteConsultorioEt.text.toString()
                ).let { pet ->
                    Intent().apply {
                        putExtra(PET, pet)
                        setResult(RESULT_OK, this)
                        finish()
                    }
                }
            }
        }
    }
}