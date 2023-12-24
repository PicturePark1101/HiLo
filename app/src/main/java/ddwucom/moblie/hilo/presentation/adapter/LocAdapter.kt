package ddwucom.moblie.hilo.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ddwucom.moblie.hilo.R
import ddwucom.moblie.hilo.data.LocDto
import ddwucom.moblie.hilo.databinding.ListMapLocationBinding

class LocAdapter(val locList: ArrayList<LocDto>) : RecyclerView.Adapter<LocAdapter.LocViewHolder>() {
    override fun getItemCount(): Int = locList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocAdapter.LocViewHolder {
        val locView = ListMapLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return LocViewHolder(locView)
    }

    override fun onBindViewHolder(holder: LocAdapter.LocViewHolder, position: Int) {
        holder.binding.tvLocName.text = locList[position].locName
        holder.binding.tvLocLoc.text = locList[position].address
        holder.binding.tvLocType.text = locList[position].locType
    }
    class LocViewHolder(val binding : ListMapLocationBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}