package me.study.silang.utils;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

import static android.content.Context.DOWNLOAD_SERVICE;


public class DownloadAsyncTask extends AsyncTask<Integer, Integer, String> {

    private ProgressDialog dialog;
    private Context mContext;

    private long currentDownloadID;
    private boolean idDownloading = true;
    private String fileName, requestUrlPath;
    private DownloadSuccess downloadSuccess;

    public interface DownloadSuccess {
        void callback();
    }

    public DownloadAsyncTask(Context mContext, ProgressDialog dialog, String fileName, String requestUrlPath, DownloadSuccess downloadSuccess) {
        this.mContext = mContext;
        this.dialog = dialog;
        this.fileName = fileName;
        this.requestUrlPath = requestUrlPath;
        this.downloadSuccess = downloadSuccess;
    }

    @Override
    protected String doInBackground(Integer... integers) {
        downloadByDownloadManager(mContext, fileName, requestUrlPath);
        if (integers.length > 0)
            return String.valueOf(integers[0].intValue());
        else
            return "";
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(mContext, "开始下载", Toast.LENGTH_SHORT).show();
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle("下载中...");
        dialog.setCancelable(false);
        dialog.setMax(100);
        dialog.setButton("取消下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                remove(mContext);
                dialog.cancel();
            }
        });
        dialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int value = values[0];
        if (value <= 100) {
            dialog.setProgress(value);
            if (value == 100) {
                Toast.makeText(mContext, "下载完成", Toast.LENGTH_SHORT).show();
                downloadSuccess.callback();
            }
        }
    }

    // 4下载器下载
    public void downloadByDownloadManager(Context context, String fileName, String requestUrlStr) {

        String downloadUrlStr = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/" + fileName;
        DownloadManager.Request downloadRequest = new DownloadManager.Request(Uri.parse(requestUrlStr));
        // 通过setAllowedNetworkTypes方法可以设置允许在何种网络下下载
        downloadRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        downloadRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        // 下载标题
        downloadRequest.setTitle("downloading" + fileName);
        File saveFile = new File(downloadUrlStr);
        downloadRequest.setDestinationUri(Uri.fromFile(saveFile));

        DownloadManager manager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        currentDownloadID = manager.enqueue(downloadRequest);
        Log.e("e", "DownloadManager start downloading ---------");
        // 获取下载进度
        getDownloadProgress(manager, currentDownloadID, fileName);

    }

    // 5下载进度并返回完成
    public void getDownloadProgress(final DownloadManager manager, final long downloadID, final String resourceName) {
        while (idDownloading) {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(downloadID);
            Cursor cursor = manager.query(query);

            if (cursor.moveToFirst()) {
                long bytesDownloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                long bytesTotal = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                final int downloadProgress = (int) (bytesDownloaded * 100 / bytesTotal);

                publishProgress(downloadProgress);

                Log.e("e", resourceName + ":下载进度: " + downloadProgress + "%");
                if (downloadProgress == 100) {
                    currentDownloadID = -1;
                    break;
                } else {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        StringWriter stringWriter = new StringWriter();
                        e.printStackTrace(new PrintWriter(stringWriter, true));
                    }
                }

                /*int status = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS));
                if (status == DownloadManager.STATUS_PENDING) {
                    Log.e("status", "pengding");
                } else if (status ==DownloadManager.STATUS_PAUSED) {
                    Log.e("status", "paused");
                } else if (status == DownloadManager.STATUS_RUNNING) {
                    Log.e("status", "runing");
                } else if (status == DownloadManager.STATUS_SUCCESSFUL) {
                    Log.e("status", "successful");
                    break;
                } else if (status == DownloadManager.STATUS_FAILED) {
                    Log.e("status", "failed");
                    break;
                }*/
                cursor.close();
            }
        }
    }

    public void remove(Context context) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        if (currentDownloadID >= 0) {
            downloadManager.remove(currentDownloadID);
        }
        idDownloading = false;
    }
}