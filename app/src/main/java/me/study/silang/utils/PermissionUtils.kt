package me.study.silang.utils

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.PermissionInfo
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

import java.util.ArrayList
import java.util.Arrays


object PermissionUtils {
    private val TAG = "PermissionUtils"

    /** Android 6.0以上版本需要请求的权限信息(targetSdkVision >= 23)  */
    private val SettingPermission = arrayOf(
        "android.permission.SEND_SMS",
        "android.permission.RECEIVE_SMS",
        "android.permission.READ_SMS",
        "android.permission.RECEIVE_WAP_PUSH",
        "android.permission.RECEIVE_MMS",
        "android.permission.READ_EXTERNAL_STORAGE",
        "android.permission.WRITE_EXTERNAL_STORAGE",
        "android.permission.READ_CONTACTS",
        "android.permission.WRITE_CONTACTS",
        "android.permission.GET_ACCOUNTS",
        "android.permission.READ_PHONE_STATE",
        "android.permission.CALL_PHONE",
        "android.permission.READ_CALL_LOG",
        "android.permission.WRITE_CALL_LOG",
        "android.permission.ADD_VOICEMAIL",
        "android.permission.USE_SIP",
        "android.permission.PROCESS_OUTGOING_CALLS",
        "android.permission.READ_CALENDAR",
        "android.permission.WRITE_CALENDAR",
        "android.permission.CAMERA",
        "android.permission.ACCESS_FINE_LOCATION",
        "android.permission.ACCESS_COARSE_LOCATION",
        "android.permission.BODY_SENSORS",
        "android.permission.RECORD_AUDIO"
    )
    private val permissinList = Arrays.asList(*SettingPermission)


    internal val PermissionRquestCode = 6554

    internal val PermissionResultCode = 6555

    private var CallInstance: PermissionCallBack? = null
    /** 请求权限  */
    fun Request(activity: Activity) {
        val permissions = getPermissions(activity)    // 获取应用的所有权限
        requestPermissionProcess(activity, *permissions)    // 执行权限请求逻辑
    }

    /** 获取AndroidManifest.xml中所有permission信息， 返回信息如{"android.permission.INTERNET", "android.permission.READ_PHONE_STATE"}  */
    fun getPermissions(activity: Activity): Array<String> {
        var permissions = arrayOf<String>()
        try {
            val packageManager = activity.packageManager
            val packageName = activity.packageName

            val packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
            permissions = packageInfo.requestedPermissions
        } catch (e: Exception) {

        }

        return permissions
    }

    /** 请求所需权限 如： String[] permissions = { Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE };  */
    fun requestPermissionProcess(activity: Activity, vararg permissions: String) {
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        val sdkVersion = activity.applicationInfo.targetSdkVersion
        if (Build.VERSION.SDK_INT >= 23 && sdkVersion >= 23) {
            // 检查该权限是否已经获取
            val list = ArrayList<String>()
            for (permission in permissions) {
                try {
                    val ret = ContextCompat.checkSelfPermission(activity.applicationContext, permission)
                    //                    int ret = activity.checkPermission(permission, Process.myPid(), Process.myUid());

                    // 权限是否已经 授权 GRANTED---授权 DINIED---拒绝
                    if (ret != PackageManager.PERMISSION_GRANTED && !list.contains(permission)) list.add(permission)
                } catch (ex: Exception) {
                    Log.e(TAG, "是否已授权,无法判断权限：$permission")
                }

            }

            // 请求没有的权限
            if (list.size > 0) {
                val permission = list.toTypedArray()
                activity.requestPermissions(permission, PermissionRquestCode)    // 从权限请求返回
            }

        } else {
            if (CallInstance != null) CallInstance!!.Success()
        }
    }

