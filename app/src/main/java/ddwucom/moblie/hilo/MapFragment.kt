package ddwucom.moblie.hilo

import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import dagger.hilt.android.AndroidEntryPoint
import ddwucom.moblie.hilo.base.BaseFragment
import ddwucom.moblie.hilo.data.LocDto
import ddwucom.moblie.hilo.data.model.request.LocationApiRequest
import ddwucom.moblie.hilo.databinding.FragmentMapBinding
import ddwucom.moblie.hilo.presentation.adapter.LocAdapter
import ddwucom.moblie.hilo.presentation.viewmodel.LocationApiViewModel
import ddwucom.moblie.hilo.utils.AddressUtil.Companion.CITY_LIST
import ddwucom.moblie.hilo.utils.AddressUtil.Companion.CITY_TOWN_MAP

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map) {

    private lateinit var googleMap: GoogleMap
    private val TAG = "MapFragment"
    private val viewModel: LocationApiViewModel by viewModels()
    private lateinit var locList: ArrayList<LocDto>

    override fun initView() {
        super.initView()
        Log.d("순서확인", "MapFragment의 initView")

        val mapFragment: SupportMapFragment? = childFragmentManager.findFragmentById(R.id.map_googlemap) as SupportMapFragment?
        mapFragment?.getMapAsync(mapReadyCallback)

        val adapter = ArrayAdapter(requireContext(), R.layout.list_korea_location, CITY_LIST)
        val autoCompleteTextView = binding.etMapCity.editText as? AutoCompleteTextView

        autoCompleteTextView?.setAdapter(adapter)

        autoCompleteTextView?.setOnItemClickListener { parent, view, position, id ->

            val selectedCity = adapter.getItem(position).toString()
            Log.d(TAG, selectedCity)

            handleItemSelected(selectedCity)
        }
    }

    // dropdown에서 item 선택
    private fun handleItemSelected(selectedCity: String) {
        var townList: List<String>? = CITY_TOWN_MAP[selectedCity]

        if (townList != null) {
            val adapter = ArrayAdapter(requireContext(), R.layout.list_korea_location, townList)
            val townText = binding.etMapTown.editText as? AutoCompleteTextView

            // 새로 선택하면 리셋되어야함!
            townText?.setText("", false)

            townText?.setAdapter(adapter)

            townText?.setOnItemClickListener { parent, view, position, id ->
                val selectedTown = adapter.getItem(position).toString()
                requestLocation(selectedCity, selectedTown)
            }
        }
    }

    // 두번째 드롭박스 선택했을 때 api 요청감
    private fun requestLocation(selectedCity: String, selectedTown: String) {
        val request = LocationApiRequest(
            getString(R.string.service_key),
            "1",
            "10",
            getString(R.string.resultType),
            selectedCity,
            selectedTown,
        )

        viewModel.loadData(request)

        // LiveData를 관찰하고 데이터가 변경되면 처리
        viewModel.liveData.observe(
            this,
            Observer { response ->
                locList = response
                Log.d(TAG, locList.toString())
                initRecyclerView(locList)
            },
        )
    }

    private fun initRecyclerView(locList: ArrayList<LocDto>) {
        val adapter = LocAdapter(locList)

        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.rcMap.adapter = adapter
        binding.rcMap.layoutManager = layoutManager
    }

    val mapReadyCallback = object : OnMapReadyCallback {
        override fun onMapReady(map: GoogleMap) {
            googleMap = map
            Log.d(TAG, "GoogleMap is ready")
        }
    }
}
