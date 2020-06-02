package com.aojhev.imssconsultas;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class Cursos extends AppCompatActivity implements View.OnClickListener {

private AdView mAdView;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //botonatras
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setContentView(R.layout.activity_cursos);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }
    public void Tecnico (View view){
        Intent intent1=new Intent(this,TecnicoEnfermeria.class);
        startActivity(intent1);
    }
    public void Postecnico (View view){
        Intent intent121=new Intent(this,PostEnfermeria.class);
        startActivity(intent121);
    }
    public  void Medicos (View view){

        Intent intent1271=new Intent(this,CursoMedico.class);
        startActivity(intent1271);
    }
    public  void Biblios (View view){

        Intent intent1471=new Intent(this,CursoBiblio.class);
        startActivity(intent1471);
    }

    public  void Nutri (View view){

        Intent intent1971=new Intent(this,Nutricion.class);
        startActivity(intent1971);
    }

    public  void Tecnicos (View view){

        Intent intent1071=new Intent(this,CursosTecnicos.class);
        startActivity(intent1071);
    }

    public  void Listas (View view){

        Intent intent10713=new Intent(this,Listas.class);
        startActivity(intent10713);
    }

    public  void MedicinaFam (View view){

        Intent intentae4 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.sntss.org.mx/convocatorias"));
        startActivity(intentae4);
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
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(getBaseContext(), MenuPrincipal.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

    }
}
