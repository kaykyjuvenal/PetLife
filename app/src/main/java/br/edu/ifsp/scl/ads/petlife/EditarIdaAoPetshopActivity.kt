package br.edu.ifsp.scl.ads.petlife

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityEditarIdaAoPetshopBinding


class EditarIdaAoPetshopActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditarIdaAoPetshopBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarIdaAoPetshopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.salvarBt.setOnClickListener {
            val nomePet = binding.nomePetEt.text.toString()
            val novaData = binding.dataPetshopEt.text.toString()

            if (nomePet.isNotEmpty() && novaData.isNotEmpty()) {
                val resultIntent = intent.apply {
                    putExtra("nomePet", nomePet)
                    putExtra("novaDataPetshop", novaData)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}
