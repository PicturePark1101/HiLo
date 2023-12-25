package ddwucom.moblie.hilo

import android.os.Build
import android.text.InputFilter
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import ddwucom.moblie.hilo.base.BaseFragment
import ddwucom.moblie.hilo.data.model.entity.FitnessLocation
import ddwucom.moblie.hilo.databinding.FragmentHomeBinding
import ddwucom.moblie.hilo.presentation.adapter.LocRegAdapter
import ddwucom.moblie.hilo.presentation.viewmodel.FitnessLocationViewModel
import ddwucom.moblie.hilo.presentation.viewmodel.MyFitnessShViewModel

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val fitnessLocationViewModel: FitnessLocationViewModel by activityViewModels()
    private val myFitnessShViewModel: MyFitnessShViewModel by activityViewModels()

    lateinit var locRegAdapter: LocRegAdapter
    private val TAG = "HomeFragment"

    override fun initView() {
        super.initView()

        Log.d(TAG, "${myFitnessShViewModel.allData.value}")
        // 등록한 장소 리사이클러뷰
        ryRgLocation()

        // 등록한 운동 출력
        setView()
    }

    override fun setView() {
        // 추가 버튼 눌렀을 때
        binding.chipHomeMyFitnessAdd.setOnClickListener {
            chipRgMyFitness()
        }

        // 모든 chip을 삭제, 다시 처음부터 생성하기
        // sh 변경했을 경우
        myFitnessShViewModel.allData.observe(
            viewLifecycleOwner,
            Observer { allData ->
                val chipGroup: ChipGroup = binding.chipHomeMyFitnessGroup

                // 이하 로직은 그대로 유지

                // 추가 Chip을 제외한 모든 Chip을 삭제
                chipGroup.removeAllViews()

                Log.d(TAG, "${myFitnessShViewModel.allData.value}")

                // chip 추가
                allData.forEach {
                    Log.d("chip 추가 확인", "현재 값들 :${it.value}")

                    val newChip = Chip(requireContext())

                    newChip.text = it.value.toString()
                    newChip.isClickable = true
                    newChip.isCheckable = false
                    chipGroup.addView(newChip)

                    Log.d("chip 추가 확인", "추가되는 값 value :${it.value}")

                    // 클릭 시 삭제
                    newChip.setOnClickListener {
                        val alert = AlertDialog.Builder(requireContext())
                            .setTitle("운동 삭제")
                            .setMessage("정말 삭제하시겠습니까?")
                            .setPositiveButton("삭제") { dialog, whichButton ->
                                chipGroup.removeView(newChip)
                                // sh에서 삭제~
                                myFitnessShViewModel.deleteData(newChip.text.toString())
                            }
                            .setNegativeButton("취소") { dialog, whichButton ->
                                // 취소 버튼을 클릭했을 때의 처리
                            }
                            .show()
                    }
                }
            },
        )
    }
    private fun chipRgMyFitness() {
        val alert = AlertDialog.Builder(requireContext())
            .setTitle("운동 등록")
            .setMessage("등록할 운동명을 입력하세요")

        val name = EditText(requireContext())
        val filterArray = arrayOf<InputFilter>(InputFilter.LengthFilter(15)) // 글자수 제한
        name.filters = filterArray
        alert.setView(name)

        alert.setPositiveButton("등록") { dialog, whichButton ->
            val fitnessName = name.text.toString()
            Log.d("SH등록확인", "$fitnessName")

            myFitnessShViewModel.saveData(fitnessName, fitnessName)
        }

        alert.setNegativeButton("취소") { dialog, whichButton ->
            // 취소 버튼을 클릭했을 때의 처리
        }

        alert.show()
    }
    private fun ryRgLocation() {
        locRegAdapter = LocRegAdapter()

        binding.rcHomeLocList.adapter = locRegAdapter
        binding.rcHomeLocList.layoutManager = LinearLayoutManager(requireContext())

        fitnessLocationViewModel.allLocation.observe(
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
                            fitnessLocationViewModel.removeLocation(loc)
                        }
                    }
                    .show()
            }
        })
    }
}
