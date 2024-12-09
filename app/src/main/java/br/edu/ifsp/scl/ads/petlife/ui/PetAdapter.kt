package br.edu.ifsp.scl.ads.petlife.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.ifsp.scl.ads.petlife.R
import br.edu.ifsp.scl.ads.petlife.databinding.TilePetBinding
import br.edu.ifsp.scl.ads.petlife.model.Pet

class PetAdapter (
    context: Context,
    private val petsList: MutableList<Pet>
    ): ArrayAdapter<Pet>(
    context, R.layout.tile_pet, petsList){
        private data class petTileHolder(
            val nomePetTv: TextView,
            val dataNascimentoTv: TextView,
            val porteTv: TextView
        )
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        lateinit var tpb: TilePetBinding

        val pet = petsList[position]

        var petTileView = convertView
        if (petTileView == null) {
            tpb = TilePetBinding.inflate(
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                parent, false
            )
            petTileView = tpb.root

            //Criar um holder para a nova célula
            val newPetTileHolder = petTileHolder(tpb.nomeTv, tpb.dataNascimentoTv, tpb.porteTv)

            //Associar holder a nova célula
            petTileView.tag = newPetTileHolder
        }
            val holder = petTileView.tag as petTileHolder
            holder.let {
                it.nomePetTv.text = pet.nome
                it.dataNascimentoTv.text = pet.dataNascimento
                it.porteTv.text = pet.porte
            }
        return petTileView
    }
}