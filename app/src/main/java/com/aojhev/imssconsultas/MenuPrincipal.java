package com.aojhev.imssconsultas;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;

import java.util.Calendar;
import java.util.Objects;

public class MenuPrincipal extends AppCompatActivity implements View.OnClickListener {
    LinearLayout botontarjeton, botoncalendario, botonpromociones, botonnoticias, botonrol, botonconsulta, botondoc, botoncursos,botonpermuta, botonprestamo, botonconvocaimss;

    InterstitialAd mInterstitialAd;
    AdView mAdView;
    ColorDrawable dialogColor;
    ProgressBar progresbar;
    int califica;
    ImageView imv;
    SharedPreferences sharedPref;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        alarma(this);
        dialogColor = new ColorDrawable(Color.GRAY);
        dialogColor.setAlpha(0);
        super.onCreate(savedInstanceState);

        StartAppSDK.init(this, "210155791", false);
        StartAppAd.disableSplash();
        sharedPref = getSharedPreferences("inicio", Context.MODE_PRIVATE);
        califica = sharedPref.getInt("califica", 0);

        if (califica == 15) {
            dialogocalifica();
            califica = 0;
        } else
            califica++;

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("califica", califica);
        editor.apply();
        if (sharedPref.getBoolean("apps", false)) {

            final AlertDialog.Builder constructor = new AlertDialog.Builder(this);
            View vista = getLayoutInflater().inflate(R.layout.instruccionesapps, null);
            constructor.setView(vista);
            final AlertDialog dialogo = constructor.create();
            Objects.requireNonNull(dialogo.getWindow()).setBackgroundDrawable(dialogColor);
            Button botonok = vista.findViewById(R.id.botonok);
            final CheckBox chbx = vista.findViewById(R.id.chbxdialog);
            botonok.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               SharedPreferences sharedPref;
                                               sharedPref = getSharedPreferences(
                                                       "inicio", Context.MODE_PRIVATE);
                                               SharedPreferences.Editor editor = sharedPref.edit();
                                               editor.putBoolean("apps", chbx.isChecked());
                                               editor.apply();

                                               dialogo.cancel();
                                           }
                                       }
            );
            dialogo.show();
        }
        if (!sharedPref.getBoolean("inicio", false)) {
            final AlertDialog.Builder constructor = new AlertDialog.Builder(this);
            View vista = getLayoutInflater().inflate(R.layout.alert_dialog_inicio, null);
            constructor.setView(vista);
            final AlertDialog dialogo = constructor.create();
            Objects.requireNonNull(dialogo.getWindow()).setBackgroundDrawable(dialogColor);
            Button botonok = vista.findViewById(R.id.botonok);
            final CheckBox chbx = vista.findViewById(R.id.chbxdialog);
            TextView texto = vista.findViewById(R.id.txt);
            texto.setText(getString(R.string.mensajeinicio));
            botonok.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               SharedPreferences sharedPref;
                                               sharedPref = getSharedPreferences(
                                                       "inicio", Context.MODE_PRIVATE);
                                               SharedPreferences.Editor editor = sharedPref.edit();
                                               editor.putBoolean("inicio", chbx.isChecked());
                                               editor.apply();

                                               dialogo.cancel();
                                           }
                                       }
            );
            dialogo.show();
        }

        setContentView(R.layout.activity_menu_principal);
        mAdView = findViewById(R.id.adView1);

        progresbar = findViewById(R.id.pgbr);
        imv = findViewById(R.id.imagevi);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.adinter));
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest1);
        mInterstitialAd.setAdListener(new AdListener(){

            @Override
            public void onAdClosed() {


            }
        });
        botontarjeton = findViewById(R.id.botontarjeton);
        botontarjeton.setOnClickListener(this);
        botonprestamo = findViewById(R.id.botonprestamos);
        botonprestamo.setOnClickListener(this);
        botoncalendario = findViewById(R.id.botoncalendario);
        botoncalendario.setOnClickListener(this);
        botonpermuta = findViewById(R.id.botonpermuta);
        botonpermuta.setOnClickListener(this);
        botonpromociones = findViewById(R.id.botonpromociones);
        botonpromociones.setOnClickListener(this);
        botonnoticias = findViewById(R.id.botonnoticias);
        botonnoticias.setOnClickListener(this);
        botonrol = findViewById(R.id.botonrol);
        botonrol.setOnClickListener(this);
        botonconsulta = findViewById(R.id.botonconsulta);
        botonconsulta.setOnClickListener(this);
        botondoc = findViewById(R.id.botondoc);
        botondoc.setOnClickListener(this);
        botoncursos = findViewById(R.id.botoncursos);
        botoncursos.setOnClickListener(this);
        botonconvocaimss = findViewById(R.id.botonconvocatoria);
        botonconvocaimss.setOnClickListener(this);

    }

    private void dialogocalifica() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipal.this);
        final LayoutInflater inflater = getLayoutInflater();
        View vi = inflater.inflate(R.layout.dialogocalifica, null);
        builder.setView(vi);
        final AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(dialogColor);
        Button botonsi = vi.findViewById(R.id.botonsi);
        botonsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentae4 = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.linkapp)));
                startActivity(intentae4);
            }
        });
        Button botonno = vi.findViewById(R.id.botonno);
        botonno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                        dialog.cancel();
                    }else{
                        dialog.cancel();
                    }
            }
        });

        dialog.show();

    }
    @Override
    public void onBackPressed() {
        StartAppAd.onBackPressed(this);
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.botontarjeton:
                final AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipal.this);
                final LayoutInflater inflater1 = getLayoutInflater();
                View vi = inflater1.inflate(R.layout.dialogo_tarjeton, null);
                builder.setView(vi);
                final AlertDialog dialog = builder.create();
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(dialogColor);
                Button botonok = vi.findViewById(R.id.botoncont);
                final RadioButton rbtna = vi.findViewById(R.id.rbtna);
                final RadioButton rbtnj = vi.findViewById(R.id.rbtnj);
                botonok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (rbtna.isChecked()) {
                            Intent intent1 = new Intent(MenuPrincipal.this, SubMenuActivos.class);
                            startActivity(intent1);
                            finish();
                        }
                        if (rbtnj.isChecked()) {
                            Intent intentae4 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://rh.imss.gob.mx/tarjetonjubilados/(S(lpvgwevvhy0ja2padtk4t12e))/default.aspx"));
                            startActivity(intentae4);
                        }


                    }
                });

                dialog.show();

                break;
            case R.id.botoncalendario:
                Intent intent11 = new Intent(this, Calendario.class);
                startActivity(intent11);
                finish();
                break;

            case R.id.botonconvocatoria:
                Intent intent113 = new Intent(this, ConvocatoriaImss.class);
                startActivity(intent113);
                finish();
                break;

            case R.id.botonpromociones:
                Intent intent1281 = new Intent(this, Promociones.class);
                startActivity(intent1281);
                finish();
                break;

            case R.id.botonprestamos:
                Intent intent1381 = new Intent(this, Prestamos.class);
                startActivity(intent1381);
                finish();
                break;

            case R.id.botonnoticias:

                Intent intent08 = new Intent(this, Noticias.class);
                startActivity(intent08);
                finish();
                break;

            case R.id.botonrol:
                Intent intent13 = new Intent(this, Rol_Vacacional.class);
                startActivity(intent13);
                finish();
                break;

            case R.id.botonconsulta:
                Toast toast3 = new Toast(getApplicationContext());
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast,
                        (ViewGroup) findViewById(R.id.lytLayout));

                TextView txtMsg = layout.findViewById(R.id.txtMensaje);
                txtMsg.setText("Esto puede tardar unos segundos, favor de esperar " +
                        "GRACIAS");

                toast3.setDuration(Toast.LENGTH_LONG);
                toast3.setView(layout);
                toast3.show();

                Intent intent11111 = new Intent(this, MainActivity.class);
                startActivity(intent11111);
                finish();
                break;

            case R.id.botondoc:
                Intent intent121 = new Intent(this, Documentos.class);
                startActivity(intent121);
                finish();
                break;

            case R.id.botonpermuta:
                Intent intent1271 = new Intent(this, Permutas.class);
                startActivity(intent1271);
                finish();
                break;
            case R.id.botoncursos:
                Intent intent1213 = new Intent(this, Cursos.class);
                startActivity(intent1213);
                finish();
                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();

            View vi = inflater.inflate(R.layout.dialogoconfirm, null);
            builder.setView(vi);


            final AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawable(dialogColor);

            //decidir despues si sera cancelable o no
            dialog.setCancelable(false);
            Button botonsi = vi.findViewById(R.id.botonsi);
            botonsi.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                            MenuPrincipal.super.onDestroy();
                            System.exit(0);
                        }
                    }
            );
            Button botonno = vi.findViewById(R.id.botonno);
            botonno.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();

                        }
                    }
            );
            dialog.show();
            //Metodos.dialogo( this, getLayoutInflater(), "¿seguro deseas salir de la aplicacion?", 0 );
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        imv.setVisibility(View.GONE);
        progresbar.setVisibility(View.GONE);
        setTitle(R.string.app_name);
    }

    @Override
    protected void onPause() {
        super.onPause();
        imv.setVisibility(View.VISIBLE);
        progresbar.setVisibility(View.VISIBLE);
        setTitle("Cargando");
    }

    @Override
    protected void onStop() {
        super.onStop();
        imv.setVisibility(View.VISIBLE);
        progresbar.setVisibility(View.VISIBLE);
        setTitle("Cargando");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intentmenu = new Intent();
        switch (item.getItemId()) {
            case R.id.compartir:

                return true;
            case R.id.agenda:
                verificarapp("com.heisenbergtao.manualsupervivencia",this,"https://play.google.com/store/apps/details?id=com.heisenbergtao.manualsupervivencia");

                return true;
            case R.id.diagnostico:
                verificarapp("com.imsstitucional.haisemberg1213",this,"https://play.google.com/store/apps/details?id=com.imsstitucional.haisemberg1213");

                return true;
            default:
                startActivity(intentmenu);
                return super.onOptionsItemSelected(item);
        }

    }

    private void verificarapp(String nombrePaquete, Context context, String link) {
        Intent intentmenu;
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(nombrePaquete, PackageManager.GET_ACTIVITIES);
            intentmenu = getPackageManager().getLaunchIntentForPackage(nombrePaquete);
            startActivity(intentmenu);
        } catch (PackageManager.NameNotFoundException e) {
            intentmenu = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            startActivity(intentmenu);
        }

    }

    private void alarma(Context context) {
        AlarmManager alarmMgr;
        PendingIntent alarmIntent;

        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MenuPrincipal.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

// Alarma a las 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 57);

// Repeticiones en intervalos de 20 minutos
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 20, alarmIntent);
    }
}
