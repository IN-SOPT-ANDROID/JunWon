package org.sopt.sample.presentation.my_page.gallery

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class LauncherObserver(private val registry: ActivityResultRegistry, private val action: (uri: Uri) -> Unit) :
    DefaultLifecycleObserver {
    lateinit var getContent: ActivityResultLauncher<PickVisualMediaRequest>

    override fun onCreate(owner: LifecycleOwner) {
        getContent = registry.register("키키키키키", owner, ActivityResultContracts.PickMultipleVisualMedia()) { uris ->
            uris?.let { if (it.isNotEmpty()) action(it[0]) }
        }
    }

    fun selectImage() {
        getContent.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
    }
}
