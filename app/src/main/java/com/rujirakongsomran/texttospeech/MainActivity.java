package com.rujirakongsomran.texttospeech;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText etMessage;
    Button btnSpeak;

    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstances();
    }

    private void initInstances() {
        etMessage = (EditText) findViewById(R.id.etMessage);
        btnSpeak = (Button) findViewById(R.id.btnSpeak);

        // Init text to speech
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.ENGLISH);

                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(MainActivity.this, "This language is not supported", Toast.LENGTH_SHORT).show();
                    } else {
                        btnSpeak.setEnabled(true);

                    }
                    textToSpeech.setPitch(0.6f);
                    textToSpeech.setSpeechRate(1.0f);
                    speak();
                }
            }
        });


        btnSpeak.setOnClickListener(btnSpeakListener);

    }

    private void speak() {
        String message = etMessage.getText().toString();
        if (Build.VERSION.SDK_INT >= 22) {
            textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    protected void onDestroy() {
        // Don't forget to shutdown Text To Speech when you close app
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    // Listener
    View.OnClickListener btnSpeakListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            speak();
        }
    };

}
