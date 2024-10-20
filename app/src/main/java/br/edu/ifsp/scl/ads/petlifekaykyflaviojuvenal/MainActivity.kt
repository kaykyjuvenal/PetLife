package br.edu.ifsp.scl.ads.petlifekaykyflaviojuvenal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityMainBinding

data class Pet(
    val nome: String,
    val dataNascimento: String,
    val tipo: String,
    val cor: String,
    val porte: String,
    var ultimaIdaPetShop: String,
    var ultimaIdaVeterinario: String,
    var ultimaIdaVacina: String
)

class MainActivity : AppCompatActivity() {

    private lateinit var amb: ActivityMainBinding
    private val listaPets = mutableListOf<Pet>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        amb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(amb.root)

    }
}