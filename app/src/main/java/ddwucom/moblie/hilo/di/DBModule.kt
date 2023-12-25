package ddwucom.moblie.hilo.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ddwucom.moblie.hilo.data.database.FitnessLocationDatabase
import ddwucom.moblie.hilo.data.model.dao.FitnessLocationDao
import ddwucom.moblie.hilo.data.repository.FitnessLocationRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FitnessLocationDatabase {
        return Room.databaseBuilder(
            context,
            FitnessLocationDatabase::class.java,
            "fitness_location_table.db",
        )
            .build()
    }

    @Provides
    fun provideBookDao(fitnessLocationDatabase: FitnessLocationDatabase): FitnessLocationDao {
        return fitnessLocationDatabase.fitnessLocationDao()
    }

    @Provides
    fun provideBookRepository(fitnessLocationDao: FitnessLocationDao): FitnessLocationRepository {
        return FitnessLocationRepository(fitnessLocationDao)
    }
}
