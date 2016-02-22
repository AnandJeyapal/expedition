/*
* Copyright 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


package com.example.android.revealeffectbasic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewAnimator;

import com.example.android.common.activities.SampleActivityBase;
import com.example.android.common.logger.Log;
import com.example.android.common.logger.LogFragment;
import com.example.android.common.logger.LogWrapper;
import com.example.android.common.logger.MessageOnlyLogFilter;

/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * {@link android.support.v4.app.Fragment} which can display a view.
 * <p/>
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 */
public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private FloatingActionButton addNewUser;
    private ImageView closeRegister;

    // Whether the Log Fragment is currently shown

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addNewUser = (FloatingActionButton) findViewById(R.id.fab_add_new);
        closeRegister = (ImageView) findViewById(R.id.close_register);
        closeRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // previously visible view
                final View myView = findViewById(R.id.reveal_view);

                int cx = myView.getWidth();
                int cy = 0;

                float initialRadius = (float) Math.hypot(cx, cy);

                Animator anim =
                        ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);

                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        myView.setVisibility(View.INVISIBLE);
                        addNewUser.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        findViewById(R.id.login_view).setVisibility(View.VISIBLE);
                    }
                });

                anim.start();
            }
        });
        addNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewUser.startAnimation(
                        AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_button));
                // previously invisible view
                View myView = findViewById(R.id.reveal_view);

                int cx = myView.getWidth();
                int cy = 0;

                float finalRadius = (float) Math.hypot(cx, cy);

                Animator anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);

                myView.setVisibility(View.VISIBLE);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        findViewById(R.id.login_view).setVisibility(View.INVISIBLE);
                    }

                });
                addNewUser.setVisibility(View.GONE);
                anim.start();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}
