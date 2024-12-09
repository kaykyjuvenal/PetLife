package br.edu.ifsp.scl.ads.petlife.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.util.Log

class PetSqliteImpl(context: Context) : PetDAO {
    companion object {
        private const val PET_DATABASE_FILE = "pets"
        private const val PET_TABLE = "pet"
        private const val NOME_COLUMN = "nome"
        private const val DATA_NASCIMENTO_COLUMN = "dataNascimento"
        private const val TIPO_COLUMN = "tipo"
        private const val COR_COLUMN = "cor"
        private const val PORTE_COLUMN = "porte"
        private const val ULTIMA_DATA_PETSHOP_COLUMN = "ultimaIdaPetShop"
        private const val ULTIMA_DATA_VETERINARIO_COLUMN = "ultimaIdaVeterinario"
        private const val ULTIMA_DATA_VACINA_COLUMN = "ultimaIdaVacina"
        private const val TELEFONE_CONSULTORIO_COLUMN = "telefoneConsultorio"
        private const val SITE_CONSULTORIO_COLUMN = "siteConsultorio"

        private const val CREATE_PET_TABLE_STATEMENT =
            "CREATE TABLE IF NOT EXISTS $PET_TABLE (" +
                    "$NOME_COLUMN TEXT NOT NULL PRIMARY KEY," +
                    "$DATA_NASCIMENTO_COLUMN TEXT NOT NULL," +
                    "$TIPO_COLUMN TEXT NOT NULL," +
                    "$COR_COLUMN TEXT NOT NULL," +
                    "$PORTE_COLUMN TEXT NOT NULL," +
                    "$ULTIMA_DATA_PETSHOP_COLUMN TEXT NOT NULL," +
                    "$ULTIMA_DATA_VETERINARIO_COLUMN TEXT NOT NULL," +
                    "$ULTIMA_DATA_VACINA_COLUMN TEXT NOT NULL," +
                    "$TELEFONE_CONSULTORIO_COLUMN TEXT NOT NULL," +
                    "$SITE_CONSULTORIO_COLUMN TEXT NOT NULL)"
    }

    private val petDatabase: SQLiteDatabase = context.openOrCreateDatabase(
        PET_DATABASE_FILE,
        Context.MODE_PRIVATE,
        null
    )

    init {
        try {
            petDatabase.execSQL(CREATE_PET_TABLE_STATEMENT)
        } catch (se: SQLException) {
            Log.e("PetSqliteImpl", se.toString())
        }
    }

    override fun createPet(pet: Pet): Long =
        petDatabase.insert(
            PET_TABLE,
            null,
            petToContentValues(pet)
        )

    override fun retrievePet(nome: String): Pet {
        val cursor = petDatabase.query(
            true,
            PET_TABLE,
            null,
            "$NOME_COLUMN = ?",
            arrayOf(nome),
            null,
            null,
            null,
            null
        )


        return if (cursor.moveToFirst()) {
            cursorToPet(cursor)
        } else {
            Pet()
        }
    }


    override fun retrievePets(): MutableList<Pet> {
        val petsList = mutableListOf<Pet>()

        val cursor = petDatabase.rawQuery("SELECT * FROM $PET_TABLE", null)
            while(cursor.moveToNext()){
                petsList.add(cursorToPet(cursor))
            }

        return petsList
    }

    override fun updatePet(pet: Pet) = petDatabase.update(
        PET_TABLE,
        petToContentValues(pet),
        "$NOME_COLUMN = ?",
        arrayOf(pet.nome)
    )

    override fun deletePet(nome: String) = petDatabase.delete(
      PET_TABLE,
        "$NOME_COLUMN = ?",
        arrayOf(nome)
    )

    private fun petToContentValues(pet: Pet) = ContentValues().apply {
        with(pet) {
            put(NOME_COLUMN, nome)
            put(DATA_NASCIMENTO_COLUMN, dataNascimento)
            put(TIPO_COLUMN, tipo)
            put(COR_COLUMN, cor)
            put(PORTE_COLUMN, porte)
            put(ULTIMA_DATA_PETSHOP_COLUMN, ultimaIdaPetShop)
            put(ULTIMA_DATA_VETERINARIO_COLUMN, ultimaIdaVeterinario)
            put(ULTIMA_DATA_VACINA_COLUMN, ultimaIdaVacina)
            put(TELEFONE_CONSULTORIO_COLUMN, telefoneConsultorio)
            put(SITE_CONSULTORIO_COLUMN, siteConsultorio)
        }
    }

    private fun cursorToPet(cursor: Cursor) = with(cursor) {
        Pet(
            getString(getColumnIndexOrThrow(NOME_COLUMN)),
            getString(getColumnIndexOrThrow(DATA_NASCIMENTO_COLUMN)),
            getString(getColumnIndexOrThrow(TIPO_COLUMN)),
            getString(getColumnIndexOrThrow(COR_COLUMN)),
            getString(getColumnIndexOrThrow(PORTE_COLUMN)),
            getString(getColumnIndexOrThrow(ULTIMA_DATA_PETSHOP_COLUMN)),
            getString(getColumnIndexOrThrow(ULTIMA_DATA_VETERINARIO_COLUMN)),
            getString(getColumnIndexOrThrow(ULTIMA_DATA_VACINA_COLUMN)),
            getString(getColumnIndexOrThrow(TELEFONE_CONSULTORIO_COLUMN)),
            getString(getColumnIndexOrThrow(SITE_CONSULTORIO_COLUMN))
        )
    }
}