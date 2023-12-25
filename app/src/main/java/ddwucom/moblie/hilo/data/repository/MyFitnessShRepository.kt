package ddwucom.moblie.hilo.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MyFitnessShRepository @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private val sharedPreferences = context.getSharedPreferences("MyFitness", Context.MODE_PRIVATE)

    fun saveData(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun readData(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    fun getAllData(): Map<String, *> {
        return sharedPreferences.all
    }

    fun deleteData(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }
}
