package com.smartcam.ai.di

import com.smartcam.ai.core.AndroidAppLogger
import com.smartcam.ai.core.AppLogger
import com.smartcam.ai.data.repository.DemoSmartCamRepository
import com.smartcam.ai.data.repository.SmartCamRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ArchitectureModule {
    @Binds
    abstract fun bindLogger(logger: AndroidAppLogger): AppLogger

    @Binds
    abstract fun bindSmartCamRepository(repository: DemoSmartCamRepository): SmartCamRepository
}
