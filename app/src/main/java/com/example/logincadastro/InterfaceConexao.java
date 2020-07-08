package com.example.logincadastro;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;

import com.example.logincadastro.bluetoothHelper.ConnectionThread;
import com.example.logincadastro.bluetoothHelper.DescobrirDispositivos;
import com.example.logincadastro.bluetoothHelper.DispositivosPareados;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class InterfaceConexao extends AppCompatActivity {

    // Constantes do Blootooth
    public static int ENABLE_BLUETHOOTH = 1;
    public static int SELECT_PAIRED_DEVICE = 2;
    public static int SELECT_DISCOVERED_DEVICE = 3;

    // Constantes de Controle do veículo
    public static String LIGAR_VEICULO = "Veículo Ligado\n";
    public static String DESLIGAR_VEICULO = "Veículo Desligado\n";
    public static String TRAVAR_PORTAS = "Portas Travadas\n";
    public static String DESTRAVAR_PORTAS = "Portas Destravadas\n";

    static TextView txvEstadoMsg;
    ConnectionThread conexao;
    Switch swtTravas;
    Button btnStartStop;

    public static boolean chaveBotao = true;// Controle Start - Stop

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface_conexao);

        // Identificação dos componentes de tela
        btnStartStop = (Button) findViewById(R.id.btnStartStop);
        txvEstadoMsg = (TextView) findViewById( R.id.txvEstadoMsg);
        swtTravas = (Switch) findViewById(R.id.swtTravas);

        // Inicializa os elementos de tela
        swtTravas.getTextOff();// Configura o texto do switch
        btnStartStop.setBackgroundResource(R.color.Red);// Muda cor do botão --> vermelho
        btnStartStop.setText(LIGAR_VEICULO); // --> seta o texto

        // Mantem os componentes invisíveis
        swtTravas.setVisibility(View.INVISIBLE);
        btnStartStop.setVisibility(View.INVISIBLE);


        // Permissão para acionar o bluetooth
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if(btAdapter == null){
            txvEstadoMsg.setText("Que pena! Hardware Bluetooth não está funcionando :(");
        }else{
            if(!btAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, ENABLE_BLUETHOOTH);
                txvEstadoMsg.setText("Solicitando ativação do Bluetooth...");

            } else {
                txvEstadoMsg.setText("Bluetooth já ativado :)");
            }
        }
        // Evento do botão Switch para travas eletrônicas
        swtTravas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    swtTravas.getTextOn();
                    // Converte a mensagem em um Byte
                    byte[] data = DESTRAVAR_PORTAS.getBytes();
                    // Envia pela thread
                    conexao.write(data);
                } else{
                    swtTravas.getTextOff();
                    // Converte a mensagem em um Byte
                    byte[] data = TRAVAR_PORTAS.getBytes();
                    // Envia pela thread
                    conexao.write(data);
                }
            }
        });
    }
    // Evento do botão STart Stop
    public void botaoStartStop(View view){

        // Envio de mensagens pelo bluetooth
        if(chaveBotao) {
            // Ligar
            btnStartStop.setBackgroundResource(R.color.green);// Muda cor do botão --> verde
            btnStartStop.setText(LIGAR_VEICULO); // --> seta o texto
            // Converte a mensagem em um Byte
            byte[] data = LIGAR_VEICULO.getBytes();
            // Envia pela thread
            conexao.write(data);

            chaveBotao = !chaveBotao;
        }
        else{
            // Desligar
            // Converte a mensagem em um Byte
            btnStartStop.setBackgroundResource(R.color.Red);// Muda cor do botão --> vermelho
            btnStartStop.setText(DESLIGAR_VEICULO); // --> seta o texto
            byte[] data = DESLIGAR_VEICULO.getBytes();
            // Envia pela thread
            conexao.write(data);

            chaveBotao = !chaveBotao;
        }
    }
    // Lista de Disp Pareados
    public void dispositivosPareados(View view){

        Intent intentDispPareados = new Intent(InterfaceConexao.this, DispositivosPareados.class);
        startActivityForResult(intentDispPareados, SELECT_PAIRED_DEVICE);

    }
    // Busca de Disp
    public void procurarDispositivos(View view){

        Intent intentProcurarDisp = new Intent(InterfaceConexao.this, DescobrirDispositivos.class);
        startActivityForResult(intentProcurarDisp, SELECT_DISCOVERED_DEVICE);
    }
    // Habilita a visibilidade do bluetooth do celular
    public void habilitarVisibilidade(View view) {

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 30);
        startActivity(discoverableIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);/////////////////////////////////////
        if (requestCode == ENABLE_BLUETHOOTH) {
            if (resultCode == RESULT_OK) {
                txvEstadoMsg.setText("Bluetooth ativado :D");
            } else {
                //txvEstadoMsg.setText("Bluetooth não ativado :(");
                Toast.makeText(this, "Bluetooth Não Ativado",Toast.LENGTH_SHORT).show();
                finish();
            }
        }else if (requestCode == SELECT_PAIRED_DEVICE || requestCode == SELECT_DISCOVERED_DEVICE) {
            if (resultCode == RESULT_OK) {

                txvEstadoMsg.setText("Você selecionou " + data.getStringExtra("btDevName") + "\n"
                        + data.getStringExtra("btDevAddress"));

                //Conectar com o dispositivo
                conexao = new ConnectionThread(data.getStringExtra("btDevAddress"));
                conexao.start();

                // Habilita a Visibilidade para interação do usuário
                swtTravas.setVisibility(View.VISIBLE);
                btnStartStop.setVisibility(View.VISIBLE);

                // Avisa a tela onde ocorre a cobrança que a conexão foi estabelecida
                Intent intent = new Intent(this, MenuLocacao.class);
                setResult(RESULT_OK, intent);// Vai computar o preço quando retornar

            } else {
                txvEstadoMsg.setText("Nenhum dispositivo selecionado :(");
            }
        }
    }
    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            Bundle bundle = msg.getData();
            byte[] data = bundle.getByteArray("data");
            String dataString= new String(data);

            if(dataString.equals("---N"))
                txvEstadoMsg.setText("Ocorreu um erro durante a conexão D:");
            else if(dataString.equals("---S"))
                txvEstadoMsg.setText("Conectado :D");
            else {

                //textSpace.setText(new String(data));
            }
        }
    };
}
