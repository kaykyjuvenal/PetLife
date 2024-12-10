package br.edu.ifsp.scl.ads.petlife.controller

import br.edu.ifsp.scl.ads.eventolife.model.EventoSqliteImpl
import br.edu.ifsp.scl.ads.petlife.model.Evento
import br.edu.ifsp.scl.ads.petlife.model.EventosDao
import br.edu.ifsp.scl.ads.petlife.ui.MainActivity
import br.edu.ifsp.scl.ads.petlife.model.Pet
import br.edu.ifsp.scl.ads.petlife.model.PetDAO
import br.edu.ifsp.scl.ads.petlife.model.PetSqliteImpl

class MainController(mainActivity: MainActivity) {
    private val PetDao: PetDAO = PetSqliteImpl(mainActivity)
    private val EventosDao: EventosDao =  EventoSqliteImpl(mainActivity)
    fun insertPet(pet: Pet) = PetDao.createPet(pet)
    fun getPet(nome: String) = PetDao.retrievePet(nome)
    fun getPets() = PetDao.retrievePets()
    fun modifyPet(pet: Pet) = PetDao.updatePet(pet)
    fun modifyEvento(evento: Evento) = EventosDao.updateEvento(evento)
    fun removePet(nome: String) = PetDao.deletePet(nome)
    fun getEventsFromPet(nome: String) = EventosDao.retrieveEventosFromPet(nome)
    fun getEventos()= EventosDao.retrieveEventos()
    fun insertEvento(evento: Evento) = EventosDao.createEvento(evento)
}