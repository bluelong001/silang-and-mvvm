package me.study.silang.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import androidx.appcompat.app.AppCompatActivity;
import me.study.silang.R;
import me.study.silang.ui.login.LoginActivity;

public class StartActivity extends AppCompatActivity {


    private boolean isRun;
    Context mContext;

    private final Thread going = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);

                if (isRun) {
                    isRun = false;
                    Message msg = Message.obtain();
                    msg.what = 1;
                    handler.sendMessage(msg);
//                    jump.start();


                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        //        final WeakReference<StartActivity> mActivity;
//        Handler(StartActivity activity){
//
//
//            mActivity = new WeakReference<StartActivity>(activity);
//        }
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
//                StartActivity activity = mActivity.get();
//                if(activity==null){
//                    super.handleMessage(msg);
//                    return;
//                }
                case 1:
                    Intent intent = new Intent();

                    intent.setClass(StartActivity.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }
    };
    private final Thread jump = new Thread(new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent();
//            Intent service = new Intent(mContext, NetworkStateService.class);
//            startService(service);
            startService(intent);
            intent.setClass(StartActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    });

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_start);

        isRun = true;
        mContext = this;
        //查询权限



    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == event.ACTION_DOWN) {
            if (isRun) {
                Message msg = Message.obtain();
                msg.what = 1;
                handler.sendMessage(msg);
//                jump.start();
                isRun = false;
            }

        }
        return super.onTouchEvent(event);
    }
}
