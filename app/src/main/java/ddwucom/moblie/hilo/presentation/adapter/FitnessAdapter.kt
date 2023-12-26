package ddwucom.moblie.hilo.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ddwucom.moblie.hilo.data.model.entity.FitnessRecord
import ddwucom.moblie.hilo.databinding.ListHomeRgLocBinding

class FitnessAdapter() : RecyclerView.Adapter<FitnessAdapter.FitnessViewHolder>() {
    var fitnessRecords: List<FitnessRecord>? = null
    interface OnRvBtnClickListener {
        fun onRvBtnClick(view: View, loc: FitnessRecord?)
    }

    lateinit var btnClickListener: OnRvBtnClickListener

    fun setRvBtnClickListener(listener: OnRvBtnClickListener) {
        this.btnClickListener = listener
    }

    override fun getItemCount(): Int {
        return fitnessRecords?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FitnessAdapter.FitnessViewHolder {
        val locView = ListHomeRgLocBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FitnessAdapter.FitnessViewHolder(locView, fitnessRecords, btnClickListener)
    }

    override fun onBindViewHolder(holder: FitnessAdapter.FitnessViewHolder, position: Int) {
        holder.binding.tvRgLocType.text = fitnessRecords?.get(position)?.placeName
        holder.binding.tvRgLocName.text = fitnessRecords?.get(position)?.fitnessName
        holder.binding.tvRgLocAddr.text = fitnessRecords?.get(position)?.fitnessDate
        holder.binding.tvRgLocRgdate.text = fitnessRecords?.get(position)?.fitnessMemo
//        holder.binding.btnRgLocRv.setText("내 운동기록에서 삭제")
    }

    class FitnessViewHolder(
        val binding: ListHomeRgLocBinding,
        val fitnessLocations: List<FitnessRecord>?,
        val listener: FitnessAdapter.OnRvBtnClickListener,
    ) : RecyclerView.ViewHolder(
        binding.root,
    ) {
        init {

            binding.root.setOnLongClickListener {
                listener.onRvBtnClick(it, fitnessLocations?.get(adapterPosition))
                false
            }

//            binding.btnRgLocRv.setOnClickListener {
//            }
        }
    }
}
