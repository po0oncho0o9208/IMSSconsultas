package com.aojhev.imssconsultas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.widget.Toast.LENGTH_LONG;

public class InfoCorona extends AppCompatActivity implements View.OnClickListener {
    ImageView visor;
    Button compartir, derecha, izquierda;
    int[] imagenes = {R.drawable.infocorona0, R.drawable.infocorona1, R.drawable.infocorona2, R.drawable.infocorona3, R.drawable.infocorona4 , R.drawable.infocorona5};
    int regulador = 0, reguladoranuncios = 0;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    String aplicacionelegida = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        setContentView(R.layout.activity_infocorona);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        validaPermisos();
        visor = findViewById(R.id.imageviewcorona);
        visor.setImageResource(R.drawable.infocorona0);
        visor.getTag();
        compartir = findViewById(R.id.botoncorona);
        compartir.setOnClickListener(this);
        izquierda = findViewById(R.id.izq);
        izquierda.setOnClickListener(this);
        derecha = findViewById(R.id.der);
        derecha.setOnClickListener(this);
        izquierda.setEnabled(false);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9129010539844350/3517030849");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
               // Toast.makeText(InfoCorona.this,"supone cerrado", LENGTH_LONG).show();
                enviarimagen(aplicacionelegida);
                // Code to be executed when the interstitial ad is closed.
            }
        });



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.botoncorona:
                dialogomensaje();
                break;
            case R.id.der:
                if (regulador < 5 ) {
                    izquierda.setEnabled(true);
                    regulador++;
                    visor.setImageResource(imagenes[regulador]);
                    if (regulador == 5){
                        derecha.setEnabled(false);
                    }
                }
                break;
            case R.id.izq:
                if (regulador > 0 ) {
                    derecha.setEnabled(true);
                    regulador--;
                    visor.setImageResource(imagenes[regulador]);
                    if (regulador == 0){
                        izquierda.setEnabled(false);
                    }
                }
                break;

        }

    }
    private void dialogomensaje() {
       ColorDrawable dialogColor = new ColorDrawable(Color.GRAY);
        dialogColor.setAlpha(0);
        final AlertDialog.Builder builder = new AlertDialog.Builder(InfoCorona.this);
        final LayoutInflater inflater = getLayoutInflater();
        View vi = inflater.inflate(R.layout.dialogocompartircorona, null);
        builder.setView(vi);
        Button whats = vi.findViewById(R.id.botonwhats);
        whats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    aplicacionelegida = "com.whatsapp";
                    mInterstitialAd.show();
                } else {
                    enviarimagen("com.whatsapp");
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }

            }
        });
        Button face = vi.findViewById(R.id.botonface);
        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    aplicacionelegida = "com.facebook.katana";
                    mInterstitialAd.show();
                } else {
                    enviarimagen("com.facebook.katana");
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }

            }
        });
        Button messe = vi.findViewById(R.id.botonmessenger);
        messe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    aplicacionelegida = "com.facebook.orca";
                    mInterstitialAd.show();
                } else {
                    enviarimagen("com.facebook.orca");
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }

            }
        });
        Button mas = vi.findViewById(R.id.botonmas);
        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    aplicacionelegida = "otra";
                    mInterstitialAd.show();
                } else {
                    enviarimagen("otra");
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(dialogColor);
        dialog.show();

    }
    private void enviarimagen (String paquete){

        try {
            Uri imageUri = null;
            try {
                imageUri = Uri.parse(MediaStore.Images.Media.insertImage(this.getContentResolver(),
                        BitmapFactory.decodeResource(getResources(), imagenes[regulador]), null, null));
            } catch (NullPointerException e) {


            }

            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            if (paquete != "otra")
                whatsappIntent.setPackage(paquete);
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Hola te comporta esta información para ayudarte en esta contingencia, cuidemonos entre todos. Saludos");
            whatsappIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, "descarga la aplicacion Consultas IMSS para mas informacion https://play.google.com/store/apps/details?id=com.aojhev.imssconsultas ");
            whatsappIntent.setType("image/jpeg");
            whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            try {
                this.startActivity(whatsappIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "No tienes instalada esta aplicacion", LENGTH_LONG).show();
                // ToastHelper.MakeShortText("Whatsapp have not been installed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void validaPermisos() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        if ((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            return;
        }

        if ((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))) {
            AlertDialog.Builder dialogo = new AlertDialog.Builder(InfoCorona.this);
            dialogo.setTitle("Permisos Desactivados");
            dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

            dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    requestPermissions(new String[]{ WRITE_EXTERNAL_STORAGE}, 100);
                }
            });
            dialogo.show();
        } else {
            requestPermissions(new String[]{ WRITE_EXTERNAL_STORAGE}, 100);
        }

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