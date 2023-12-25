package ddwucom.moblie.hilo.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ddwucom.moblie.hilo.data.model.entity.FitnessLocation
import ddwucom.moblie.hilo.databinding.ListHomeRgLocBinding

class LocRegAdapter() : RecyclerView.Adapter<LocRegAdapter.LocViewHolder>() {
    var fitnessLocations: List<FitnessLocation>? = null

//    interface OnLocClickListener {
//        fun onLocItemClick(view: View, position: Int, fitnessLocation : FitnessLocation)
//    }
//
//    lateinit var listener: OnLocClickListener
//
//    fun setOnLocItemClickListener(listener: OnLocClickListener) {
//        this.listener = listener
//    }

    override fun getItemCount(): Int {
        return fitnessLocations?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocRegAdapter.LocViewHolder {
        val locView = ListHomeRgLocBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return LocViewHolder(locView, fitnessLocations)
    }

    override fun onBindViewHolder(holder: LocRegAdapter.LocViewHolder, position: Int) {
        holder.binding.tvRgLocType.text = fitnessLocations?.get(position)?.locType
        holder.binding.tvRgLocName.text = fitnessLocations?.get(position)?.name
        holder.binding.tvRgLocAddr.text = fitnessLocations?.get(position)?.address
        holder.binding.tvRgLocRgdate.text = fitnessLocations?.get(position)?.regDate
    }

    class LocViewHolder(
        val binding: ListHomeRgLocBinding,
        val fitnessLocations: List<FitnessLocation>?,
    ) : RecyclerView.ViewHolder(
        binding.root,
    ) {
        init {
            binding.root.setOnClickListener {
//                listener.onLocItemClick(it, adapterPosition,
//                    fitnessLocations?.get(adapterPosition) ?: fitnessLocations()
//                )
            }
        }
    }
}
