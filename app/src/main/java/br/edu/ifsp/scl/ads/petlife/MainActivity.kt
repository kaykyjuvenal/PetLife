package br.edu.ifsp.scl.ads.petlife

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityMainBinding

data class Pet(
    var nome: String,
    var dataNascimento: String,
    var tipo: String,
    var cor: String,
    var porte: String,
    var ultimaIdaPetShop: String,
    var ultimaIdaVeterinario: String,
    var ultimaIdaVacina: String
)

class MainActivity : AppCompatActivity() {

    private lateinit var amb: ActivityMainBinding
    private val listaPets = mutableListOf<Pet>()
    private lateinit var editarIdaVeterinarioLauncher: ActivityResultLauncher<Intent>
    private lateinit var editarIdaPetshopLauncher: ActivityResultLauncher<Intent>
    private lateinit var editarIdaVacinaLauncher: ActivityResultLauncher<Intent>
    private lateinit var editarPetLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        amb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(amb.root)
        amb.salvarPetBt.setOnClickListener {
           salvarPetNaLista()
        }
        amb.editarIdaVeterinarioBt.setOnClickListener {
           editarIdaVeterinario()
        }
        amb.editarIdaPetshopBt.setOnClickListener {
            editarIdaPetshop()
        }
        amb.editarIdaVacinaBt.setOnClickListener {
            editarIdaVacina()
        }
        amb.editarPetBt.setOnClickListener {
            editarPet()
        }
        editarIdaVeterinarioLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        { result ->
            if (result.resultCode == RESULT_OK) {
                val nomePet = result.data?.getStringExtra("nomePet")
                val novaData = result.data?.getStringExtra("novaDataVeterinario")
                if (nomePet != null && novaData != null) {
                    atualizarDataVeterinario(nomePet, novaData)
                }
            }
        }
        editarIdaPetshopLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){ result ->
            if (result.resultCode == RESULT_OK){
                val nomePet = result.data?.getStringExtra("nomePet")
                val novaData = result.data?.getStringExtra("novaDataPetshop")
                if (nomePet != null && novaData != null){
                    atualizarDataPetshop(nomePet, novaData)
                }
            }
        }
        editarIdaVacinaLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val nomePet = result.data?.getStringExtra("nomePet")
                val novaData = result.data?.getStringExtra("novaDataVacina")
                if (nomePet != null && novaData != null) {
                    atualizarDataVacina(nomePet, novaData)
                }
            }
        }
        editarPetLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){ result ->
            if (result.resultCode == RESULT_OK){
                val nomePet = result.data?.getStringExtra("nomePet")
                val novoNomePet = result.data?.getStringExtra("novoNomePet")
                val novaDataNascimento = result.data?.getStringExtra("novaDataNascimento")
                val novoTipo = result.data?.getStringExtra("novoTipo")
                val novaCor = result.data?.getStringExtra("novaCor")
                val novoPorte = result.data?.getStringExtra("novoPorte")
                if (nomePet != null && novoNomePet != null && novaDataNascimento != null
                    && novoTipo != null && novaCor != null && novoPorte != null) {
                    atualizarPet(nomePet, novoNomePet, novaDataNascimento, novoTipo, novaCor, novoPorte)
                }
            }
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
    private fun editarIdaVeterinario() {
        val intent = Intent(this, EditarIdaAoVeterinarioActivity::class.java)
        editarIdaVeterinarioLauncher.launch(intent)
    }
    private fun atualizarDataVeterinario(nomePet: String, novaData: String) {
        val pet = listaPets.find { it.nome == nomePet }
        pet?.let {
            it.ultimaIdaVeterinario = novaData
            atualizarListaPets()
            Toast.makeText(this, "Data de ida ao Veterinario atualizada!", Toast.LENGTH_SHORT).show()
        } ?: Toast.makeText(this, "Pet não encontrado!", Toast.LENGTH_SHORT).show()
    }
    private fun editarIdaPetshop(){
        val intent = Intent(this, EditarIdaAoPetshopActivity::class.java)
        editarIdaPetshopLauncher.launch(intent)
    }
    private fun atualizarDataPetshop(nomePet: String, novaData: String) {
        val pet = listaPets.find { it.nome == nomePet }
        pet?.let {
            it.ultimaIdaPetShop = novaData
            atualizarListaPets()
            Toast.makeText(this, "Data de ida ao Petshop atualizada!", Toast.LENGTH_SHORT).show()
        } ?: Toast.makeText(this, "Pet não encontrado!", Toast.LENGTH_SHORT).show()
    }
    private fun editarIdaVacina(){
        val intent = Intent(this, EditarIdaParaVacinaActivity::class.java)
        editarIdaVacinaLauncher.launch(intent)
    }
    private fun atualizarDataVacina(nomePet: String, novaData: String) {
        val pet = listaPets.find { it.nome == nomePet }
        pet?.let {
            it.ultimaIdaVacina = novaData
            atualizarListaPets()
            Toast.makeText(this, "Data de ida para vacina atualizada!", Toast.LENGTH_SHORT).show()
        } ?: Toast.makeText(this, "Pet não encontrado!", Toast.LENGTH_SHORT).show()
    }
    private fun atualizarPet(nomePet: String,novoNomePet : String, novaDataNascimento : String, novoTipo : String,
                             novaCor : String, novoPorte : String){
        val pet = listaPets.find { it.nome == nomePet }
        pet?.let {
            it.nome = novoNomePet
            it.dataNascimento = novaDataNascimento
            it.tipo = novoTipo
            it.cor = novaCor
            it.porte = novoPorte
            atualizarListaPets()
            Toast.makeText(this, "Pet atualizado!", Toast.LENGTH_SHORT).show()
        } ?: Toast.makeText(this, "Pet não encontrado!", Toast.LENGTH_SHORT).show()
    }
    private fun editarPet(){
        val intent = Intent(this, EditarPetActivity::class.java)
        editarPetLauncher.launch(intent)
    }

}