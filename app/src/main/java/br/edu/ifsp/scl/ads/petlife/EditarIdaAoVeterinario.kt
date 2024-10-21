package br.edu.ifsp.scl.ads.petlife

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityEditarIdaAoVeterinarioBinding
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityEditarPetBinding

class EditarIdaAoVeterinarioActivity : AppCompatActivity() {


    private lateinit var binding: ActivityEditarIdaAoVeterinarioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarIdaAoVeterinarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.salvarBt.setOnClickListener {
            val nomeCachorro = binding.nomePetEt.text.toString()
            val novaData = binding.dataVeterinarioEt.text.toString()

            if (nomeCachorro.isNotEmpty() && novaData.isNotEmpty()) {
                val resultIntent = intent.apply {
                    putExtra("nomePet", nomeCachorro)
                    putExtra("novaDataVacina", novaData)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}
