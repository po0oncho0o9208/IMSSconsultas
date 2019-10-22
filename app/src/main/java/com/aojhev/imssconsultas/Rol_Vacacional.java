package com.aojhev.imssconsultas;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class Rol_Vacacional extends AppCompatActivity implements View.OnClickListener{

    InterstitialAd mInterstitialAd;

    AdView mAdView;
    PDFView pdfView;
    LinearLayout botonrolcomple;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //botonatras
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        setContentView(R.layout.activity_rol);
        mAdView = findViewById(R.id.adView8);
        AdRequest adRequest = new AdRequest.Builder().build();

        botonrolcomple = findViewById(R.id.rolvaca);
        botonrolcomple.setOnClickListener(this);
        mAdView.loadAd(adRequest);

        pdfView = findViewById(R.id.pdfView);
        //  pdfView.fromFile("Convenio.pdf").load();
        pdfView.fromAsset("rol.pdf").load();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(getBaseContext(), MenuPrincipal.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(getBaseContext(), MenuPrincipal.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        Intent intent111 = new Intent(this, NuevoRolVacacional.class);
        startActivity(intent111);
        finish();
    }
}


