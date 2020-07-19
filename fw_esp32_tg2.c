/*****************************************************************************
************************************MAIN**************************************
*****************************************************************************/

#include "BluetoothSerial.h"    // Inclusão de biblioteca

/*****************************************************************************
******************************VERIFICAÇÃO DE ERRO BT**************************
*****************************************************************************/
#if !defined(CONFIG_BT_ENABLED) || !defined(CONFIG_BLUEDROID_ENABLED)
#error Bluetooth is not enabled! 
Please run `make menuconfig` to and enable it
#endif

BluetoothSerial SerialBT;       // Cria uma instancia de BluetoothSerial chamada SerialBT

/*****************************************************************************
***************************DECLARAÇÃO DE VARIÁVEIS****************************
*****************************************************************************/

const int ACC = 2;
const int portas = 4;
char mensagem_char;
String mensagem = "";

/*****************************************************************************
****************************CONFIGURA HARDWARE********************************
*****************************************************************************/

void setup() 
{
  pinMode (ACC, OUTPUT);        // Define GPIO2 como saída digital
  pinMode (portas, OUTPUT);     // Define GPIO4 como saída digital
  Serial.begin(115200);         // Define a taxa de transmissão da porta serialBT para 115200 bps
  SerialBT.begin("VeiculoX");
}

/*****************************************************************************
******************************ROTINA PRINCIPAL********************************
*****************************************************************************/

void loop() 
{
  if (SerialBT.available())                             // Checa se há mensagem no buffer da porta serial
  {
    char mensagem_char = SerialBT.read();               // Copia a informação para uma variável em char
    if (mensagem_char != '\n')
    {
      mensagem += String (mensagem_char);               // Copia a informação para uma variável String      
    }
    else
    {
      mensagem = "";
    }
    Serial.write(mensagem_char);
  }
    if (mensagem == "Veículo Ligado")                   // Compara o dado da mensagem recebida
    {
      digitalWrite (ACC, HIGH);                         // Seta a saída digital
      SerialBT.println("O Motor foi ligado");           // Retorno de mensagem para porta serial
      Serial.print("\nStatus: O Motor foi ligado");     
    }

    if (mensagem == "Veículo Desligado")                // Compara o dado da mensagem recebida
    {
      digitalWrite (ACC, LOW);                          // Reseta a saída digital
      SerialBT.println("O Motor foi desligado");        // Retorno de mensagem para porta serial
      Serial.print("\nStatus: O Motor foi desligado");
    }

    if (mensagem == "Abrir portas")                     // Compara o dado da mensagem recebida
    {
      digitalWrite (portas, HIGH);                      // Seta a saída digital
      SerialBT.println("As portas foram abertas");      // Retorno de mensagem para porta serial
      Serial.print("\nStatus: As portas foram abertas");
    }

    if (mensagem == "Fechar portas")                    // Compara o dado da mensagem recebida
    {
      digitalWrite (portas, LOW);                       // Reseta a saída digital
      SerialBT.println("As portas foram fechadas");     // Retorno de mensagem para porta serial
      Serial.print("\nStatus: As portas foram fechadas");
    }
  delay(20);
}