    /** 处理权限请求结果逻辑，再次调用请求、或提示跳转设置界面  */
    @RequiresApi(api = Build.VERSION_CODES.M)
    fun onRequestPermissionsResult(
        activity: Activity,
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PermissionRquestCode) {
            val needPermissions = ArrayList<String>()    // 应用未授权的权限
            val noaskPermissions = ArrayList<String>()    // 用户默认拒绝的权限

            for (i in permissions.indices) {
                val permission = permissions[i]
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    try {
                        // 用户点了默认拒绝权限申请，这时候就得打开自定义dialog，让用户去设置里面开启权限
                        if (!activity.shouldShowRequestPermissionRationale(permission)) {
                            Log.i(TAG, "permissinList Size：" + permissinList.size)
                            if (permissinList.contains(permission)) {
                                noaskPermissions.add(permission)
                            } else {
                                Log.i(TAG, "自动允许或拒绝权限：$permission")
                            }
                        } else {
                            // 记录需要请求的权限信息
                            needPermissions.add(permission)
                        }
                    } catch (ex: Exception) {
                        Log.e(TAG, "自动允许或拒绝权限,无法判断权限：$permission")
                    }

                }
            }

            if (needPermissions.size > 0) {
                requestPermissionProcess(activity, *needPermissions.toTypedArray())    // 请求未授予的权限
            } else if (noaskPermissions.size > 0) {
                PermissionSetting(activity, noaskPermissions[0])    // 对话框提示跳转设置界面，添加权限
            } else {
                if (CallInstance != null) CallInstance!!.Success()
            }
        }
    }

    /** 在手机设置中打开的应用权限  */
    private fun PermissionSetting(activity: Activity, permission: String) {
        if (permission.trim { it <= ' ' } == "") return

        // 获取权限对应的标题和详细说明信息
        var permissionLabel = ""
        var permissionDescription = ""

        try {
            val packageManager = activity.packageManager
            // Tools.showText("permission -> " + permission);

            val permissionInfo = packageManager.getPermissionInfo(permission, 0)

            // PermissionGroupInfo permissionGroupInfo = packageManager.getPermissionGroupInfo(permissionInfo.group, 0);
            // Tools.showText("permission组 -> " + permissionGroupInfo.loadLabel(packageManager).toString());

            permissionLabel = permissionInfo.loadLabel(packageManager).toString()
            // Tools.showText("permission名称 -> " + permissionLabel);

            permissionDescription = permissionInfo.loadDescription(packageManager).toString()
            // Tools.showText("permission描述 -> " + permissionDescription);

        } catch (ex: Exception) {
            return
        }

        // 自定义Dialog弹窗，显示权限请求
        permissionLabel = "应用需要权限：$permissionLabel\r\n$permission"
        val builder = AlertDialog.Builder(activity)
        builder.setCancelable(false)
        builder.setTitle(permissionLabel)
        builder.setMessage(permissionDescription)
        builder.setPositiveButton("去添加 权限") { dialog, which ->
            dialog.dismiss()

            // 打开应用对应的权限设置界面
            val action = android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val intent = Intent(action)
            val uri = Uri.fromParts("package", activity.packageName, null)
            intent.data = uri
            activity.startActivityForResult(intent, PermissionResultCode)    // 从应用设置界面返回时执行OnActivityResult
        }
        builder.setNegativeButton("拒绝则 退出") { dialog, which ->
            dialog.dismiss()

            // 若拒绝了所需的权限请求，则退出应用
            activity.finish()
            System.exit(0)
        }
        builder.show()
    }

    /** Activity执行结果，回调函数  */
    fun onActivityResult(activity: Activity, requestCode: Int, resultCode: Int, data: Intent) {
        // Toast.makeText(activity, "onActivityResult设置权限！", Toast.LENGTH_SHORT).show();
        if (requestCode == PermissionResultCode)
        // 从应用权限设置界面返回
        {
            // Toast.makeText(activity, "onActivityResult -> " + resultCode, Toast.LENGTH_SHORT).show();
            Request(activity)        // 再次进行权限请求（若存在未获取到的权限，则会自动申请）
        }
    }

    // ----------

    /** 权限请求回调  */
    abstract class PermissionCallBack {
        /** 权限请求成功  */
        abstract fun Success()
    }

    /** 请求权限, 请求成功后执行回调逻辑  */
    fun Request(activity: Activity, Call: PermissionCallBack) {
        CallInstance = Call
        val permissions = getPermissions(activity)    // 获取应用的所有权限
        requestPermissionProcess(activity, *permissions)    // 执行权限请求逻辑
    }

}
