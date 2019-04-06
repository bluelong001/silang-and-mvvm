package me.study.silang.ui.main.bbs

import androidx.databinding.ObservableField
import me.study.silang.base.videomodel.BaseViewModel

class PostViewModel : BaseViewModel(){
    val title = ObservableField<String>()
    val content = ObservableField<String>()
}
