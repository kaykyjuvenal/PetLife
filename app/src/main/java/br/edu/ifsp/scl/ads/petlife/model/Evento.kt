package br.edu.ifsp.scl.ads.petlife.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Evento(
    val data : String = "",
    val hora : String = "",
    val tipoDeEvento : String = "",
    val nomeDoPet : String = ""

): Parcelable