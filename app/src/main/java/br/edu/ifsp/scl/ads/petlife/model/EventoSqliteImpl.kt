package br.edu.ifsp.scl.ads.eventolife.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import br.edu.ifsp.scl.ads.petlife.model.Evento
import br.edu.ifsp.scl.ads.petlife.model.EventosDao

class EventoSqliteImpl(context: Context) : EventosDao {
    companion object {
        private const val EVENTO_DATABASE_FILE = "eventos"
        private const val EVENTO_TABLE = "evento"
        private const val DATA_COLUMN = "data"
        private const val HORA_COLUMN = "hora"
        private const val TIPO_DE_EVENTO_COLUMN = "tipoDeEvento"
        private const val NOME_DO_PET_COLUMN = "nomeDoPet"

        private const val CREATE_EVENTO_TABLE_STATEMENT =
            "CREATE TABLE IF NOT EXISTS $EVENTO_TABLE (" +
                    "$DATA_COLUMN TEXT NOT NULL PRIMARY KEY," +
                    "$HORA_COLUMN TEXT NOT NULL ," +
                    "$TIPO_DE_EVENTO_COLUMN TEXT NOT NULL," +
                    "$NOME_DO_PET_COLUMN TEXT NOT NULL);"
        }

        private val eventoDatabase: SQLiteDatabase = context.openOrCreateDatabase(
            EVENTO_DATABASE_FILE,
            Context.MODE_PRIVATE,
            null
        )

        init {
            try {
                eventoDatabase.execSQL(CREATE_EVENTO_TABLE_STATEMENT)
            } catch (se: SQLException) {
                Log.e("EventoSqliteImpl", se.toString())
            }
        }

        override fun createEvento(evento: Evento): Long =
            eventoDatabase.insert(
                EVENTO_TABLE,
                null,
                eventoToContentValues(evento)
            )

        override fun retrieveEvento(data: String): Evento {
            val cursor = eventoDatabase.query(
                true,
                EVENTO_TABLE,
                null,
                "$DATA_COLUMN = ?",
                arrayOf(data),
                null,
                null,
                null,
                null
            )


            return if (cursor.moveToFirst()) {
                cursorToEvento(cursor)
            } else {
                Evento()
            }
        }

        override fun retrieveEventos(): MutableList<Evento> {
            val eventosList = mutableListOf<Evento>()
            val cursor = eventoDatabase.rawQuery("SELECT * FROM $EVENTO_TABLE", null)
            while(cursor.moveToNext()){
                eventosList.add(cursorToEvento(cursor))
            }
            return eventosList
        }
        override fun retrieveEventosFromPet(nomeDoPet: String ): MutableList<Evento> {
            val eventosList = mutableListOf<Evento>()

            val cursor = eventoDatabase.rawQuery("SELECT * FROM $EVENTO_TABLE WHERE NOME_DO_PET_COLUMN = ?",
                arrayOf(nomeDoPet)
            )
            while(cursor.moveToNext()){
                eventosList.add(cursorToEvento(cursor))
            }

            return eventosList
        }
        override fun retrieveEventos(mutableList: MutableList<String>): MutableList<Evento> {
            val eventosList = mutableListOf<Evento>()
            val cursor = eventoDatabase.rawQuery("SELECT * FROM $EVENTO_TABLE WHERE NOME_DO_PET_COLUMN = ?",
                mutableList.toTypedArray()
            )
            while(cursor.moveToNext()){
                eventosList.add(cursorToEvento(cursor))
            }
            return eventosList
        }



    override fun updateEvento(evento: Evento) = eventoDatabase.update(
            EVENTO_TABLE,
            eventoToContentValues(evento),
            "$DATA_COLUMN = ?",
            arrayOf(evento.data)
        )

        override fun deleteEvento(data: String) = eventoDatabase.delete(
            EVENTO_TABLE,
            "$DATA_COLUMN = ?",
            arrayOf(data)
        )



    private fun eventoToContentValues(evento: Evento) = ContentValues().apply {
            with(evento) {
                put(DATA_COLUMN, data)
                put(HORA_COLUMN, hora)
                put(TIPO_DE_EVENTO_COLUMN, tipoDeEvento)
                put(NOME_DO_PET_COLUMN, nomeDoPet)

            }
        }


        private fun cursorToEvento(cursor: Cursor) = with(cursor) {
            Evento(
                getString(getColumnIndexOrThrow(DATA_COLUMN)),
                getString(getColumnIndexOrThrow(HORA_COLUMN)),
                getString(getColumnIndexOrThrow(TIPO_DE_EVENTO_COLUMN)),
                getString(getColumnIndexOrThrow(NOME_DO_PET_COLUMN))
            )
        }

}