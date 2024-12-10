package br.edu.ifsp.scl.ads.petlife.ui

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityEditarEventoBinding
import br.edu.ifsp.scl.ads.petlife.model.Constant.PET
import br.edu.ifsp.scl.ads.petlife.model.Constant.VIEW_MODE
import br.edu.ifsp.scl.ads.petlife.model.Evento


class EditarEventosActivity : AppCompatActivity() {

    private lateinit var binding:ActivityEditarEventoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarEventoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewMode = intent.getBooleanExtra(VIEW_MODE, false)
        val receivedEvento = intent.getParcelableExtra<Evento>("EVENTO")
        val position = intent.getIntExtra("position",-1)


        if(receivedEvento != null && position != -1){
            receivedEvento?.let { pet ->
                with(binding) {
                    with(pet) {

                        dataEt.isEnabled = !viewMode
                        horarioEt.isEnabled = !viewMode
                        tipoDeEventoEt.isEnabled = !viewMode
                        nomeDoPetEt.isEnabled = !viewMode


                        dataEt.setText(data)
                        horarioEt.setText(hora)
                        tipoDeEventoEt.setText(tipoDeEvento)
                        nomeDoPetEt.setText(nomeDoPet)

                        salvarBt.visibility = if (viewMode) GONE else VISIBLE
                    }
                }
            }
        }
        binding.toolbarIn.toolbar.let {
            it.subtitle = "Editar Evento"
            setSupportActionBar(it)
        }
        binding.run {
            binding.salvarBt.setOnClickListener {
                if (receivedEvento!= null) {
                    Evento(
                        dataEt.text.toString(),
                        horarioEt.text.toString(),
                        tipoDeEventoEt.text.toString(),
                        nomeDoPetEt.text.toString()

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