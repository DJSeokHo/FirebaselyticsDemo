package com.swein.firebaselyticsdemo.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.swein.firebaselyticsdemo.R;
import com.swein.firebaselyticsdemo.framework.firebaseanalytics.EventTracker;
import com.swein.firebaselyticsdemo.framework.firebaseanalytics.ExceptionTracker;
import com.swein.firebaselyticsdemo.framework.noticenter.NotificationCenter;
import com.swein.firebaselyticsdemo.framework.noticenter.NotificationConstants;
import com.swein.firebaselyticsdemo.framework.thread.ThreadUtil;
import com.swein.firebaselyticsdemo.framework.toast.ToastUtil;
import com.swein.firebaselyticsdemo.list.MyListActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity {

    private final static String TAG = "MainActivity";

    private Button buttonLogin;

    private EditText editTextInputID;
    private EditText editTextPassword;

    private FrameLayout progressBar;

    private TextView textViewTitle;

    private boolean closeFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExceptionTracker.getInstance().setUserId(this, "seok ho");

        findView();
        setListener();

        initKVO();


        try {
            List list = null;
            Log.d(TAG, (String) list.get(5));
        }
        catch (Exception e) {
            ExceptionTracker.getInstance().sendTryCatchException(this, e);
        }


        Button crashButton = new Button(this);
        crashButton.setText("Crash!");
        crashButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Crashlytics.getInstance().crash(); // Force a crash
            }
        });
        addContentView(crashButton,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        EventTracker.getInstance().setScreen(this, "MainActivity");
        return super.onCreateView(parent, name, context, attrs);
    }

    private void initKVO() {

        NotificationCenter.getDefaultCenter().addObserverForKey(NotificationConstants.DIALOG_FROM_HOME_ACTIVITY_OK_BUTTON_CLICKED, this, new NotificationCenter.NotificationCenterRunnable() {
            @Override
            public void run(String key, Object poster, HashMap<String, Object> data) {
                Log.d(TAG, "dialog ok button clicked");

                HashMap<String, Object> hashMap = data;

                ArrayList<Object> list = (ArrayList<Object>)hashMap.get("list");
                textViewTitle.setText(String.valueOf(list.get(3)));
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

    // findView 이류: 코드 관리 편하게....
    private void findView() {

        editTextInputID = findViewById(R.id.editTextInputID);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        progressBar = findViewById(R.id.progressBar);
        textViewTitle = findViewById(R.id.textViewTitle);

    }

    // 이류: 코드 관리 편하게....
    private void setListener() {

        // click 이벤트 설정
        buttonLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                EventTracker.getInstance().sendEvent(MainActivity.this, "MainActivity", "click", "buttonLogin");

                Log.d(TAG, "login button clicked");

                final String id = editTextInputID.getText().toString().trim();
                Log.d(TAG, "text from id is " + id);

                final String password = editTextPassword.getText().toString().trim();
                Log.d(TAG, "text from password is " + password);

                progressBar.setVisibility(View.VISIBLE);

                goToNextActivity(id, password, "");



//                // 배경 스래드 시작
//                ThreadUtil.startThread(new Runnable() {
//
//                    @Override
//                    public void run() {
//
//
//                        // send id, password to server
//                        VolleyController volleyController = new VolleyController(MainActivity.this);
//                        String url = WebConstants.DOMAIN + WebConstants.LOGIN_URL + "id=" + id + "&password=" + password;
//                        volleyController.requestUrlGet(url, new VolleyController.SHVolleyDelegate() {
//
//                            @Override
//                            public void onResponse(String response) {
//                                Log.d(TAG, response);
//
//                                try {
//                                    JSONObject jsonObject = new JSONObject(response);
//                                    String status = jsonObject.getString("status");
//
//                                    JSONObject obj = jsonObject.getJSONObject("obj");
//
//                                    if("success".equals(status)) {
//                                        final String accessToken = obj.getString("accessToken");
//                                        ToastUtil.showCustomShortToastNormal(MainActivity.this, "성공 " + accessToken);
//
//                                        goToNextActivity(id, password, accessToken);
//
//                                    }
//                                    else {
//                                        String message = obj.getString("message");
//                                        ToastUtil.showCustomShortToastNormal(MainActivity.this, "오류 " + message);
//                                    }
//                                }
//                                catch (JSONException e) {
//                                    e.printStackTrace();
//                                    ToastUtil.showCustomShortToastNormal(MainActivity.this, "오류");
//                                }
//
//
//                            }
//
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Log.d(TAG, error.getMessage());
//                                ToastUtil.showCustomShortToastNormal(MainActivity.this, "인터넷 오류");
//                            }
//                        });
//
//                        // get response from server
//
//                        // success
//
//                    }
//                });
            }
        });

    }

    private void goToNextActivity(final String id, final String password, final String accessToken) {

        // 배경 스래드 끝....
        // UI 스래드 시작
        ThreadUtil.startUIThread(0, new Runnable() {

            @Override
            public void run() {

                // 화면 갠싱

                progressBar.setVisibility(View.GONE);

                Toast.makeText(MainActivity.this, id + getString(R.string.login_success), Toast.LENGTH_SHORT).show();

//              Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                Intent intent = new Intent(MainActivity.this, MyListActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("password", password);
                intent.putExtra("accessToken", accessToken);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        deleteKVO();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getKeyCode() != KeyEvent.KEYCODE_BACK) {
            return super.onKeyDown( keyCode, event );
        }

        if(!closeFlag){
            ToastUtil.showCustomShortToastNormal(this, "뒤로 버튼을 한번 더 누르면 종료됩니다.");
            closeFlag = true;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    closeFlag = false;
                }
            }, 3000);
        }
        else{
            finish();
        }

        return false;

    }
}
