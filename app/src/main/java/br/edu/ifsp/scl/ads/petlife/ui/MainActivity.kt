package br.edu.ifsp.scl.ads.petlife.ui

import EventoAdapter
import android.content.Intent
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
import br.edu.ifsp.scl.ads.eventolife.ui.AdicionarEventoActivity
import br.edu.ifsp.scl.ads.petlife.R
import br.edu.ifsp.scl.ads.petlife.controller.MainController
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityMainBinding
import br.edu.ifsp.scl.ads.petlife.model.Constant.PET
import br.edu.ifsp.scl.ads.petlife.model.Constant.VIEW_MODE
import br.edu.ifsp.scl.ads.petlife.model.Evento
import br.edu.ifsp.scl.ads.petlife.model.Pet


class MainActivity : AppCompatActivity() {

    private lateinit var amb: ActivityMainBinding

    private lateinit var adicionarPetLauncher : ActivityResultLauncher<Intent>
    private lateinit var adicionarEventoLauncher : ActivityResultLauncher<Intent>
    private lateinit var editarEventosLauncher: ActivityResultLauncher<Intent>
    private lateinit var editarPetLauncher: ActivityResultLauncher<Intent>

    private val petsList = mutableListOf<Pet>()
    private val eventsList = mutableListOf<Evento>()

    private val petAdapter: PetAdapter by lazy{
        PetAdapter(this,petsList)
    }
    private val eventoAdapter: EventoAdapter by lazy{
        EventoAdapter(this,eventsList)
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
        fillEventoList()

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
        adicionarEventoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val evento = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra<Evento>("EVENTO")
                } else {
                    result.data?.getParcelableExtra("EVENTO", Evento::class.java)
                }
                if (evento != null) {
                    adicionarEvento(evento)
                }
            }
        }

        editarEventosLauncher = registerForActivityResult(
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
        R.id.addEventoMi -> {
            adicionarEventoLauncher.launch(Intent(this, AdicionarEventoActivity::class.java)
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
            eventoAdapter.notifyDataSetChanged()
        }
    }
    private fun adicionarEvento(evento: Evento?) {
        evento?.let { receivedEvento ->
            val position = eventsList.indexOfFirst { it.data == receivedEvento.data }
            if (position == -1) {
                eventsList.add(receivedEvento)
                mainController.insertEvento(receivedEvento)
            } else {
                eventsList[position] = receivedEvento
                mainController.modifyEvento(receivedEvento)
            }
            eventoAdapter.notifyDataSetChanged()
        }
    }

    private fun atualizarPet(pet:Pet){
        pet?.let {
            val position = petsList.indexOfFirst { it.nome == pet.nome }
            petsList[position] = pet
            //atualiza o banco
            mainController.modifyPet(pet)
            Toast.makeText(this, "Pet atualizado!", Toast.LENGTH_SHORT).show()
        }?: Toast.makeText(this, "Pet não encontrado!", Toast.LENGTH_SHORT).show()
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

    private fun fillEventoList(){
        Thread{
            runOnUiThread{
                eventsList.clear()
                eventsList.addAll(mainController.getEventos())
                petAdapter.notifyDataSetChanged()
            }
        }.start()
    }



}