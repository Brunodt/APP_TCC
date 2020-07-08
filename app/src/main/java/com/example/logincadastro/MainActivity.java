package com.example.logincadastro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.logincadastro.model.BDUsuarios;
import com.example.logincadastro.model.Usuario;

import java.util.ArrayList;

// não confunda as ideias negão
// os parametros que chagam do Cadastro são salvos num array do login
// Aquilo que é digitado no login é utilizado em comparação com aquilo que foi salvo
public class MainActivity extends AppCompatActivity {
    public static int CADASTRO_OK = 1;// Constante de request Code
    //List<Usuario> listaUsuario;
    private EditText edtUsername, edtSenha;
    private Usuario usuario, checkUser;
    //ArrayList<Usuario> listaDeUsuarios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Identificação dos componentes edit text
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
    }

    // método para click de botão
    public void acessarAtividadePrincipal(View view) {

        usuario = new Usuario();
        checkUser = new Usuario();

        BDUsuarios bancoDeDados = new BDUsuarios(this);

        usuario.setUsername(edtUsername.getText().toString());
        usuario.setSenha(edtSenha.getText().toString());

        // Verificação de erros de escrita do usuário///// Caso ele deixe o campo em branco
        if (usuario.getUsername().trim().isEmpty() || usuario.getSenha().trim().isEmpty()) {
            if (usuario.getUsername().trim().isEmpty()) {
                edtUsername.setError("Campo em branco");
                edtUsername.requestFocus();
            } else {
                edtSenha.setError("Campo em branco");
                edtSenha.requestFocus();
            }
        } else {

            try {

                checkUser = bancoDeDados.validarLogin(usuario.getUsername(),usuario.getSenha());
                Log.i("retorno", checkUser.getUsername()+checkUser.getSenha());// Para debug

                //Verifica se usuário está cadastrado no app
                if(checkUser.getUsername() != null && checkUser.getSenha()!= null){// == nulo significa que existe usuário cadastrado
                //if (usuario.getUsername().equals(checkUser.getUsername()) && usuario.getSenha().equals(checkUser.getSenha())) {

                    Log.i("mensagem", "DEBUGADOOOOO");// Para debug

                    //Para abertura da proxima téla
                    Intent intent = new Intent(this, SelecaoVeiculo.class);
                    //intent.putExtra("nomeUsuarioLogado", checkUser.getUsername());// envia o username para proxima tela
                    //setIntent(intent);
                    startActivity(intent);

                    finish();// Sempre que o login for realizado com sucesso, a atividade de login será dstruida
                    // Isso para que o usuário não fique voltando para o login e sendo obrigado a escrever de novo o login
                } else {
                    Toast.makeText(getApplicationContext(), "Não existe esse usuário no sistema", Toast.LENGTH_SHORT).show();// Msg de erro
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "problemas com a checagem", Toast.LENGTH_SHORT).show();// Msg de erro
            }
        }


        /*
        usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setSenha(senha);

        compararUsuario = new Usuario();// Instancia de objeto

        // Conversão do texto escrito pelo usuário em string
        compararUsuario.setUsername(edtUsername.getText().toString());
        compararUsuario.setSenha(edtSenha.getText().toString());

        // Verificação de erros de escrita do usuário///// Caso ele deixe o campo em branco
        if (compararUsuario.getUsername().trim().isEmpty() || compararUsuario.getSenha().trim().isEmpty()) {
            if (compararUsuario.getUsername().trim().isEmpty()) {
                edtUsername.setError("Campo em branco");
                edtUsername.requestFocus();
            } else {
                edtSenha.setError("Campo em branco");
                edtSenha.requestFocus();
            }
        } else {

            try {

                if (!listaDeUsuarios.isEmpty()) {
                    Log.i ("mensagem", compararUsuario.getUsername()+""+compararUsuario.getSenha());// Para debug

                    //Verifica se usuário está cadastrado no app
                    if(usuario.getUsername().equals(compararUsuario.getUsername()) && usuario.getSenha().equals(compararUsuario.getSenha()) ) {

                        Log.i ("mensagem", "DEBUGADOOOOO");// Para debug

                        //Para abertura da proxima téla
                        Intent intent = new Intent(this, SelecaoVeiculo.class);
                        intent.putExtra("nomeUsuarioLogado",compararUsuario.getUsername());// envia o username para proxima tela
                        setIntent(intent);
                        startActivity(intent);

                        finish();// Sempre que o login for realizado com sucesso, a atividade de login será dstruida
                        // Isso para que o usuário não fique voltando para o login e sendo obrigado a escrever de novo o login
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Não existe esse usuário no sistema", Toast.LENGTH_SHORT).show();// Msg de erro
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        */
    }

    // Acessar atividade de Cadastro
    public void acessarCadastro(View view) {
        Intent intent = new Intent(this, Cadastro.class);
        startActivity(intent);
    }

    /*@Override// Recebimento de parâmetros da atividade de cadastro
    public void onActivityResult(int requestCode, int resultCode, Intent dado) {

        if (requestCode == CADASTRO_OK) {
            if (resultCode == RESULT_OK) {
                try {
                    Log.i("mensagem", "Setando resultados no vetor");

                    usuario = new Usuario();

                    usuario.setUsername(dado.getStringExtra("nome_usuario"));
                    usuario.setSenha(dado.getStringExtra("senha_usuario"));

                    listaDeUsuarios.add(usuario);// Salva no vetor
                    Toast.makeText(getApplicationContext(), "parametros de usuário = " + usuario.getUsername(), Toast.LENGTH_SHORT).show();
                    Log.i("mensagem", "parametros de usuário = " + usuario.getUsername());// Para debug

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }*/

    // Método utilizado para recuperar o estado da lista de Usuários quando for iniciáda a proxima activity
   /*  @Override
   protected void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);

        bundle.putSerializable(ListaDeUsuarios.KEY, new ListaDeUsuarios(listaDeUsuarios));
    }*/
}
