package com.ron.nbshopbot;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "NBShopBot";
    private EditText etSimulatorID;
    private EditText etChatSessionID;
    private EditText etMessage;

    private Button btnCreateSimulator;
    private Button btnCreateChatSession;
    private Button btnSendMessage;
    private Button btnUpdateSimulator;

    private ShopBotChatSession chatSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initListener();
        chatSession = new ShopBotChatSession();
    }

    private void initUI() {
        etSimulatorID = (EditText) findViewById(R.id.etSimulatorID);
        etChatSessionID = (EditText) findViewById(R.id.etChatSessionID);
        etMessage = (EditText) findViewById(R.id.etMessage);

        btnCreateSimulator = (Button) findViewById(R.id.btnCreateSimulator);
        btnCreateChatSession = (Button) findViewById(R.id.btnCreateChat);
        btnSendMessage = (Button) findViewById(R.id.btnSendMessage);
        btnUpdateSimulator = (Button) findViewById(R.id.btnUpdateSimulator);
    }

    private void initListener() {
        btnCreateSimulator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CreateSimulatorTask().execute();
            }
        });

        btnCreateChatSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CreateChatSessionTask().execute();
            }
        });

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendMessageTask().execute(etMessage.getText().toString());
            }
        });

        btnUpdateSimulator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UpdateSimulatorTask().execute();
            }
        });

    }



    private class CreateSimulatorTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... args) {
            return chatSession.createUserSimulator();
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String message) {
            Log.d(TAG, message);
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            etSimulatorID.setText(chatSession.getUserSimulatorID());
        }
    }


    private class CreateChatSessionTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... args) {
            return chatSession.createChatSession();
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String chat_id) {
            Log.d(TAG, chat_id);
            etChatSessionID.setText(chatSession.getChatSessionID());
        }
    }

    private class SendMessageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... msgs) {
            try {
                return chatSession.sendMessage(msgs[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "No message";
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String message) {
            Log.d(TAG, message);
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        }
    }

    private class UpdateSimulatorTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... args) {
            try {
                return chatSession.updateSimulatorState();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "No message";
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String message) {
            Log.d(TAG, message);
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        }
    }




}
