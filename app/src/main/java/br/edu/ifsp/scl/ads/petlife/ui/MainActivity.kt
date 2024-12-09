package br.edu.ifsp.scl.ads.petlife.ui

import android.content.Intent
import android.content.Intent.ACTION_CALL
import android.content.Intent.ACTION_CHOOSER
import android.content.Intent.ACTION_DIAL
import android.content.Intent.ACTION_VIEW
import android.content.Intent.EXTRA_INTENT
import android.content.Intent.EXTRA_TITLE
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import br.edu.ifsp.scl.ads.petlife.R
import br.edu.ifsp.scl.ads.petlife.controller.MainController
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityMainBinding
import br.edu.ifsp.scl.ads.petlife.model.Constant.PET
import br.edu.ifsp.scl.ads.petlife.model.Constant.VIEW_MODE
import br.edu.ifsp.scl.ads.petlife.model.Pet


class MainActivity : AppCompatActivity() {

    private lateinit var amb: ActivityMainBinding
    private val petsList = mutableListOf<Pet>()
    private lateinit var adicionarPetLauncher : ActivityResultLauncher<Intent>
    private lateinit var editarIdaVeterinarioLauncher: ActivityResultLauncher<Intent>
    private lateinit var editarIdaPetshopLauncher: ActivityResultLauncher<Intent>
    private lateinit var editarIdaVacinaLauncher: ActivityResultLauncher<Intent>
    private lateinit var editarPetLauncher: ActivityResultLauncher<Intent>
    private lateinit var ligarAoConsultorioLauncher: ActivityResultLauncher<Intent>
    private lateinit var abrirSiteConsultorioLauncher: ActivityResultLauncher<Intent>

