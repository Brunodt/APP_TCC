package com.example.logincadastro.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDHelper extends SQLiteOpenHelper {

    private static final String NOME_BD = "Tabela";
    private static final int VERSAO_BD = 1;

    public BDHelper(Context ctx){
        super(ctx,NOME_BD,null,VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("CREATE TABLE usuario (_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL, senha TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int oldVersion, int newVersion) {
        //
    }
}
