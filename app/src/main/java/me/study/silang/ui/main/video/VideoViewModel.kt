package me.study.silang.ui.main.video

import android.net.Uri
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import me.study.silang.base.videomodel.BaseViewModel

class VideoViewModel : BaseViewModel() {
    val addStatus = ObservableField<Boolean>()
    val videoUri = ObservableField<Uri>()

    init{
        addStatus.set(false)
    }
}
