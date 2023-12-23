package ddwucom.moblie.hilo

import android.util.Log
import ddwucom.moblie.hilo.base.BaseFragment
import ddwucom.moblie.hilo.databinding.FragmentMapBinding

class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map) {

    override fun initView() {
        super.initView()
        Log.d("순서확인", "MapFragment의 initView")
        binding.apply {
            // TODO.
        }
    }
}
