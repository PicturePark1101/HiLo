package ddwucom.moblie.hilo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import ddwucom.moblie.hilo.base.BaseFragment
import ddwucom.moblie.hilo.data.LocDto
import ddwucom.moblie.hilo.data.model.entity.FitnessLocation
import ddwucom.moblie.hilo.data.model.request.LocationApiRequest
import ddwucom.moblie.hilo.databinding.FragmentMapBinding
import ddwucom.moblie.hilo.presentation.adapter.LocApiAdapter
import ddwucom.moblie.hilo.presentation.viewmodel.FitnessLocationViewModel
import ddwucom.moblie.hilo.presentation.viewmodel.LocationApiViewModel
import ddwucom.moblie.hilo.utils.AddressUtil.Companion.CITY_LIST
import ddwucom.moblie.hilo.utils.AddressUtil.Companion.CITY_TOWN_MAP
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map) {

    private val TAG = "MapFragment"

    private val locationApiViewModel: LocationApiViewModel by viewModels()
    private val fitnessLocationViewModel: FitnessLocationViewModel by viewModels()

    private lateinit var googleMap: GoogleMap

    private lateinit var locList: ArrayList<LocDto>
    var centerMarker: Marker? = null

    @RequiresApi(Build.VERSION_CODES.S)
    override fun initView() {
        super.initView()
        Log.d("순서확인", "MapFragment의 initView")
        checkPermissions()

        // 지도
        val mapFragment: SupportMapFragment? =
            childFragmentManager.findFragmentById(R.id.map_googlemap) as SupportMapFragment?
        mapFragment?.getMapAsync(mapReadyCallback)

        // 첫번째 드롭박스
        val adapter = ArrayAdapter(requireContext(), R.layout.list_korea_location, CITY_LIST)
        val cityText = binding.etMapCity.editText as? AutoCompleteTextView

        cityText?.setAdapter(adapter)

        cityText?.setOnItemClickListener { parent, view, position, id ->

            val selectedCity = adapter.getItem(position).toString()
            Log.d(TAG, selectedCity)

            handleItemSelected(selectedCity)
        }
    }

    // dropdown에서 item 선택
    @RequiresApi(Build.VERSION_CODES.S)
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

    // 두번째 드롭박스 선택했을 때 api 요청, 요청 받아 지도에 표시
    @RequiresApi(Build.VERSION_CODES.S)
    private fun requestLocation(selectedCity: String, selectedTown: String) {
        val request = LocationApiRequest(
            getString(R.string.service_key),
            "1",
            "20",
            getString(R.string.resultType),
            selectedCity,
            selectedTown,
        )

        locationApiViewModel.loadData(request)

        // 두번째 드롭박스 눌렸을 때 레트로핏으로 요청함
        locationApiViewModel.liveData.observe(
            this,
            Observer { response ->
                centerMarker?.remove()
                locList = response
                Log.d(TAG, locList.toString())
                initRecyclerView(locList)

                if (locList.size > 0) {
                    // 가장 첫번째로 온 장소로 카메라 이동
                    val fistLocLot = locList.get(0).lot
                    val firstLocLat = locList.get(0).lat

                    moveCamera(LatLng(firstLocLat.toDouble(), fistLocLot.toDouble()))
                    locList.forEach {
                        addMarker(it)
                    }
                }
            },
        )
    }

    // 리사이클러뷰 시작
    private fun initRecyclerView(locList: ArrayList<LocDto>) {
        val adapter = LocApiAdapter(locList)

        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.rcMap.adapter = adapter
        binding.rcMap.layoutManager = layoutManager

        // 클릭했을 때 해당 위치로 카메라 이동
        adapter.setOnLocItemClickListener(object : LocApiAdapter.OnLocClickListener {
            override fun onLocItemClick(view: View, position: Int, loc: LocDto) {
                moveCamera(LatLng(loc.lat.toDouble(), loc.lot.toDouble()))
            }
        })
    }

    // 마커 추가
    @RequiresApi(Build.VERSION_CODES.O)
    fun addMarker(loc: LocDto) {
        val markerOptions: MarkerOptions = MarkerOptions()
        markerOptions.position(LatLng(loc.lat.toDouble(), loc.lot.toDouble()))
            .title(loc.locName)
            .snippet(loc.locType)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        centerMarker = googleMap.addMarker(markerOptions)
        centerMarker?.showInfoWindow()
        centerMarker?.tag = "database_id"

        googleMap.setOnInfoWindowClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("내 장소로 등록하기")
                .setMessage("내 운동 장소로 등록하시겠습니까?")
                .setNeutralButton("닫기") { dialog, which ->
                }
                .setPositiveButton("등록") { dialog, which ->
                    registerLocation(loc)
                }
                .show()
        }
    }

    // 카메라 이동 함수
    private fun moveCamera(lotLng: LatLng) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lotLng, 17F))
    }

    // 장소 Room DB에 저장하기..
    @RequiresApi(Build.VERSION_CODES.O)
    private fun registerLocation(loc: LocDto) {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val currentDateFormat = currentDate.format(formatter).toString()

        val rgLoc = FitnessLocation(
            0,
            loc.locName,
            loc.address,
            loc.lat,
            loc.lot,
            loc.locType,
            currentDateFormat,
        )

        fitnessLocationViewModel.addLocation(rgLoc)

        Log.d(TAG, rgLoc.toString())
    }

    val mapReadyCallback = object : OnMapReadyCallback {
        override fun onMapReady(map: GoogleMap) {
            googleMap = map
            Log.d(TAG, "GoogleMap is ready")
        }
    }

    fun checkPermissions() {
        if (requireContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED &&
            requireContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
//            Toast.makeText(requireContext(), "Permissions are already granted", Toast.LENGTH_SHORT).show()
        } else {
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                ),
            )
        }
    }

    val locationPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
//                    Toast.makeText(requireContext(), "FINE_LOCATION is granted", Toast.LENGTH_SHORT).show()
                }

                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
//                    Toast.makeText(requireContext(), "COARSE_LOCATION is granted", Toast.LENGTH_SHORT).show()
                }
                else -> {
//                    Toast.makeText(requireContext(), "Location permissions are required", Toast.LENGTH_SHORT).show()
                }
            }
        }
}
