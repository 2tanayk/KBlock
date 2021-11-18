package com.kamathtanay.kblock.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

object PermissionUtil {
    fun requestPermission(ctx: Fragment, permission: String=""): ActivityResultLauncher<String> {
        return ctx.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Log.e("Permission", "Granted")
            } else {
                Log.e("Permission", "Denied $permission")
            }
        }
    }

    fun hasPermission(ctx: Context, permission: String) =
        ContextCompat.checkSelfPermission(ctx, permission) == PackageManager.PERMISSION_GRANTED
}