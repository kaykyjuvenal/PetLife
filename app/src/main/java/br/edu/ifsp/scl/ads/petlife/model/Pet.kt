package br.edu.ifsp.scl.ads.petlife.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pet(
    val nome: String = "",
    val dataNascimento: String = "",
    val tipo: String = "",
    val cor: String = "",
    val porte: String = "",
    val eventos: MutableList<String> = mutableListOf()
): Parcelable