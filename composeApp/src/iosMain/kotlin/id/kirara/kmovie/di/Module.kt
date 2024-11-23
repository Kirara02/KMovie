package id.kirara.kmovie.di

import com.liftric.kvault.KVault
import id.kirara.kmovie.core.IosLocationRepository
import id.kirara.kmovie.domain.location.LocationRepository
import id.kirara.kmovie.data.KVaultSettingsProvider
import id.kirara.kmovie.domain.account.SessionSettings
import org.koin.dsl.module

actual val settingsModule = module {
    single {
        SessionSettings(
            KVaultSettingsProvider(KVault(NameSessionSettings))
        )
    }
}

actual val locationModule = module {
    single <LocationRepository> { IosLocationRepository() }
}