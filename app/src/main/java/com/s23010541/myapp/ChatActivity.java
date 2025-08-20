package com.s23010541.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatActivity extends AppCompatActivity {

    private EditText userInput;
    private Button sendButton;
    private TextView chatOutput;
    private ImageButton backButton; // ✅ Add reference

    private final OkHttpClient client = new OkHttpClient();
    private static final String API_KEY = "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"; // ⚠️ Replace with your real key
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        userInput = findViewById(R.id.userInput);
        sendButton = findViewById(R.id.sendButton);
        chatOutput = findViewById(R.id.chatOutput);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ChatActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish(); // closes ChatActivity so the user doesn’t come back here on system back
        });


        sendButton.setOnClickListener(v -> {
            String message = userInput.getText().toString().trim();
            if (!message.isEmpty()) {
                chatOutput.append("\nYou: " + message);
                sendMessageToChatGPT(message);
                userInput.setText("");
            }
        });
    }

    private void sendMessageToChatGPT(String message) {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("model", "gpt-3.5-turbo");

            JSONArray messages = new JSONArray();
            JSONObject userMsg = new JSONObject();
            userMsg.put("role", "user");
            userMsg.put("content", message);
            messages.put(userMsg);

            jsonBody.put("messages", messages);

            RequestBody body = RequestBody.create(jsonBody.toString(), JSON);

            Request request = new Request.Builder()
                    .url(API_URL)
                    .header("Authorization", "Bearer " + API_KEY)
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() -> chatOutput.append("\nError: " + e.getMessage()));
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        runOnUiThread(() -> chatOutput.append("\nError: " + response.message()));
                    } else {
                        try {
                            String resStr = response.body().string();
                            JSONObject json = new JSONObject(resStr);
                            String reply = json
                                    .getJSONArray("choices")
                                    .getJSONObject(0)
                                    .getJSONObject("message")
                                    .getString("content");

                            runOnUiThread(() -> chatOutput.append("\nChatGPT: " + reply.trim()));
                        } catch (Exception e) {
                            runOnUiThread(() -> chatOutput.append("\nParsing error: " + e.getMessage()));
                        }
                    }
                }
            });

        } catch (Exception e) {
            chatOutput.append("\nError: " + e.getMessage());
        }
    }
}
