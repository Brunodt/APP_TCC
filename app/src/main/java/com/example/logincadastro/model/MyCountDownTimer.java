package com.example.logincadastro.model;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.logincadastro.SelecaoVeiculo;

import java.util.Calendar;

public class MyCountDownTimer extends CountDownTimer {

    private TextView txvContador;
    private Context context;
    private long timeInFuture;

    public MyCountDownTimer (Context context, TextView txvContador, long timeInFuture, long interval){
        super(timeInFuture, interval);
        this.context = context;
        this.txvContador = txvContador;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        timeInFuture = millisUntilFinished;
        txvContador.setText(getCorrectTimer(true, millisUntilFinished)+":"+getCorrectTimer(false, millisUntilFinished));

    }
    @Override
    public void onFinish() {

        timeInFuture -=1000;
        txvContador.setText(getCorrectTimer(true, timeInFuture)+":"+getCorrectTimer(false, timeInFuture));
        cancel();

        Toast.makeText(context, "Tempo de chagada encerrado",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, SelecaoVeiculo.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);

    }

    private String getCorrectTimer(boolean isMinute, long millisUntilFinished){
        String aux;
        int constCalendar = isMinute ? Calendar.MINUTE : Calendar.SECOND;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millisUntilFinished);

        aux = calendar.get(constCalendar)<10 ? "0"+ calendar.get(constCalendar): ""+calendar.get(constCalendar);
        return(aux);
    }
}
