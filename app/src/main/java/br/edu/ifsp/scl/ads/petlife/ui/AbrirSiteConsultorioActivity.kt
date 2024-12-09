package br.edu.ifsp.scl.ads.petlife.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityAbrirSiteConsultorioBinding
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityLigarAoConsultorioBinding

class AbrirSiteConsultorioActivity :  AppCompatActivity() {

    private lateinit var binding: ActivityAbrirSiteConsultorioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
        binding = ActivityAbrirSiteConsultorioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val position = intent.getIntExtra("position", -1)

        binding.abrirBt.setOnClickListener {
            if (position != null) {
                Intent().apply {
                    putExtra("position", position)
                    setResult(RESULT_OK, this)
                    finish()
                }
            }
        }
    }
}