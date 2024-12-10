package br.edu.ifsp.scl.ads.petlife.model

interface EventosDao {
    fun createEvento(evento: Evento): Long
    fun retrieveEvento(dataEvento: String): Evento?
    fun updateEvento(evento: Evento): Int
    fun deleteEvento(dataEvento: String): Int
    fun retrieveEventos(mutableList: MutableList<String>): MutableList<Evento>
    fun retrieveEventos(): MutableList<Evento>
    fun retrieveEventosFromPet(nomeDoPet: String): MutableList<Evento>
}