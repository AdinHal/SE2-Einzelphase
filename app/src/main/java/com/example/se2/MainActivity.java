package com.example.se2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public String temp;
    public String sentence;
    public String msg;
    public String response;
    public TextView srvr;
    //public EditText MatrikelNummer;
    public ArrayList<Object>userinput=new ArrayList<>();
    public Reader inputStream;
    public BufferedReader inFromUser,inFromServer;
    public Socket socket;
    public DataOutputStream outToServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        srvr=findViewById(R.id.serverText);

    }

    public void convert(View view){
        EditText et=findViewById(R.id.EditText);
        TextView output=findViewById(R.id.asciitext);
        //int[]tes={1,1,8,2,8,9,1,7};
        String str = et.getText().toString();
        int length = str.length();

        int[] tes = new int[length];

        for(int i=0;i<length;i++) {
            tes[i] = Character.getNumericValue(str.charAt(i));
        }
        ArrayList<Integer>matnr= new ArrayList<>(tes.length);
        ArrayList<Object>objektici=new ArrayList<>();
        for(int i:tes){
            matnr.add(i);
        }
        for (int i = 0; i < matnr.size(); i++) {
            switch (i){
                case 0:
                    objektici.add("a ");
                    break;
                case 1:
                    objektici.add(matnr.get(1)+" ");
                    break;
                case 2:
                    objektici.add("c ");
                    break;
                case 3:
                    objektici.add(matnr.get(3)+" ");
                    break;
                case 4:
                    objektici.add("e ");
                    break;
                case 5:
                    objektici.add(matnr.get(5)+" ");
                    break;
                case 6:
                    objektici.add("g ");
                    break;
                case 7:
                    objektici.add(matnr.get(7)+" ");
                    break;
                case 8:
                    objektici.add("i ");
                    break;
                default:
                    System.out.println("Please enter a valid MatNr");
                    break;
            }
        }

            output.setText("Deine ASCII MatNr: "+objektici.toString());

    }
    public void tcptest(View view) {
        EditText MatrikelNummer=(EditText)findViewById(R.id.EditText);
        msg=MatrikelNummer.getText().toString();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    inputStream = new StringReader(msg);
                    inFromUser=new BufferedReader(inputStream);
                    socket = new Socket("se2-isys.aau.at", 53212);
                    outToServer=new DataOutputStream(socket.getOutputStream());
                    inFromServer=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    sentence=inFromUser.readLine();
                    outToServer.writeBytes(sentence+"\n");
                    response=inFromServer.readLine();
                    temp=response;
                    socket.close();
                    inputStream.close();
                    outToServer.close();
                    inFromServer.close();
                    inFromUser.close();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }

        });
        thread.start();
        try {
            thread.join();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        srvr.setText(temp);
    }
}