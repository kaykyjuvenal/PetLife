package br.edu.ifsp.scl.ads.petlife.model

interface PetDAO {
    fun createPet(pet: Pet): Long
    fun retrievePet(nome: String): Pet?
    fun updatePet(pet: Pet): Int
    fun deletePet(nome: String): Int
    fun retrievePets(): MutableList<Pet>
    fun retrieveEventos(): MutableList<String>
}