package com.grabieckacper.libgame.model.repository.module

import com.grabieckacper.libgame.model.repository.AuthRepository
import com.grabieckacper.libgame.model.repository.DatabaseRepository
import com.grabieckacper.libgame.model.repository.impl.AuthRepositoryImpl
import com.grabieckacper.libgame.model.repository.impl.DatabaseRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideDatabaseRepository(): DatabaseRepository {
        return DatabaseRepositoryImpl()
    }
}
