package ddwucom.moblie.hilo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ddwucom.moblie.hilo.base.BaseActivity
import ddwucom.moblie.hilo.databinding.ActivityRecordFitnessBinding
import ddwucom.moblie.hilo.databinding.ActivitySignInBinding

class RecordFitnessActivity : BaseActivity<ActivityRecordFitnessBinding>(R.layout.activity_record_fitness) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_fitness)
    }
}