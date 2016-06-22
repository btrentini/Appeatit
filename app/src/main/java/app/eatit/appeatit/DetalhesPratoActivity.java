package app.eatit.appeatit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import app.eatit.appeatit.Model.Refeicao;
import app.eatit.appeatit.R;

public class DetalhesPratoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Intent params;
    private Refeicao refeicao;
    private TextView nomeRefeicao,preco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_prato);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        params = getIntent();
        refeicao = params.getParcelableExtra("refeicao");

        nomeRefeicao = (TextView) findViewById(R.id.nomeRefeicao);
        preco = (TextView) findViewById(R.id.preco);
        nomeRefeicao.setText(refeicao.getNome());
        preco.setText("$"+refeicao.getValor());


        Log.d("Log",refeicao.getNome());
    }
}
