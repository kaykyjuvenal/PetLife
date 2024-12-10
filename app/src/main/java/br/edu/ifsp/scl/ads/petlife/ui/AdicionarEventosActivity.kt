package br.edu.ifsp.scl.ads.eventolife.ui

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import br.edu.ifsp.scl.ads.petlife.R
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityAdicionarEventoBinding
import br.edu.ifsp.scl.ads.petlife.model.Constant.VIEW_MODE
import br.edu.ifsp.scl.ads.petlife.model.Evento

class AdicionarEventoActivity :  AppCompatActivity() {
        private lateinit var binding: ActivityAdicionarEventoBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityAdicionarEventoBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val viewMode = intent.getBooleanExtra(VIEW_MODE,false)
            val toolbar : Toolbar = findViewById(R.id.toolbarIn)
            setSupportActionBar(toolbar)

            val receivedEvento = intent.getParcelableExtra<Evento>("EVENTO")
            receivedEvento?.let { evento ->
                with(binding) {
                    with(evento) {
                        dataEventoEt.setText(data)
                        horaEventoEt.setText(hora)
                        tipoDoEventoEt.setText(tipoDeEvento)
                        nomeDoPetEt.setText(nomeDoPet)

                        horaEventoEt.isEnabled =!viewMode
                        tipoDoEventoEt.isEnabled = !viewMode
                        nomeDoPetEt.isEnabled = !viewMode

                        salvarEventoBt.visibility = if (viewMode) GONE else VISIBLE
                    }
                }
            }
            binding.toolbarIn.toolbar.let {
                it.subtitle = if(receivedEvento == null)
                    "New Evento"
                else
                    if (viewMode)
                        "Evento Details"
                    else
                        "Edit evento"
                setSupportActionBar(it)
            }
            binding.run {
                salvarEventoBt.setOnClickListener {
                    Evento(
                        dataEventoEt.text.toString(),
                        horaEventoEt.text.toString(),
                        tipoDoEventoEt.text.toString(),
                        nomeDoPetEt.text.toString(),

                        ).let { evento ->
                        Intent().apply {
                            putExtra("EVENTO", evento)
                            setResult(RESULT_OK, this)
                            finish()
                        }
                    }
                }
            }
        }
    }
