package ddwucom.moblie.hilo

import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import ddwucom.moblie.hilo.base.BaseFragment
import ddwucom.moblie.hilo.data.model.entity.FitnessLocation
import ddwucom.moblie.hilo.databinding.FragmentHomeBinding
import ddwucom.moblie.hilo.presentation.adapter.LocRegAdapter
import ddwucom.moblie.hilo.presentation.viewmodel.FitnessLocationViewModel

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: FitnessLocationViewModel by activityViewModels()
    lateinit var locRegAdapter: LocRegAdapter
    private val TAG = "HomeFragment"

    override fun initView() {
        super.initView()

        ryRgLocation()
    }

    private fun ryRgLocation() {
        locRegAdapter = LocRegAdapter()

        binding.rcHomeLocList.adapter = locRegAdapter
        binding.rcHomeLocList.layoutManager = LinearLayoutManager(requireContext())

        viewModel.allLocation.observe(
            this,
            Observer { loc ->
                locRegAdapter.fitnessLocations = loc
                locRegAdapter.notifyDataSetChanged()
                Log.d(TAG, "등록한 장소 리스트 조회")

                loc.forEach {
                    Log.d(TAG, it.toString())
                }
            },
        )

        Log.d("ff", "dfdf")
        locRegAdapter.setRvBtnClickListener(object : LocRegAdapter.OnRvBtnClickListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onRvBtnClick(view: View, loc: FitnessLocation?) {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("내 장소에서 삭제")
                    .setMessage("내 운동 장소에서 삭제하시겠습니까?")
                    .setNeutralButton("닫기") { dialog, which ->
                    }
                    .setPositiveButton("삭제") { dialog, which ->
                        if (loc != null) {
                            Log.d(TAG, loc.address + loc.lat)
                        }

                        if (loc != null) {
                            viewModel.removeLocation(loc)
                        }
                    }
                    .show()
            }
        })
    }

}
