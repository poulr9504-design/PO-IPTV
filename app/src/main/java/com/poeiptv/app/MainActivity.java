
package com.poeiptv.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.*;
import android.text.InputType;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.Executors;

public class MainActivity extends Activity {

    private EditText campoUrl;
    private TextView status;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> canais = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(25, 30, 25, 25);
        layout.setBackgroundColor(Color.rgb(15, 20, 30));

        TextView titulo = new TextView(this);
        titulo.setText("POE IPTV");
        titulo.setTextSize(30);
        titulo.setTextColor(Color.WHITE);
        layout.addView(titulo);

        campoUrl = new EditText(this);
        campoUrl.setHint("Cole sua URL M3U aqui");
        campoUrl.setTextColor(Color.WHITE);
        campoUrl.setHintTextColor(Color.LTGRAY);
        campoUrl.setSingleLine(true);
        campoUrl.setInputType(InputType.TYPE_CLASS_TEXT);
        layout.addView(campoUrl);

        Button carregar = new Button(this);
        carregar.setText("CARREGAR LISTA");
        layout.addView(carregar);

        status = new TextView(this);
        status.setTextColor(Color.WHITE);
        layout.addView(status);

        ListView lista = new ListView(this);
        adapter = new ArrayAdapter<>(
            this,
            android.R.layout.simple_list_item_1,
            canais
        );
        lista.setAdapter(adapter);

        layout.addView(lista, new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            0,
            1
        ));

        carregar.setOnClickListener(v -> {
            String endereco = campoUrl.getText().toString().trim();

            if (!endereco.startsWith("http://")
                    && !endereco.startsWith("https://")) {
                status.setText("Informe uma URL válida.");
                return;
            }

            carregar.setEnabled(false);
            status.setText("Carregando lista...");

            Executors.newSingleThreadExecutor().execute(() -> {
                HttpURLConnection conexao = null;

                try {
                    URL url = new URL(endereco);
                    conexao = (HttpURLConnection) url.openConnection();
                    conexao.setConnectTimeout(15000);
                    conexao.setReadTimeout(20000);
                    conexao.setRequestProperty(
                        "User-Agent", "POE-IPTV/1.0"
                    );

                    int resposta = conexao.getResponseCode();

                    if (resposta < 200 || resposta >= 300) {
                        throw new Exception("Erro HTTP: " + resposta);
                    }

                    BufferedReader leitor = new BufferedReader(
                        new InputStreamReader(
                            conexao.getInputStream(), "UTF-8"
                        )
                    );

                    ArrayList<String> encontrados = new ArrayList<>();
                    String linha;
                    String nome = null;

                    while ((linha = leitor.readLine()) != null) {
                        linha = linha.trim();

                        if (linha.startsWith("#EXTINF")) {
                            int virgula = linha.lastIndexOf(",");
                            nome = virgula >= 0
                                ? linha.substring(virgula + 1).trim()
                                : "Canal";
                        } else if (!linha.isEmpty()
                                && !linha.startsWith("#")
                                && nome != null) {
                            encontrados.add(nome);
                            nome = null;
                        }
                    }

                    leitor.close();

                    runOnUiThread(() -> {
                        canais.clear();
                        canais.addAll(encontrados);
                        adapter.notifyDataSetChanged();

                        if (canais.isEmpty()) {
                            status.setText(
                                "Nenhum canal encontrado. Confira a lista."
                            );
                        } else {
                            status.setText(
                                canais.size() + " canais encontrados."
                            );
                        }

                        carregar.setEnabled(true);
                    });

                } catch (Exception e) {
                    runOnUiThread(() -> {
                        status.setText(
                            "Não foi possível carregar a lista. Confira a URL."
                        );
                        carregar.setEnabled(true);
                    });
                } finally {
                    if (conexao != null) {
                        conexao.disconnect();
                    }
                }
            });
        });

        setContentView(layout);
    }
}
