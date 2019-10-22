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

public class Documentos extends AppCompatActivity {

    AdView mAdView;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //botonatras
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setContentView(R.layout.activity_documentos);
        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }
    public void contrato (View view){
        Intent intent1=new Intent(this,Contratos.class);
        startActivity(intent1);
    }
    public void Convenio (View view){
        Intent intent121=new Intent(this,Txt.class);
        startActivity(intent121);
    }

    public void Modifi (View view){
        Intent intent1331=new Intent(this,ModificacionesContrato.class);
        startActivity(intent1331);
    }

    public void Tabulador (View view){
        Intent intent133=new Intent(this,TabuladorSueldos.class);
        startActivity(intent133);
    }

    public  void OtrosDocunentos (View view){

        Intent intentae = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.sntss.org.mx/requerimientos/leyes-y-reglamentos-gfee"));
        startActivity(intentae);
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

}

