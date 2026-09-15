package com.smartcam.ai.di

import android.content.Context
import androidx.room.Room
import com.smartcam.ai.data.local.SmartCamDatabase
import com.smartcam.ai.engine.DetectionEngine
import com.smartcam.ai.engine.MockDetectionEngine
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SmartCamDatabase {
        return Room.databaseBuilder(
            context,
            SmartCamDatabase::class.java,
            "smartcam_ai_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideCameraDao(db: SmartCamDatabase) = db.cameraDao()

    @Provides
    fun provideZoneDao(db: SmartCamDatabase) = db.zoneDao()

    @Provides
    fun provideEventDao(db: SmartCamDatabase) = db.eventDao()

    @Provides
    fun provideActivityDao(db: SmartCamDatabase) = db.activityDao()

    @Provides
    fun provideAuditDao(db: SmartCamDatabase) = db.auditDao()

    @Provides
    fun provideRuleDao(db: SmartCamDatabase) = db.ruleDao()

    @Provides
    @Singleton
    fun provideDetectionEngine(engine: MockDetectionEngine): DetectionEngine = engine
}