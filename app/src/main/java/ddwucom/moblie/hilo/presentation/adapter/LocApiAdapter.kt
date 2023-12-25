package ddwucom.moblie.hilo.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ddwucom.moblie.hilo.data.LocDto
import ddwucom.moblie.hilo.databinding.ListMapLocationBinding

class LocApiAdapter(val locList: ArrayList<LocDto>) : RecyclerView.Adapter<LocApiAdapter.LocViewHolder>() {

    interface OnLocClickListener {
        fun onLocItemClick(view: View, position: Int, loc : LocDto)
    }

    lateinit var listener: OnLocClickListener

    fun setOnLocItemClickListener(listener: OnLocClickListener) {
        this.listener = listener
    }

    override fun getItemCount(): Int = locList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocApiAdapter.LocViewHolder {
        val locView = ListMapLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return LocViewHolder(locView, locList, listener)
    }

    override fun onBindViewHolder(holder: LocApiAdapter.LocViewHolder, position: Int) {
        holder.binding.tvLocName.text = locList[position].locName
        holder.binding.tvLocLoc.text = locList[position].address
        holder.binding.tvLocType.text = locList[position].locType
    }
    class LocViewHolder(val binding: ListMapLocationBinding,
                        val locList : ArrayList<LocDto>,
                        listener: OnLocClickListener) : RecyclerView.ViewHolder(binding.root,
                   ){
        init{
            binding.root.setOnClickListener {
                listener.onLocItemClick(it, adapterPosition, locList[adapterPosition])
            }

            binding.btnListMapRg.setOnClickListener {
                // 등록버튼
            }
        }
    }
}
