import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.ifsp.scl.ads.petlife.R
import br.edu.ifsp.scl.ads.petlife.databinding.TileEventsBinding
import br.edu.ifsp.scl.ads.petlife.model.Evento


class EventoAdapter (
        context: Context,
        private val eventsList: MutableList<Evento>
    ): ArrayAdapter<Evento>(
        context, R.layout.tile_events, eventsList){
        private data class eventoTileHolder(
            val dataEventoTv:  TextView,
            val TipoEventoTv: TextView
        )
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            lateinit var tpb: TileEventsBinding

            val evento = eventsList[position]

            var eventoTileView = convertView
            if (eventoTileView == null) {
                tpb = TileEventsBinding.inflate(
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                    parent, false
                )
                eventoTileView = tpb.root

                //Criar um holder para a nova célula
                val newEventoTileHolder = eventoTileHolder(tpb.dataEventoTv, tpb.tipoDeEventoTv)

                //Associar holder a nova célula
                eventoTileView.tag = newEventoTileHolder
            }
            val holder = eventoTileView.tag as eventoTileHolder
            holder.let {
                it.dataEventoTv.text = evento.data
                it.TipoEventoTv.text = evento.tipoDeEvento
            }
            return eventoTileView
        }
    }