package com.goforer.base.storage

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.goforer.myhomework.presentation.Caller.logoutApp
import timber.log.Timber
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalStorage
@Inject
constructor(val context: Context) {
    companion object {
        const val key_signed_up = "signed_iup"
        const val key_logged_in = "logged_in"
    }

    private var spec = KeyGenParameterSpec.Builder(
        MasterKey.DEFAULT_MASTER_KEY_ALIAS,
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    )
        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
        .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
        .build()

    private var masterKey = MasterKey.Builder(context)
        .setKeyGenParameterSpec(spec)
        .build()

    private val pref = EncryptedSharedPreferences.create(
        context,
        "Encrypted_Shared_Preferences",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    internal fun logOut() {
        Timber.e("LocalStorage - Log out")

        clearPreference(clearLoggedIn = false)
        deleteCache(context)
        logoutApp(context)
    }

    private fun deleteCache(context: Context) {
        runCatching {
            deleteDir(context.cacheDir)
        }.onFailure { e ->
            e.printStackTrace()
        }
    }

    private fun deleteDir(dir: File?): Boolean {
        return if (dir != null && dir.isDirectory) {
            val children = dir.list() ?: return false

            for (i in children.indices) {
                val success = deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
            }

            dir.delete()
        } else if (dir != null && dir.isFile) {
            dir.delete()
        } else {
            false
        }
    }

    internal fun clearPreference(
        clearLoggedIn: Boolean = false
    ) {
        Timber.d("LocalStorage - Clear preference")
        val loggedIn = isLoggedIn()
        val editor = pref.edit()

        editor.clear()
        editor.apply()
        editor.commit()

        if (!clearLoggedIn && loggedIn)
            setLoggedIn()
    }

    internal fun isSignUp() = pref.getBoolean(key_signed_up, false)

    internal fun setSignedUp() {
        val editor = pref.edit()
        editor.putBoolean(key_signed_up, true)
        editor.apply()
    }

    internal fun isLoggedIn() = pref.getBoolean(key_logged_in, false)

    internal fun setLoggedIn() {
        val editor = pref.edit()
        editor.putBoolean(key_logged_in, true)
        editor.apply()
    }
}
