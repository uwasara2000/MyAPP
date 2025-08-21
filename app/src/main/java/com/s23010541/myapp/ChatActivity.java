package com.s23010541.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private ImageButton backButton;

    private final OkHttpClient client = new OkHttpClient();
    private static final String API_KEY = "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"; // ⚠️ Replace with fresh key
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        userInput = findViewById(R.id.userInput);
        sendButton = findViewById(R.id.sendButton);
        chatOutput = findViewById(R.id.chatOutput);
        backButton = findViewById(R.id.backButton);

        // Back button → go to Dashboard
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ChatActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        });

        // Send button
        sendButton.setOnClickListener(v -> {
            String message = userInput.getText().toString().trim();
            if (!message.isEmpty()) {
                chatOutput.append("\n\nYou: " + message);
                sendMessageToChatGPT(message);
                userInput.setText("");
            }
        });
    }

    private void sendMessageToChatGPT(String message) {
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
                    runOnUiThread(() ->
                            chatOutput.append("\n\n❌ Network error: " + e.getMessage()));
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        String errMsg = "HTTP " + response.code() + ": " + response.message();
                        runOnUiThread(() ->
                                chatOutput.append("\n\n❌ Error: " + errMsg));
                        return;
                    }

                    try {
                        String resStr = response.body().string();
                        JSONObject json = new JSONObject(resStr);
                        JSONArray choices = json.getJSONArray("choices");

                        if (choices.length() > 0) {
                            JSONObject messageObj = choices
                                    .getJSONObject(0)
                                    .getJSONObject("message");

                            String reply = messageObj.getString("content");

                            runOnUiThread(() ->
                                    chatOutput.append("\n\nChatGPT: " + reply.trim()));
                        } else {
                            runOnUiThread(() ->
                                    chatOutput.append("\n\n⚠️ No reply from ChatGPT"));
                        }
                    } catch (Exception e) {
                        Log.e("ChatActivity", "Parsing error", e);
                        runOnUiThread(() ->
                                chatOutput.append("\n\n⚠️ Parsing error: " + e.getMessage()));
                    }
                }
            });

        } catch (Exception e) {
            chatOutput.append("\n\n⚠️ Error: " + e.getMessage());
        }
    }
}
