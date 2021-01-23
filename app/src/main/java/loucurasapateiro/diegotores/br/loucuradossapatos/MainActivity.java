package loucurasapateiro.diegotores.br.loucuradossapatos;


import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import loucurasapateiro.diegotores.br.loucuradossapatos.WebService.Espaco;
import loucurasapateiro.diegotores.br.loucuradossapatos.WebService.EspacoREST;

public class MainActivity extends AppCompatActivity {

    private WebView web;
    private View progresso, relative;
    private Toast toast;
    private long lastBackPressTime = 0;
    public String espaco_padrao = "https://www.miarte.com.br/diegotorres";
    public String espaco;
    public static Toolbar toolbar;
    private AlertDialog alerta;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        relative = (RelativeLayout) findViewById(R.id.progresso);
        progresso = findViewById(R.id.avloadingIndicatorView);
        web = (WebView) findViewById(R.id.webView);
        web.getSettings().setJavaScriptEnabled(true);
        web.setWebViewClient(new ClienteWeb());
        ChecarCompatibilidade();
        obter_espaco();
        web.loadUrl(espaco_padrao);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Integer id = item.getItemId();

        Log.i("menu", id.toString());
        Integer id2 = R.id.action_settings;
        Log.i("menu", id2.toString());

        if(id == R.id.action_settings){

            final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
            builder.setTitle("Miarte Calçados");
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            alerta = builder.create();
            alerta.show();

        }

        return super.onOptionsItemSelected(item);

    }

    public void obter_espaco(){


        try {
            EspacoREST consultorREST = new EspacoREST();
            ArrayList<Espaco> c = consultorREST.execute().get();

            this.espaco = c.get(0).getEspaco();
            this.espaco_padrao = espaco;
            Log.i("consultor", this.espaco);

        } catch (Exception e) {
            Log.e("erro", e.getMessage());
        }

    }



    private class ClienteWeb extends WebViewClient {

        /*@Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }*/

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            web.setVisibility(View.GONE);
            relative.setVisibility(View.VISIBLE);
            progresso.setVisibility(View.VISIBLE);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progresso.setVisibility(View.GONE);
            web.setVisibility(View.VISIBLE);
            relative.setVisibility(View.GONE);
            //Principal_2.toolbar.setTitle(view.getTitle());
            super.onPageFinished(view, url);
        }

    }

    private void ChecarCompatibilidade(){
        int versao = Build.VERSION.SDK_INT;

        if (versao >= 21){
            web.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    @Override
    public void onBackPressed() {
        if(web.canGoBack()){
            web.goBack();

        }else {

            if (this.lastBackPressTime < System.currentTimeMillis() - 4000) {
                toast = Toast.makeText(this, "Pressione o Botão Voltar novamente para fechar o Aplicativo.", Toast.LENGTH_LONG);
                toast.show();
                this.lastBackPressTime = System.currentTimeMillis();
            } else {
                if (toast != null) {
                    toast.cancel();
                }
                super.onBackPressed();
            }
        }
    }


}
