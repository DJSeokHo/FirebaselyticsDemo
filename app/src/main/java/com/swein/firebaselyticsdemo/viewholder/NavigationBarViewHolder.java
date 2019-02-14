package com.swein.firebaselyticsdemo.viewholder;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.swein.firebaselyticsdemo.R;
import com.swein.firebaselyticsdemo.viewholder.interfaces.NavigationBarViewHolderInterface;

public class NavigationBarViewHolder {

    private final static String TAG = "NavigationBarViewHolder";

    private ImageButton imageButtonSetting;
    private View view;

    private NavigationBarViewHolderInterface navigationBarViewHolderInterface;

    public NavigationBarViewHolder(Activity activity, NavigationBarViewHolderInterface navigationBarViewHolderInterface) {
        this.navigationBarViewHolderInterface = navigationBarViewHolderInterface;
        view = activity.getLayoutInflater().inflate(R.layout.view_holder_navigation_bar, null);
        findView();
        setListener();
    }

    private void findView() {
        imageButtonSetting = view.findViewById(R.id.imageButtonSetting);
    }

    private void setListener() {

        imageButtonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "???", Toast.LENGTH_SHORT).show();
                navigationBarViewHolderInterface.onImageButtonClicked();
                navigationBarViewHolderInterface.passValue("123");
            }
        });
    }

    public View getView() {
        return view;
    }
}
