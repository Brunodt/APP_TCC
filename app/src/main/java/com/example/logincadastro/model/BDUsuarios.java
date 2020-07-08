package com.example.logincadastro.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BDUsuarios {
    private SQLiteDatabase bd;

    public BDUsuarios(Context context){
        BDHelper auxBd = new BDHelper(context);
        bd = auxBd.getWritableDatabase();
    }

    public Usuario checarUsuarioPorUsername(String username){

        Usuario u = new Usuario();
       //String [] colunas = new String[]{"_id","username","senha"};
        Cursor cursor = bd.query("usuario",new String[]{"_id","username","senha"},"username"+"=?",new String [] {username},null,null,null,null);

        if(cursor!=null){
            cursor.moveToFirst();
            if(cursor.getCount() > 0){
                u.setId(cursor.getLong(0));
                u.setUsername(cursor.getString(1));
                u.setSenha(cursor.getString(2));
            }else{
                return null;
            }
        }cursor.close();
        bd.close();

        return u;
    }

    public Usuario validarLogin(String username, String senha) {

        String[] selectionArgs = new String[]{username, senha};
        Cursor cursor;
        cursor = bd.rawQuery("select * from usuario where username=? and senha=?", selectionArgs);
        Usuario usuarioLinha = new Usuario();
        if (cursor.moveToNext()) {
            usuarioLinha.setId(cursor.getLong(cursor.getColumnIndex("_id")));
            usuarioLinha.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            usuarioLinha.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
        }
        return usuarioLinha;
    }
    /*public int compararCadastroPorUsernameSenha(String username, String senha){
        //Usuario u = new Usuario();
        int i = 0;
        Cursor cursor = bd.rawQuery("SELECT count(*) FROM usuario WHERE username=? AND senha=?", new String[]{username,senha});

        //("SELECT count(*) FROM usuario WHERE username=? AND senha=?", new String[]{username,senha});

        if(cursor!=null){
            cursor.moveToFirst();
            if(cursor.getCount() > 0){
                i = 1;
            /*if(cursor.getCount() > 0){
                u.setId(cursor.getLong(0));
                u.setUsername(cursor.getString(1));
                u.setSenha(cursor.getString(2));
            }else{
                 i = 0;
            }
        }cursor.close();
        bd.close();
        return i;
    }*/

    public void inserir(Usuario usuario) throws Exception{
        ContentValues valores = new ContentValues();
        valores.put("username", usuario.getUsername());
        valores.put("senha", usuario.getSenha());

        bd.insert("usuario", null, valores);
    }

    // implementado mas não utilizado
    public void atualizar(Usuario usuario){
        ContentValues valores = new ContentValues();
        valores.put("username", usuario.getUsername());

        bd.update("usuario", valores, "_id = ?", new String[]{""+usuario.getId()});
    }

    public void deletar (Usuario usuario){
        bd.delete("usuario","_id = "+usuario.getId(),null);
    }

    /*public Usuario validarLogin(String username, String senha){
        String[] colunas = new String[]{"_id",username,senha};
        Cursor cursor = bd.rawQuery("SELECT * FROM Usuarios WHERE username=? AND senha=?", colunas);
        Usuario usuarioLinha = null;

        while(cursor.moveToNext()){
            usuarioLinha = new Usuario();
            usuarioLinha.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            usuarioLinha.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
        }cursor.close();
        return usuarioLinha;
    }*/
    /*
    public List<Usuario> buscar(){
        List<Usuario> lista = new ArrayList<Usuario>();
        String[] colunas = new String[]{"_id","username","senha"};

        Cursor cursor = bd.query("usuario",colunas,null,null,null,null,null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{
                Usuario u = new Usuario();
                u.setId(cursor.getLong(0));
                u.setUsername(cursor.getString(1));
                u.setSenha(cursor.getString(2));
                lista.add(u);
            }while(cursor.moveToNext());
        }
        return(lista);
    }*/
}
