package com.poeiptv.app;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(25, 40, 25, 25);

        TextView titulo = new TextView(this);
        titulo.setText("POE IPTV");
        titulo.setTextSize(28);
        layout.addView(titulo);

        TextView mensagem = new TextView(this);
        mensagem.setText("Seu aplicativo está pronto para receber uma lista IPTV autorizada.");
        mensagem.setTextSize(18);
        layout.addView(mensagem);

        setContentView(layout);
    }
}
