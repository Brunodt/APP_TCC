package com.example.logincadastro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class SelecaoVeiculo extends AppCompatActivity {

    // Definido aquí os comes dos veículos e as imagens que irão popular o Spinner box
    private String[] carrosNome = {"Chevrolet Captiva", "Hyundai HB20", "Renault Logan",
            "Toyota Corolla Altis", "Volkswagen Virtus"};

    private int[] carroImagem = {R.drawable.gm_captiva, R.drawable.hyundai_hb20, R.drawable.renault_logan,
            R.drawable.toyota_corolla, R.drawable.volkswagen_virtus};

    //URL das descrições dos veículos
    private String urlCaptiva = "https://onedrive.live.com/?cid=600F38FF77B71F09&id=600F38FF77B71F09%211272&parId=600F38FF77B71F09%211221&o=OneUp";
    private String urlHB20 = "https://onedrive.live.com/?cid=600F38FF77B71F09&id=600F38FF77B71F09%211273&parId=600F38FF77B71F09%211221&o=OneUp";
    private String urlLogan = "https://onedrive.live.com/?cid=600F38FF77B71F09&id=600F38FF77B71F09%211274&parId=600F38FF77B71F09%211221&o=OneUp";
    private String urlCorolla = "https://onedrive.live.com/?cid=600F38FF77B71F09&id=600F38FF77B71F09%211275&parId=600F38FF77B71F09%211221&o=OneUp";
    private String urlVirtus = "https://onedrive.live.com/?cid=600F38FF77B71F09&id=600F38FF77B71F09%211223&parId=600F38FF77B71F09%211221&o=OneUp";

    // Declara os componentes
    private EditText edtHora ;
    private Spinner spnCarros;
    private ImageView ivImagem;

    private int posicao; // posição do intent vetor

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecao_veiculo);

        //Identificação dos componentes
        spnCarros = (Spinner) findViewById(R.id.spnCarros);
        ivImagem = (ImageView) findViewById(R.id.ivImagem);

        //Adaptador do vetor de String
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, carrosNome);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCarros.setAdapter(adapter);

        //Quando um Item do Spinner Box é selecionado:
        spnCarros.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ivImagem.setImageResource(carroImagem[position]);// Altera a imagem correspondendo o veículo em questão
                posicao = position;
                Log.i("script", "A posição atual"+ posicao);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                /// não necessita de ser Implementado, mas é um método obrigatório
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Intent pegarNomeUsuario = getIntent();
        //Toast.makeText(getApplicationContext(), "Bem vindo senhor: "+ pegarNomeUsuario.getStringExtra("nomeUsuarioLogado"),Toast.LENGTH_SHORT).show();
    }

    // evento de botão
    public void acessarDescricao(View vie) {

        Intent intentVetor[] = new Intent[5];// vetor de Intent

        //A descrição dos veículos vai depender da posição em que se encontra o Spinner
        //Carrega url na posição 0
        intentVetor[0] = new Intent(Intent.ACTION_VIEW);
        intentVetor[0].setData(Uri.parse(urlCaptiva));

        //Carrega url na posição 1
        intentVetor[1] = new Intent(Intent.ACTION_VIEW);
        intentVetor[1].setData(Uri.parse(urlHB20));

        //Carrega url na posição 2
        intentVetor[2] = new Intent(Intent.ACTION_VIEW );
        intentVetor[2].setData(Uri.parse(urlLogan));

        //Carrega url na posição 3
        intentVetor[3] = new Intent(Intent.ACTION_VIEW );
        intentVetor[3].setData(Uri.parse(urlCorolla));

        //Carrega url na posição 4
        intentVetor[4] = new Intent(Intent.ACTION_VIEW );
        intentVetor[4].setData(Uri.parse(urlVirtus));
        //Mostra a
        startActivity(intentVetor[posicao]);
    }

    public void selecionarVeiculo(View view) {

        edtHora = (EditText) findViewById(R.id.edtHoras);
        String campoHoras = edtHora.getText().toString();

        if(campoHoras.trim().isEmpty() || campoHoras.equals("0") ) {// Verifica se o campo EDT está vazio
            edtHora.setError("Campo Vazio ou Em Zero");
            edtHora.requestFocus();
        }
        else{

            int h = Integer.parseInt(campoHoras);
            // Envia o nome do veículo e o numero de horas selecionado para a  Atividade que realiza a conexão com o veículo
            try {
                Intent intentNomeVeiculo = new Intent(this, MenuLocacao.class);
                intentNomeVeiculo.putExtra("horasSelecionadas",h);//Inteiro
                intentNomeVeiculo.putExtra("nomeVeiculo", carrosNome[posicao]);//String

                Log.i("TAG", "O nome do veículo: " + "" +carrosNome[posicao]+ ""+ "horas escolhidas: "+h);

                startActivity(intentNomeVeiculo);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
