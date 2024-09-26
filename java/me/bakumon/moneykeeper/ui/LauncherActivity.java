package me.bakumon.moneykeeper.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import me.bakumon.moneykeeper.Router;
import me.drakeet.floo.Floo;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Floo.navigation(this, Router.Url.URL_HOME).start();
        finish();
    }
}
