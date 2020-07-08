package com.example.logincadastro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.logincadastro.model.MyCountDownTimer;

// nessa interface será apresentado 2 lógica:
// contagem de tempo até a chegada do veículo e conexão bluetooth bem sucedida
// a outra será a computação do preço do veículo/Hora

    public class MenuLocacao extends AppCompatActivity {

    public static int RETORNO_APLICACAO = 1;// Constante de request Code
    private boolean flag = false;// Será responsável por indicar o retorno
    // E indicar que essa não é a primeira vez em que o usuário está entrando nessa Atividade

    private MyCountDownTimer timerChegarAoVeiculo;
    private TextView txvCronometro, txvNomeVeiculo, txvSaldoTotal, txv2, txv1;
    private Button btnConectar, btnDevolver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_locacao);

        // Escreve o nome do veículo escolhido
        txv1 = (TextView) findViewById(R.id.txv1);
        txv2 = (TextView) findViewById(R.id.txv2);
        txvSaldoTotal = (TextView) findViewById(R.id.txvSaldoTotal);
        btnConectar = (Button)findViewById(R.id.btnConectar);
        btnDevolver = (Button)findViewById(R.id.btnDevolver);

        // Componentes dde crompra ainda invisíveis
        txv2.setVisibility(View.GONE);
        txvSaldoTotal.setVisibility(View.GONE);
        btnDevolver.setVisibility(View.GONE);

        Intent recebeString = getIntent();
        txvNomeVeiculo = (TextView) findViewById(R.id.txvNomeVeiculo);
        txvNomeVeiculo.setText(recebeString.getStringExtra("nomeVeiculo"));
    }

    // O método onResume é o instante em que uma atividade se torna visível na IU
    @Override
    public void onResume(){
        super.onResume();

        // Primeira entrada do usuário
        if(!flag ) {

            txvCronometro = (TextView) findViewById(R.id.txvCronometro);
            //String cronometro = txvCronometro.getText().toString();

            //long l = Long.parseLong(cronometro);

            timerChegarAoVeiculo = new MyCountDownTimer(this, txvCronometro, 30 * 1000, 1000);
            // Para teste do sistema, a chegada ao
            // Veículo deve ser realizada em até 30 seg.
            timerChegarAoVeiculo.start();

            /*if(l == 0){
                Toast.makeText(getApplicationContext(), "Tempo Encerrado",Toast.LENGTH_SHORT).show();
                finish();
            }*/
        }
        else{
            // Representa o retorno para pagamento
            Intent recebeInteiro = getIntent();

            // Calculo baseado nas horas definidas
            int recebido = 0;
            recebido = recebeInteiro.getIntExtra("horasSelecionadas",recebido);

            int calculoTotal = recebido*30;
            Log.i("script",""+recebido);

            txvSaldoTotal.setText((calculoTotal)+",00R$");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Ou seja, caso a activity se encerre por algum motivo, o contador para
        if(timerChegarAoVeiculo != null){
            timerChegarAoVeiculo.cancel();

        }
    }

    // evento de botão
    public void conectarVeiculo(View view){

        try {
            // interrompe a contagem para chegar ao veículo, significando que  o usuário está proximo ao carro
            if (timerChegarAoVeiculo != null) {
                timerChegarAoVeiculo.cancel();
                Toast.makeText(this, "tentatíva de conexão iniciáda", Toast.LENGTH_SHORT).show();
                Log.i("Timer", "A contagem timerChegarAoVeiculo foi encerrada com sucessor");
            }
            Intent intentConectorBluetooth = new Intent(this, InterfaceConexao.class);
            startActivityForResult(intentConectorBluetooth, RETORNO_APLICACAO);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void devolverVeiculo(View view){
        Toast.makeText(this, "Veículo devolvido com sucesso", Toast.LENGTH_SHORT).show();
        finish();
    }
    // Resultado de uma conexão bem sucedida
   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dado){
        if(requestCode  == RETORNO_APLICACAO){
            if (resultCode == RESULT_OK){
                // Quando a conectividade for estabelecida a flag é setada para o retorno
                Toast.makeText(getApplicationContext(), "Obrigado Por Utilizar Nossos Serviços",Toast.LENGTH_SHORT).show();

                // Antigo componente de contador Some
                txv1.setVisibility(View.GONE);
                txvCronometro.setVisibility(View.GONE);
                btnConectar.setVisibility(View.GONE);
                // Àrea de cobrança aparece
                txvSaldoTotal.setVisibility(View.VISIBLE);
                txv2.setVisibility(View.VISIBLE);
                btnDevolver.setVisibility(View.VISIBLE);

                flag = true;
            }
            else{
                // Situação onde o usuário não chegou no veículo ou não estabeleceu conexão
                //Senão ela permanece em false, e o usuário será forçado a retornar a tela de aplicação
                Toast.makeText(getApplicationContext(), "Conexão Com o Veículo não Estabelecida",Toast.LENGTH_SHORT).show();
                flag = false;

                Intent retornaTelaAplicacao = new Intent(this, SelecaoVeiculo.class);
                retornaTelaAplicacao.addFlags(retornaTelaAplicacao.FLAG_ACTIVITY_CLEAR_TOP);// Atividade no topo da pilha
                startActivity(retornaTelaAplicacao);
            }
        }
    }
}
