package br.edu.ifsp.scl.ads.petlife.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityLigarAoConsultorioBinding
import br.edu.ifsp.scl.ads.petlife.model.Constant.PET
import br.edu.ifsp.scl.ads.petlife.model.Constant.VIEW_MODE
import br.edu.ifsp.scl.ads.petlife.model.Pet

class LigarAoConsultorioActivity : AppCompatActivity(){
    private lateinit var binding: ActivityLigarAoConsultorioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLigarAoConsultorioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val position = intent.getIntExtra("position", -1)

        binding.ligarBt.setOnClickListener {
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