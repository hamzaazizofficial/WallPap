package com.hamza.wallpap.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ThemeSettingPreference @Inject constructor(
    @ApplicationContext context: Context
): ThemeSetting {

    override val themeStream: MutableStateFlow<WallPapTheme>
    override var theme: WallPapTheme by AppThemePreferenceDelegate("app_theme", WallPapTheme.MODE_AUTO)

    private val preferences: SharedPreferences = context.getSharedPreferences("sample_theme", Context.MODE_PRIVATE)

    init {
        themeStream = MutableStateFlow(theme)
    }

    inner class AppThemePreferenceDelegate(
        private val name: String,
        private val default: WallPapTheme
    ): ReadWriteProperty<Any?, WallPapTheme> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): WallPapTheme =
            WallPapTheme.fromOrdinal(preferences.getInt(name, default.ordinal))

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: WallPapTheme) {
            themeStream.value = value
            preferences.edit {
                putInt(name, value.ordinal)
            }
        }

    }
}