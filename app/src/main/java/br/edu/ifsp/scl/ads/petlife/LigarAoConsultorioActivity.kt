package br.edu.ifsp.scl.ads.petlife

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityLigarAoConsultorioBinding

class LigarAoConsultorioActivity : AppCompatActivity(){
    private lateinit var binding: ActivityLigarAoConsultorioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLigarAoConsultorioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ligarBt.setOnClickListener {
            val nomePet = binding.nomePetEt.text.toString()

            if (nomePet.isNotEmpty() ) {
                val resultIntent = intent.apply {
                    putExtra("nomePet", nomePet)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}