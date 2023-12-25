package ddwucom.moblie.hilo

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ddwucom.moblie.hilo.base.BaseFragment
import ddwucom.moblie.hilo.databinding.FragmentHomeBinding
import ddwucom.moblie.hilo.presentation.adapter.LocRegAdapter
import ddwucom.moblie.hilo.presentation.viewmodel.FitnessLocationViewModel

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: FitnessLocationViewModel by viewModels()
    lateinit var locRegAdapter: LocRegAdapter
    private val TAG = "HomeFragment"

    override fun initView() {
        super.initView()

        locRegAdapter = LocRegAdapter()

        binding.rcHomeLocList.adapter = locRegAdapter
        binding.rcHomeLocList.layoutManager = LinearLayoutManager(requireContext())

        viewModel.allLocation.observe(
            this,
            Observer { loc ->
                locRegAdapter.fitnessLocations = loc
                locRegAdapter.notifyDataSetChanged()
                Log.d(TAG, "등록한 장소 리스트 조회")
            },
        )
    }
}
