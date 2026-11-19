
package com.poeiptv.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.ViewGroup;
import android.view.View;
import android.widget.*;
import android.text.InputType;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(25, 35, 25, 25);
        layout.setBackgroundColor(Color.rgb(15, 20, 30));

        TextView titulo = new TextView(this);
        titulo.setText("POE IPTV");
        titulo.setTextSize(30);
        titulo.setTextColor(Color.WHITE);
        layout.addView(titulo);

        TextView subtitulo = new TextView(this);
        subtitulo.setText("Sua televisão em um só lugar");
        subtitulo.setTextColor(Color.LTGRAY);
        subtitulo.setTextSize(16);
        layout.addView(subtitulo);

        EditText url = new EditText(this);
        url.setHint("COLE SUA URL M3U AQUI");
        url.setTextColor(Color.WHITE);
        url.setHintTextColor(Color.LTGRAY);
        url.setSingleLine(true);
        url.setTextSize(16);
        url.setPadding(20, 15, 20, 15);
        url.setBackgroundColor(Color.rgb(55, 65, 80));
        url.setInputType(InputType.TYPE_CLASS_TEXT);
        layout.addView(url, new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        Button carregar = new Button(this);
        carregar.setText("CARREGAR LISTA");
        layout.addView(carregar);

        TextView resultado = new TextView(this);
        resultado.setTextColor(Color.WHITE);
        resultado.setTextSize(17);
        layout.addView(resultado);

        carregar.setOnClickListener(v -> {
            String endereco = url.getText().toString().trim();

            if (endereco.isEmpty()) {
                resultado.setText("Digite sua URL M3U.");
            } else if (!endereco.startsWith("https://")
                    && !endereco.startsWith("http://")) {
                resultado.setText("Confira o endereço informado.");
            } else {
                resultado.setText(
                    "Endereço recebido! Próxima etapa: carregar os canais."
                );
            }
        });

        setContentView(layout);
    }
}
