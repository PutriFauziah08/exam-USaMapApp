package com.putri.fauziah.usamapapp;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.putri.fauziah.usamapapp.fragment.IntroFragment;
import com.putri.fauziah.usamapapp.view.HomeActivity;

import com.github.paolorotolo.appintro.AppIntro;
import android.Manifest;
public class WelcomeActivity extends AppIntro {

    SharedPreferences prefs = null;
    private String[] listPermission = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,

            Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        prefs = getSharedPreferences("com.putri.fauziah.usamapapp", MODE_PRIVATE);
//        if ( !prefs.getBoolean("firstrun", true) ) {
//            Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
//            startActivity(intent);
//            finish();
//        } else {
            addSlide( IntroFragment.newInstance( R.layout.slide_satu) );

            askForPermissions(listPermission, 1);

            addSlide( IntroFragment.newInstance( R.layout.slide_dua) );

            addSlide( IntroFragment.newInstance( R.layout.slide_tiga) );
            setFadeAnimation();

            setBarColor(getResources().getColor(R.color.black));
            setSeparatorColor(getResources().getColor(R.color.black));
//            prefs.edit().putBoolean("firstrun", false).commit();
//        }

    }
    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        moveActivity();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        moveActivity();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }

    private void moveActivity() {
        Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}