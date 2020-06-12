package com.aojhev.imssconsultas;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Jubilados extends AppCompatActivity implements View.OnClickListener {
    AdView mAdView;
    ProgressBar progresbar;
    WebView webview;
    String  mes, year;
    ImageView imv;
    boolean  mesb, yearb;
    Button btndescargar1;
    static int[] id = new int[]{R.drawable.ins1, R.drawable.ins2, R.drawable.ins3, R.drawable.ins4, R.drawable.ins5ju, R.drawable.ins6jub, R.drawable.ins7};


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jubilados);


        SharedPreferences sharedPref;
        sharedPref = getSharedPreferences("instruccionesj", Context.MODE_PRIVATE);
        if (!sharedPref.getBoolean("instruccionesj", false)) {
            final int[] cont = {0};
            final AlertDialog.Builder constructor = new AlertDialog.Builder(this);
            View vista = getLayoutInflater().inflate(R.layout.instrucciones, null);
            constructor.setView(vista);
            final AlertDialog dialogo = constructor.create();
            final Button botonext = vista.findViewById(R.id.botonnext);
            final Button botonback = vista.findViewById(R.id.botonback);
            botonback.setVisibility(View.INVISIBLE);
            final CheckBox chbx = vista.findViewById(R.id.chbxdialog);
            final ImageView imageV = vista.findViewById(R.id.imgvw);
            //TextView texto = vista.findViewById(R.id.txt);
            // texto.setText(getString(R.string.mensajeinicio));
            botonback.setOnClickListener(new View.OnClickListener() {
                                             @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                             @Override
                                             public void onClick(View v) {
                                                 if (cont[0] == 6) {
                                                     botonext.setBackground(getResources().getDrawable(R.drawable.btnflechar));
                                                 }
                                                 if (cont[0] == 1) {
                                                     botonback.setVisibility(View.INVISIBLE);
                                                 }
                                                 cont[0] = instruccionesbotones(imageV, cont[0], false, Jubilados.this);

                                             }
                                         }
            );
            botonext.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {
                    if (cont[0] == 0) {
                        botonback.setVisibility(View.VISIBLE);
                    }
                    if (cont[0] == 5) {
                        botonext.setBackground(getResources().getDrawable(R.drawable.btnchck));
                    }
                    if (cont[0] == 6) {
                        SharedPreferences sharedPref;
                        sharedPref = getSharedPreferences(
                                "instruccionesj", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putBoolean("instruccionesj", chbx.isChecked());
                        editor.apply();
                        dialogo.cancel();
                    } else {
                        cont[0] = instruccionesbotones(imageV, cont[0], true, Jubilados.this);
                    }

                }
            });
            dialogo.show();
        }


        imv = findViewById(R.id.imagevi);


        mAdView = findViewById(R.id.banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        progresbar = findViewById(R.id.pgbr);
        webview = findViewById(R.id.WebView);
        btndescargar1 = findViewById(R.id.btndescargar);
        btndescargar1.setOnClickListener(this);

        validaPermisos();


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            // Si hay conexión a Internet en este momento

            WebSettings webSettings = webview.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webview.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    // Activities and WebViews measure progress with different scales.
                    // The progress meter will automatically disappear when we reach 100%
                    Jubilados.this.setProgress(progress * 1000);
                }
            });
            webview.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    progresbar.setVisibility(View.VISIBLE);
                    imv.setVisibility(View.VISIBLE);
                    setTitle(" Cargando ");
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    progresbar.setVisibility(View.GONE);
                    imv.setVisibility(View.GONE);
                    setTitle("Descarga tu tarjeton");
                }

            });
            webview.loadUrl("http://rh.imss.gob.mx/tarjetonjubilados/");
            webview.getSettings().setBuiltInZoomControls(true);


        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View vi = inflater.inflate(R.layout.dialogoconfirm, null);
            TextView txtv = vi.findViewById(R.id.txtconfirm);
            txtv.setText("No cuentas con conexion a internet ");
            builder.setView(vi);
            final AlertDialog dialog = builder.create();
            dialog.setCancelable(false);
            Button botonsi = vi.findViewById(R.id.botonsi);
            botonsi.setText("Reintentar");
            botonsi.setTextSize(10);
            botonsi.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setClass(Jubilados.this, Jubilados.this.getClass());
                            //llamamos a la actividad
                            Jubilados.this.startActivity(intent);
                            //finalizamos la actividad actual
                            Jubilados.this.finish();
                        }
                    }
            );
            Button botonno = vi.findViewById(R.id.botonno);
            botonno.setTextSize(10);
            botonno.setText("Volver");
            botonno.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getBaseContext(), MenuPrincipal.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                            finish();
                        }
                    }
            );
            dialog.show();
            //Metodos.dialogo( this, getLayoutInflater(), "¿seguro deseas salir de la aplicacion?", 0 );
        }

    }

    private void validaPermisos() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        if ((checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            return;
        }

        if ((shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))) {
            AlertDialog.Builder dialogo = new AlertDialog.Builder(Jubilados.this);
            dialogo.setTitle("Permisos Desactivados");
            dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

            dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 100);
                }
            });
            dialogo.show();
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 100);
        }

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
        dialogo1(this);

        webview.loadUrl("http://rh.imss.gob.mx/tarjetonjubilados/(S( ))/Reportes/Web/wfrReporteTarjeton.aspx");


        webview.getSettings().setBuiltInZoomControls(true);
        webview.setDownloadListener(new DownloadListener() {
                                        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                                            //for downloading directly through download manager
                                            Toast.makeText(Jubilados.this, "descargando", Toast.LENGTH_LONG).show();
                                            final DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                                            request.allowScanningByMediaScanner();
                                            request.setMimeType(mimetype);
                                            //------------------------COOKIE------------------------
                                            String cookies = CookieManager.getInstance().getCookie(url);
                                            request.addRequestHeader("cookie", cookies);
                                            //------------------------COOKIE------------------------
                                            request.addRequestHeader("User-Agent", userAgent);
                                            request.setDescription("Downloading file...");
                                            request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype));
                                            request.allowScanningByMediaScanner();
                                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimetype));
                                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                            final DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                                            new Thread("Browser download") {
                                                public void run() {
                                                    dm.enqueue(request);

                                                }
                                            }.start();

                                        }
                                    }

        );

    }

    private void creararchivo(boolean chequed, String nombre) {
        File currentFile = new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/wfrReporteTarjeton.aspx");

        File newFile = new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + nombre + "wfrReporteTarjeton.aspx");

        rename(currentFile, newFile);


        if (chequed) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Toast.makeText(this, "abrir con la app de consulta tarjeton " + String.valueOf(newFile), Toast.LENGTH_LONG).show();
                intent.setDataAndType(Uri.parse(String.valueOf(newFile)), "application/pdf");
            } else {
                intent.setDataAndType(Uri.fromFile(newFile), "application/pdf");
            }




            try {
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Jubilados.this.startActivity(Intent.createChooser(intent, "Abrir con"));

            } catch (ActivityNotFoundException e) {
                Toast.makeText(Jubilados.this, "No existe una aplicación para abrir el PDF", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void rename(File from, File to) {
        if (from.getParentFile().exists() && from.exists()) {
            from.renameTo(to);
        }
    }

    private void dialogo1(final Context cont) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        View vi = inflater.inflate(R.layout.dialogonamejub, null);
        builder.setView(vi);


        final AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        Button botonok = vi.findViewById(R.id.botonokspiner);

        final CheckBox chbx = vi.findViewById(R.id.chbx);


        Spinner spinnerm = vi.findViewById(R.id.spinnerm);
        ArrayAdapter<CharSequence> adaptadorm = ArrayAdapter.createFromResource(cont, R.array.mes, android.R.layout.simple_spinner_item);
        spinnerm.setAdapter(adaptadorm);
        spinnerm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    mesb = false;
                } else {
                    mesb = true;
                }
                mes = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Spinner spinnera = vi.findViewById(R.id.spinnera);
        ArrayAdapter<CharSequence> adaptadora = ArrayAdapter.createFromResource(cont, R.array.year, android.R.layout.simple_spinner_item);
        spinnera.setAdapter(adaptadora);
        spinnera.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    yearb = false;
                } else {
                    yearb = true;
                }
                year = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        botonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (mesb) {
                        if (yearb) {
                            creararchivo(chbx.isChecked(),   mes + year);
                            dialog.cancel();
                        } else
                            Toast.makeText(cont, "Selecciona el año", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(cont, "Selecciona el mes", Toast.LENGTH_LONG).show();


            }
        });


        dialog.show();
    }

    public void salirdialogjubilados(View view) {

        Intent intent08 = new Intent(this, Jubilados.class);
    startActivity(intent08);
}

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    static public int instruccionesbotones(ImageView imv, int num, boolean contador, Context context) {
        if (contador) {
            num++;
        } else {
            num--;
        }
        imv.setBackground(context.getResources().getDrawable(id[num]));


        return num;
    }

}