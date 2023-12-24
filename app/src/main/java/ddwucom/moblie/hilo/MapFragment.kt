package ddwucom.moblie.hilo

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Looper
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import ddwucom.moblie.hilo.base.BaseFragment
import ddwucom.moblie.hilo.data.LocDto
import ddwucom.moblie.hilo.data.model.request.LocationApiRequest
import ddwucom.moblie.hilo.databinding.FragmentMapBinding
import ddwucom.moblie.hilo.presentation.adapter.LocAdapter
import ddwucom.moblie.hilo.presentation.viewmodel.LocationApiViewModel
import ddwucom.moblie.hilo.utils.AddressUtil.Companion.CITY_LIST
import ddwucom.moblie.hilo.utils.AddressUtil.Companion.CITY_TOWN_MAP
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map) {

    private lateinit var googleMap: GoogleMap
    private val TAG = "MapFragment"
    private val viewModel: LocationApiViewModel by viewModels()
    private lateinit var locList: ArrayList<LocDto>
    private lateinit var currentLoc: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var geocoder: Geocoder
    var centerMarker: Marker? = null

    @RequiresApi(Build.VERSION_CODES.S)
    override fun initView() {
        super.initView()
        Log.d("순서확인", "MapFragment의 initView")
        checkPermissions()

        // 지도
        val mapFragment: SupportMapFragment? = childFragmentManager.findFragmentById(R.id.map_googlemap) as SupportMapFragment?
        mapFragment?.getMapAsync(mapReadyCallback)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        geocoder = Geocoder(requireContext(), Locale.getDefault())

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

    override fun doOnPause() {
        fusedLocationClient.removeLocationUpdates(locCallback)
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

        viewModel.loadData(request)

        // 두번째 드롭박스 눌렸을 때 레트로핏으로 요청함
        viewModel.liveData.observe(
            this,
            Observer { response ->
                centerMarker?.remove()
                locList = response
                Log.d(TAG, locList.toString())
                initRecyclerView(locList)

                // 가장 첫번째로 온 장소로 카메라 이동
                val fistLocLot = locList.get(0).lot
                val firstLocLat = locList.get(0).lat

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(firstLocLat.toDouble(), fistLocLot.toDouble()), 17F))
                locList.forEach {
                    addMarker(it)
                }
            },
        )
    }

    // 리사이클러뷰 시작
    private fun initRecyclerView(locList: ArrayList<LocDto>) {
        val adapter = LocAdapter(locList)

        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.rcMap.adapter = adapter
        binding.rcMap.layoutManager = layoutManager
    }

    fun addMarker(loc: LocDto) {
        val markerOptions: MarkerOptions = MarkerOptions()
        markerOptions.position(LatLng(loc.lat.toDouble(), loc.lot.toDouble()))
            .title(loc.locName)
            .snippet(loc.locType)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        centerMarker = googleMap.addMarker(markerOptions)
        centerMarker?.showInfoWindow()
        centerMarker?.tag = "database_id"
    }

    val mapReadyCallback = object : OnMapReadyCallback {
        override fun onMapReady(map: GoogleMap) {
            googleMap = map
            Log.d(TAG, "GoogleMap is ready")
        }
    }

    val locCallback: LocationCallback = object : LocationCallback() {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        override fun onLocationResult(locResult: LocationResult) {
            currentLoc = locResult.locations.get(0)

            val targetLoc: LatLng = LatLng(currentLoc.latitude, currentLoc.longitude)
            Log.d(TAG, "위도: ${currentLoc.latitude}, 경도: ${currentLoc.longitude}")

            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(targetLoc, 17F))
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    val locRequest = LocationRequest.Builder(5000)
        .setMinUpdateIntervalMillis(3000)
        .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
        .build()

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("MissingPermission")
    private fun startLocUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locRequest,
            locCallback,
            Looper.getMainLooper(),
        )
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
