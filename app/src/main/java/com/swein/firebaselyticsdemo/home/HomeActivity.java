package com.swein.firebaselyticsdemo.home;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.swein.firebaselyticsdemo.R;
import com.swein.firebaselyticsdemo.framework.dialog.DialogUtil;
import com.swein.firebaselyticsdemo.framework.firebaseanalytics.EventTracker;
import com.swein.firebaselyticsdemo.framework.noticenter.NotificationCenter;
import com.swein.firebaselyticsdemo.framework.noticenter.NotificationConstants;
import com.swein.firebaselyticsdemo.viewholder.NavigationBarViewHolder;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends Activity {

    private final static String TAG = "HomeActivity";

    private TextView textViewID;
    private TextView textViewPassword;

    private String id;
    private String password;

    private Button buttonPassValue;
    private TextView textViewTitle1;
    private TextView textViewTitle2;
    private TextView textViewTitle3;


    private NavigationBarViewHolder navigationBarViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        EventTracker.getInstance().setScreen(this, "HomeActivity");

        Intent intent = getIntent();

        if(intent != null) {
            id = intent.getStringExtra("id");
            password = intent.getStringExtra("password");
        }

        findView();

        initNavigationBar();

        initData();

        initKVO();
    }

    private void initNavigationBar() {

//        navigationBarViewHolder = new NavigationBarViewHolder(this, "sss");
    }

    private void initKVO() {

        NotificationCenter.getDefaultCenter().addObserverForKey(NotificationConstants.DIALOG_FROM_HOME_ACTIVITY_OK_BUTTON_CLICKED, this, new NotificationCenter.NotificationCenterRunnable() {
            @Override
            public void run(String key, Object poster, HashMap<String, Object> data) {
                Log.d(TAG, "dialog ok button clicked");

                if(data != null) {

                    HashMap<String, Object> hashMap = data;
                    ArrayList<Object> list = (ArrayList<Object>)hashMap.get("list");

                    textViewTitle1.setText(String.valueOf(list.get(0)));
                    textViewTitle2.setText(String.valueOf(list.get(1)));
                    textViewTitle3.setText(String.valueOf(list.get(2)));
                }
            }
        });

        NotificationCenter.getDefaultCenter().addObserverForKey(NotificationConstants.DIALOG_FROM_HOME_ACTIVITY_CANCEL_BUTTON_CLICKED, this, new NotificationCenter.NotificationCenterRunnable() {
            @Override
            public void run(String key, Object poster, HashMap<String, Object> data) {
                Log.d(TAG, "dialog cancel button clicked");
            }
        });
    }

    private void deleteKVO() {
        NotificationCenter.getDefaultCenter().removeAllObserver(this);
    }


    private void findView() {

        textViewID = findViewById(R.id.textViewID);
        textViewPassword = findViewById(R.id.textViewPassword);
        buttonPassValue = findViewById(R.id.buttonPassValue);

        textViewTitle1 = findViewById(R.id.textViewTitle1);
        textViewTitle2 = findViewById(R.id.textViewTitle2);
        textViewTitle3 = findViewById(R.id.textViewTitle3);

        buttonPassValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // DialogInterface.OnClickListener onClickListener
                DialogUtil.createNormalDialogWithTwoButton(HomeActivity.this, "pass value", "pass 123", false, "확인",
                        "취소",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // 확인

//                                HashMap<String, Object> hashMap = new HashMap<>();
//
//                                hashMap.put("1", "apple");
//                                hashMap.put("2", "coffee");
//                                hashMap.put("3", "pizza");
//                                hashMap.put("4", 66);

                                ArrayList<Object> list = new ArrayList<>();
                                list.add("apple");
                                list.add("coffee");
                                list.add("pizza");
                                list.add(66);

                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("list", list);

                                // 알림 발송
                                NotificationCenter.getDefaultCenter().postNotification(NotificationConstants.DIALOG_FROM_HOME_ACTIVITY_OK_BUTTON_CLICKED, this, hashMap);
                            }
                        },
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // 취소
                                NotificationCenter.getDefaultCenter().postNotification(NotificationConstants.DIALOG_FROM_HOME_ACTIVITY_CANCEL_BUTTON_CLICKED, this, null);

                            }
                        }
                );
            }
        });
    }

    private void initData() {
        textViewID.setText(id);
        textViewPassword.setText(password);
    }

    @Override
    protected void onDestroy() {
        deleteKVO();
        super.onDestroy();
    }

}