    private val petAdapter: PetAdapter by lazy{
        PetAdapter(this,petsList)
    }
    private val mainController: MainController by lazy{
        MainController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        amb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(amb.root)
        val toolbar: Toolbar = findViewById(R.id.toolbarIn)
        setSupportActionBar(toolbar)



        amb.toolbarIn.toolbar.let {
            it.subtitle = getString(R.string.pet_list)
            setSupportActionBar(it)
        }

        fillPetList()

        amb.petsLV.adapter = petAdapter

        amb.petsLV.setOnItemClickListener { _, _, position, _ ->
            Intent(this, AdicionarPetActivity::class.java).apply {
                putExtra(PET, petsList[position])
                putExtra(VIEW_MODE, true)
                startActivity(this)
            }
        }

        registerForContextMenu(amb.petsLV)

        adicionarPetLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val pet = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra<Pet>(PET)
                }
                else {
                    result.data?.getParcelableExtra(PET, Pet::class.java)
                }
                if (pet != null) {
                    adicionarPet(pet)
                }
            }
        }

        editarIdaVeterinarioLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        { result ->
            if (result.resultCode == RESULT_OK) {
                val pet = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra<Pet>(PET)
                }
                else {
                    result.data?.getParcelableExtra(PET, Pet::class.java)
                }
                if (pet != null) {
                    atualizarPet(pet)
                }
            }

        }
        editarIdaPetshopLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){ result ->
            if (result.resultCode == RESULT_OK) {
                val pet = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra<Pet>(PET)
                }
                else {
                    result.data?.getParcelableExtra(PET, Pet::class.java)
                }
                if (pet != null) {
                    atualizarPet(pet)
                }
            }
        }
        editarIdaVacinaLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val pet = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra<Pet>(PET)
                }
                else {
                    result.data?.getParcelableExtra(PET, Pet::class.java)
                }
                if (pet != null) {
                    atualizarPet(pet)
                }
            }
        }
        editarPetLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()
        ){ result ->
            if (result.resultCode == RESULT_OK) {
                val position = result.data?.getIntExtra("position", -1)

                val pet = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra<Pet>(PET)

                }
                else {
                    result.data?.getParcelableExtra(PET, Pet::class.java)
                }
                if (pet != null && position != null) {
                    atualizarPet(pet)
                }
            }
        }
        ligarAoConsultorioLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){result ->
            if (result.resultCode == RESULT_OK) {
                if (result.resultCode == RESULT_OK) {
                    val position = result.data?.getIntExtra("position", -1)

                    if (position != null) {
                        discarPetConsultorio(position)
                    }
                }
            }
        }
        abrirSiteConsultorioLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val position = result.data?.getIntExtra("position", -1)
                val pet = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra<Pet>(PET)
                } else {
                    result.data?.getParcelableExtra(PET, Pet::class.java)
                }
                if (pet != null && position != null) {
                    abrirSiteConsultorio(position)
                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        R.id.addPetMi -> {
            adicionarPetLauncher.launch(Intent(this, AdicionarPetActivity::class.java)
                .putExtra(VIEW_MODE,false))
            startActivity(intent)
            true
        }
        else ->{
            false
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) = menuInflater.inflate(R.menu.context_menu_main,menu)

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterContextMenuInfo).position
        return when (item.itemId) {
            R.id.editarPetMi -> {
                Intent(this, EditarPetActivity::class.java).apply {
                    putExtra(PET, petsList[position])
                    putExtra("position",position)
                    putExtra(VIEW_MODE, false)
                    editarPetLauncher.launch(this)
                }

                true
            }

            R.id.excluirPetMi -> {
                mainController.removePet(petsList[position].nome)
                petsList.removeAt(position)
                petAdapter.notifyDataSetChanged()
                true
            }
            R.id.DiscarTelefoneconsultorioMi -> {
                Intent(this, LigarAoConsultorioActivity::class.java).apply {
                    putExtra("position",position)
                    ligarAoConsultorioLauncher.launch(this)
                }
                true
            }
            R.id.AbrirSiteConsultorioMi->{
                Intent(this, AbrirSiteConsultorioActivity::class.java).apply {
                    putExtra("position",position)
                    abrirSiteConsultorioLauncher.launch(this)
                }
                true
            }
            R.id.EditarUltimaIdaAoPetshopMI ->{
                Intent(this, EditarIdaAoPetshopActivity::class.java).apply {
                    putExtra(PET, petsList[position])
                    putExtra("position",position)
                    putExtra(VIEW_MODE, false)
                    editarPetLauncher.launch(this)
                }
                true
            }
            R.id.EditarUltimaIdaParaVacinaMi ->{
                Intent(this,EditarIdaParaVacinaActivity::class.java).apply {
                    putExtra(PET, petsList[position])
                    putExtra("position",position)
                    putExtra(VIEW_MODE, false)
                    editarPetLauncher.launch(this)
                }
                true
            }
            R.id.EditarUltimaIdaAoVeterinarioMi ->{
                Intent(this,EditarIdaAoVeterinarioActivity::class.java).apply {
                    putExtra(PET, petsList[position])
                    putExtra("position",position)
                    putExtra(VIEW_MODE, false)
                    editarPetLauncher.launch(this)
                }
                true
            }

            else -> {
                false
            }
        }
    }

    private fun adicionarPet(pet: Pet?) {
        pet?.let { receivedPet ->
            val position = petsList.indexOfFirst { it.nome == receivedPet.nome }
            if (position == -1) {
                petsList.add(receivedPet)
                mainController.insertPet(receivedPet)
            } else {
                petsList[position] = receivedPet
                mainController.modifyPet(receivedPet)
            }
            petAdapter.notifyDataSetChanged()
        }
    }


    private fun editarIdaVeterinario() {
        //val intent = Intent(this, EditarIdaAoVeterinarioActivity::class.java)
        //editarIdaVeterinarioLauncher.launch(intent)
    }

    //private fun editarIdaPetshop(){
      //  val intent = Intent(this, EditarIdaAoPetshopActivity::class.java)
        //editarIdaPetshopLauncher.launch(intent)
    //}
    //private fun atualizarDataPetshop(nomePet: String, novaData: String) {
       // val pet = petsList.find { it.nome == nomePet }
        //pet?.let {
          //  it.ultimaIdaPetShop = novaData
            //Atualizar no banco
            //Toast.makeText(this, "Data de ida ao Petshop atualizada!", Toast.LENGTH_SHORT).show()
        //} ?: Toast.makeText(this, "Pet não encontrado!", Toast.LENGTH_SHORT).show()
    //}
    //private fun editarIdaVacina(){
      //  val intent = Intent(this, EditarIdaParaVacinaActivity::class.java)
        //editarIdaVacinaLauncher.launch(intent)
    //}

    private fun atualizarPet(pet:Pet){
        pet?.let {
            val position = petsList.indexOfFirst { it.nome == pet.nome }
            petsList[position] = pet
            //atualiza o banco
            mainController.modifyPet(pet)
            Toast.makeText(this, "Pet atualizado!", Toast.LENGTH_SHORT).show()
        }?: Toast.makeText(this, "Pet não encontrado!", Toast.LENGTH_SHORT).show()
    }

    private fun discarPetConsultorio(position: Int){

        val pet = petsList[position]

        pet?.let {
            Uri.parse("tel: ${pet.telefoneConsultorio}").let {
                Intent(if (false) ACTION_CALL else ACTION_DIAL).apply {
                    data = it
                    startActivity(this)
                }
            }
        }
    }
    private fun abrirSiteConsultorio(position: Int){
        val pet = petsList[position]
        pet?.let {
            Uri.parse(pet.siteConsultorio).let { url ->
                Intent(ACTION_VIEW, url).let { navegadorIntent ->
                    val escolherAppIntent = Intent(ACTION_CHOOSER)
                    escolherAppIntent.putExtra(EXTRA_TITLE, "Escolha seu navegador")
                    escolherAppIntent.putExtra(EXTRA_INTENT, navegadorIntent)
                    startActivity(escolherAppIntent)
                }
            }
        }
    }
    private fun fillPetList(){
        Thread{
            runOnUiThread{
                petsList.clear()
                petsList.addAll(mainController.getPets())
                petAdapter.notifyDataSetChanged()
            }
        }.start()
    }



}