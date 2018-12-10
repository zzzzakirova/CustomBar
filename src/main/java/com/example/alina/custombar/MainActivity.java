package com.example.alina.custombar;
import com.example.alina.custombar.CustomBar;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    //inject the view with butterknife
    @BindView(R.id.customBar) CustomBar myBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //set the progress to the slider position
        findViewById(R.id.progressButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                myBar.setProgress(myBar.getSlider());
                Toast toast = Toast.makeText(MainActivity.this, "Goal is achieved", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 100);
                toast.show();
            }
        });

    }

}
