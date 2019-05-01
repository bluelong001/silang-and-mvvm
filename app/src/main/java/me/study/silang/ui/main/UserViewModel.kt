package me.study.silang.ui.main

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.study.silang.config.BaseIPAdress
import me.study.silang.entity.Message
import me.study.silang.http.RetrofitCallback
import me.study.silang.http.RetrofitManager
import me.study.silang.model.UserData
import me.study.silang.model.UserInfo
import me.study.silang.repository.MainRepository
import me.study.silang.ui.main.message.MessageListAdapter
import me.study.silang.utils.AnyCallback
import org.json.JSONObject
import java.net.URISyntaxException
import java.util.logging.Handler

class UserViewModel : ViewModel() ,MessageListAdapter.Callback{
    override fun del(msg: Message) {
        delMessage(msg)
    }


    var userInfo = ObservableField<UserInfo>()
    var userData = ObservableField<UserData>()

    lateinit var messageListAdapter: MessageListAdapter

    interface MsgEventCallback {
        fun callback(msg: Int)
    }

    var msgCall: MsgEventCallback? = null
    fun setMsgCallback(callback: MsgEventCallback) {
        this.msgCall = callback
    }

    var mSocket: Socket? = null
    fun initService(context: Context) {
        messageListAdapter = MessageListAdapter(context,this)
        service = (RetrofitManager.getInstance<MainRepository>(
            context,
            MainRepository::class.java
        ).service as MainRepository?)!!


        try {
            mSocket = IO.socket(BaseIPAdress.getSocketIOAddress())
            mSocket!!.on("systemCall") { args ->
                val data = args[0] as Int
                if (null != msgCall) msgCall!!.callback(data)
            }
            mSocket!!.on("replyCall") { args ->
                val data = args[0] as Int
                if (null != msgCall) msgCall!!.callback(data)
            }
            mSocket!!.connect()
        } catch (e: URISyntaxException) {
            e.stackTrace
        }
    }

    lateinit var service: MainRepository
//
//    @BindingAdapter("internetUrl")
//    fun setData(img: InternetImageView, data: String) {
//
//    }

    fun initUser(callback: AnyCallback?, context: Context) {
        service.getUserInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : RetrofitCallback<Any>() {
                override fun onSuccess(data: Any?) {
                    userInfo.set(data as UserInfo)
                    callback?.callback()
                    context.getSharedPreferences("silang_user_info", Context.MODE_PRIVATE).also { sharedPreferences ->
                        sharedPreferences.edit().putInt("userId", userInfo.get()!!.id!!).apply()
                    }
                    mSocket!!.emit("login", userInfo.get()!!.id.toString())
                }

                override fun onFailure(msg: String?) {

                }
            })
        service.getUserData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : RetrofitCallback<Any>() {
                override fun onSuccess(data: Any?) {
                    userData.set(data as UserData)
                    callback?.callback()
                }

                override fun onFailure(msg: String?) {

                }
            })
    }

    fun initMessage(){
        service.listMessages()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : RetrofitCallback<Any>() {
                override fun onSuccess(data: Any?) {
                    messageListAdapter.items.clear()
                    messageListAdapter.items.addAll(data as List<Message>)
                }

                override fun onFailure(msg: String?) {

                }
            })
    }
    fun addMessage(id: Int) {
        service.getMessage(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : RetrofitCallback<Any>() {
                override fun onSuccess(data: Any?) {
                    messageListAdapter.items.add(data as Message?)
                }

                override fun onFailure(msg: String?) {

                }
            })
    }

    fun delMessage(msg:Message) {
        service.delMessageById(msg.id!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : RetrofitCallback<Any>() {
                override fun onSuccess(data: Any?) {
                    messageListAdapter.items.remove(msg)
                }

                override fun onFailure(msg: String?) {

                }
            })
    }
}
