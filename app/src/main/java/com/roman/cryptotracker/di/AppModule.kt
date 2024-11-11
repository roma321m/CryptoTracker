package com.roman.cryptotracker.di

import com.roman.cryptotracker.core.data.networking.HttpClientFactory
import com.roman.cryptotracker.crypto.data.networking.RemoteCoinRepository
import com.roman.cryptotracker.crypto.domain.CoinRepository
import com.roman.cryptotracker.crypto.presentation.coin.CoinViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(engine = CIO.create()) }
    singleOf(::RemoteCoinRepository).bind<CoinRepository>()

    viewModelOf(::CoinViewModel)
}