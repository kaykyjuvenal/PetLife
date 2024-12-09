package br.edu.ifsp.scl.ads.petlife.controller

import br.edu.ifsp.scl.ads.petlife.ui.MainActivity
import br.edu.ifsp.scl.ads.petlife.model.Pet
import br.edu.ifsp.scl.ads.petlife.model.PetDAO
import br.edu.ifsp.scl.ads.petlife.model.PetSqliteImpl

class MainController(mainActivity: MainActivity) {
    private val PetDao: PetDAO = PetSqliteImpl(mainActivity)

    fun insertPet(pet: Pet) = PetDao.createPet(pet)
    fun getPet(nome: String) = PetDao.retrievePet(nome)
    fun getPets() = PetDao.retrievePets()
    fun modifyPet(pet: Pet) = PetDao.updatePet(pet)
    fun removePet(nome: String) = PetDao.deletePet(nome)
}