package com.swein.firebaselyticsdemo.myinfo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.swein.firebaselyticsdemo.R;
import com.swein.firebaselyticsdemo.viewholder.NavigationBarViewHolder;
import com.swein.firebaselyticsdemo.viewholder.interfaces.NavigationBarViewHolderInterface;


public class MyInfoActivity extends Activity {

    private final static String TAG = "MyInfoActivity";

    private TextView textViewMyInfo;

    private NavigationBarViewHolder navigationBarViewHolder;

    private FrameLayout frameLayoutNavigationBarContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        findView();

        initNavigationBar();
    }

    private void findView() {
        textViewMyInfo = findViewById(R.id.textViewMyInfo);
        frameLayoutNavigationBarContainer = findViewById(R.id.frameLayoutNavigationBarContainer);
    }


    private void initNavigationBar() {

        navigationBarViewHolder = new NavigationBarViewHolder(this, new NavigationBarViewHolderInterface() {
            @Override
            public void onImageButtonClicked() {
                Log.d(TAG, "onImageButtonClicked");
            }

            @Override
            public void passValue(String string) {
                textViewMyInfo.setText(string);
            }
        });

        frameLayoutNavigationBarContainer.addView(navigationBarViewHolder.getView());
    }
}
