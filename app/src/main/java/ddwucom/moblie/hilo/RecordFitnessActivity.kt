package ddwucom.moblie.hilo

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import ddwucom.moblie.hilo.base.BaseActivity
import ddwucom.moblie.hilo.data.model.entity.FitnessRecord
import ddwucom.moblie.hilo.databinding.ActivityRecordFitnessBinding
import ddwucom.moblie.hilo.presentation.viewmodel.FitnessLocationViewModel
import ddwucom.moblie.hilo.presentation.viewmodel.FitnessRecordViewModel
import ddwucom.moblie.hilo.presentation.viewmodel.MyFitnessShViewModel
import java.util.Calendar

@AndroidEntryPoint
class RecordFitnessActivity : BaseActivity<ActivityRecordFitnessBinding>(R.layout.activity_record_fitness) {

    private val fitnessRecordViewModel: FitnessRecordViewModel by viewModels()
    private val fitnessLocationViewModel: FitnessLocationViewModel by viewModels()
    private val fitnessShViewModel: MyFitnessShViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        clickLocation()
        clickfitness()
        clickDate()

        binding.btnRecordFitnessRg.setOnClickListener {
            var locationName = binding.etRecordFitnessLocationSelect.text.toString()
            var fitnessName = binding.tvRecordFitnessFitnessSelect.text.toString()
            var fitnessDate = binding.tvRecordFitnessDateSelect.text.toString()
            var fitnessMemo = binding.etRecordFitnessMemo.text.toString()

            if (locationName == null || fitnessName == null || fitnessDate == null) {
                Toast.makeText(this@RecordFitnessActivity, "미입력한 내역을 입력해주세요!!", Toast.LENGTH_SHORT).show()
            } else {
                fitnessRecordViewModel.addLocation(
                    FitnessRecord(
                        0,
                        locationName,
                        fitnessName,
                        fitnessDate,
                        fitnessMemo,
                    ),
                )
                finish()
            }
        }
    }

    private fun clickLocation() {
        binding.chipRecordFitnessLocation.setOnClickListener {
            val locationArr = fitnessLocationViewModel.allLocation.value
            Log.d("직접 입력", "loc : " + fitnessLocationViewModel.allLocation)

            val loc = ArrayList<String>()

            loc.add("직접 입력")
            if (locationArr != null) {
                Log.d("직접 입력", "loc : " + locationArr)

                locationArr.forEach {
                    if (it.name != null) {
                        loc.add(it.name!!)
                    }
                    Log.d("직접 입력", it.name!!)
                }
            }

            val locList = loc.toTypedArray()

            Log.d("rgTest", locList.size.toString())
            MaterialAlertDialogBuilder(this@RecordFitnessActivity)
                .setTitle("장소 선택")
                .setNeutralButton("취소") { dialog, which ->
                }
                .setPositiveButton("확인") { dialog, which ->
                    val selectedValue = locList[which]
                    binding.tvRecordFitnessFitnessSelect.setText(selectedValue)
                }
                // Single-choice items (initialized with checked item)
                .setSingleChoiceItems(locList, 0) { dialog, which ->
                    // Respond to item chosen
                }
                .show()
        }
    }

    private fun clickfitness() {
        binding.chipRecordFitnessFitnessSelect.setOnClickListener {
            val locationArr = fitnessShViewModel.allData.value

            val loc = ArrayList<String>()

//            loc.add("직접 입력")
            if (locationArr != null) {
                locationArr.forEach {
                    if (it.key != null) {
                        loc.add(it.key!!)
                    }
                }
            }

            val locList = loc.toTypedArray()

            val alertDialogBuilder = MaterialAlertDialogBuilder(this@RecordFitnessActivity)

            var selectedPosition = 0
            alertDialogBuilder.setTitle("운동 선택")
                .setNeutralButton("취소") { dialog, which ->
                    // Respond to neutral button press
                }
                .setPositiveButton("확인") { dialog, which ->
                    Log.d("rgTest", "Selected index: $which")

                    val selectedValue = locList[selectedPosition]

                    if (which != 0) {
                        binding.tvRecordFitnessFitnessSelect.setText(selectedValue)
                    }
                }
                // Single-choice items (initialized with checked item)
                .setSingleChoiceItems(locList, 1) { dialog, which ->
                    selectedPosition = which
                    Log.d("rgTest", "witht  $which")
                    // Respond to item chosen
                }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }

    private fun clickDate() {
        binding.chipRecordFitnessDate.setOnClickListener {
            var calendar = Calendar.getInstance()
            var year = calendar.get(Calendar.YEAR)
            var month = calendar.get(Calendar.MONTH)
            var day = calendar.get(Calendar.DAY_OF_MONTH)

            this?.let { it1 ->
                DatePickerDialog(it1, { _, year, month, day ->
                    run {
                        binding.tvRecordFitnessDateSelect.setText(year.toString() + "." + (month + 1).toString() + "." + day.toString())
                    }
                }, year, month, day)
            }?.show()
        }
    }
}
