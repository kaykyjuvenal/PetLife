package br.edu.ifsp.scl.ads.petlife

import android.os.Bundle
import android.widget.Toast
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
        amb.salvarPetBt.setOnClickListener {
            salvarPetNaLista()
        }
    }

    private fun salvarPetNaLista(){
        val nome = amb.nomePetEt.text.toString()
        val dataNascimento = amb.dataNascimentoEt.text.toString()
        val tipo = amb.tipoEt.text.toString()
        val cor = amb.corEt.text.toString()
        val porte = amb.porteEt.text.toString()
        val ultimaIdaPetShop = amb.ultimaIdaPetShopEt.text.toString()
        val ultimaIdaVeterinario = amb.ultimaIdaVeterinarioEt.text.toString()
        val ultimaIdaVacina = amb.ultimaIdaVacinaEt.text.toString()
        if (nome.isEmpty() || dataNascimento.isEmpty() || tipo.isEmpty() || cor.isEmpty()
            || porte.isEmpty() || ultimaIdaPetShop.isEmpty() || ultimaIdaVeterinario.isEmpty()
            || ultimaIdaVacina.isEmpty()){
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()}
            else {
            val novoPet = Pet(
                nome,
                dataNascimento,
                tipo,
                cor,
                porte,
                ultimaIdaPetShop,
                ultimaIdaVeterinario,
                ultimaIdaVacina
            )
            listaPets.add(novoPet)
            atualizarListaPets()
            limparCampos()
            Toast.makeText(this, "Pet cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
        }
    }
    private fun limparCampos(){
        amb.nomePetEt.text.clear()
        amb.dataNascimentoEt.text.clear()
        amb.tipoEt.text.clear()
        amb.corEt.text.clear()
        amb.porteEt.text.clear()
        amb.ultimaIdaPetShopEt.text.clear()
        amb.ultimaIdaVeterinarioEt.text.clear()
        amb.ultimaIdaVacinaEt.text.clear()
    }
    private fun atualizarListaPets(){
        val stringBuilder = StringBuilder()
        listaPets.forEachIndexed{index, pet -> stringBuilder.append("Pet: ${index + 1}\n" +
                "Nome: ${pet.nome}\nData de Nascimento: ${pet.dataNascimento}\nTipo: ${pet.tipo}\n" +
                "Cor: ${pet.cor}\nPorte: ${pet.porte}\nÚltima Ida ao PetShop: ${pet.ultimaIdaPetShop}" +
                "\nÚltima Ida ao Veterinário: ${pet.ultimaIdaVeterinario}\nÚltima Ida para Vacina: " +
                "${pet.ultimaIdaVacina}\n\n")
        }
        amb.listaPetsTv.text = stringBuilder.toString()
    }
}