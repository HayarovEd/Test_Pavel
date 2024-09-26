package com.edurda77.test_pavel.di


import com.edurda77.test_pavel.data.repository.RemoteRepositoryImpl
import com.edurda77.test_pavel.domain.repository.RemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DiModule {


    @Binds
    @Singleton
    abstract fun bindRemote(remoteRepositoryImpl: RemoteRepositoryImpl): RemoteRepository

}