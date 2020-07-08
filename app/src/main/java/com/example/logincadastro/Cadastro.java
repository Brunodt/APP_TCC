package com.example.logincadastro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.logincadastro.model.BDHelper;
import com.example.logincadastro.model.BDUsuarios;
import com.example.logincadastro.model.Usuario;

import java.util.ArrayList;

public class Cadastro extends AppCompatActivity {
    // Desclaração de variáveis e entradas
    private EditText edtUsername2, edtSenha2, edtReescreva;
    private String r, username, senha;
    private Usuario usuario, checkUser;
    BDUsuarios bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //Setando os Id's dos Textos editáveis
        edtUsername2 = (EditText) findViewById(R.id.edtUsername2);
        edtSenha2 = (EditText) findViewById(R.id.edtSenha2);
        edtReescreva = (EditText) findViewById(R.id.edtReescreva);
    }

    public void salvarCadastro(View view) {

        usuario = new Usuario();
        bd = new BDUsuarios(getApplicationContext());

        usuario.setUsername(edtUsername2.getText().toString());
        usuario.setSenha(edtSenha2.getText().toString());

        username = usuario.getUsername();
        senha = usuario.getSenha();
        r = edtReescreva.getText().toString();

        // Passando os parâmetros para a activity de Login
        try {
            // Verificação dos campos de usuário
            //Verificação dos campos de texto: se está em branco, se a senha é < 6
            checkUser = new Usuario();
            checkUser = bd.checarUsuarioPorUsername(username);// se nulo == já existe um usuário com esse username

            if (username.trim().isEmpty() || senha.length() < 6 || !r.equals(senha) || checkUser != null) {
                if (username.trim().isEmpty()) {
                    edtUsername2.setError("O campo de nome está vazio");
                    edtUsername2.requestFocus();
                } else if (senha.length() < 6) {
                    edtSenha2.setError("Não é permitido uma senha com menos de 6 dígitos");
                    edtSenha2.requestFocus();
                } else if (!r.equals(senha)) {
                    edtReescreva.setError("Senhas diferentes");
                    edtSenha2.requestFocus();
                } else if (checkUser != null){
                    Toast.makeText(this, "Esse Usuário já existe", Toast.LENGTH_SHORT).show();
                }
            } else{

                Log.i("mensagem", "Cadastro" + usuario.getUsername() + "" + usuario.getSenha());
                bd.inserir(usuario);

                Log.i("mensagem", "usuário salvo");
                finish();

            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "deu merda", Toast.LENGTH_SHORT).show();
        }
    }
}
