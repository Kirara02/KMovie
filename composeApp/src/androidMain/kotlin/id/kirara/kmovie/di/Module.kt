package id.kirara.kmovie.di

import com.liftric.kvault.KVault
import id.kirara.kmovie.core.AndroidLocationRepository
import id.kirara.kmovie.data.KVaultSettingsProvider
import id.kirara.kmovie.domain.account.SessionSettings
import id.kirara.kmovie.domain.location.LocationRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val settingsModule = module {
    single {
        SessionSettings(
            settingsProvider = KVaultSettingsProvider(
                KVault(androidContext(), NameSessionSettings)
            )
        )
    }
}

actual val locationModule = module {
    factory <LocationRepository> { AndroidLocationRepository(context = androidContext()) }
}