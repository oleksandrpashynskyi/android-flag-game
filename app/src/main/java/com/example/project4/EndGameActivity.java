package com.example.project4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EndGameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        // Get the data passed from MainActivity
        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        int correctAnswers = intent.getIntExtra("correctAnswers", 0);
        int wrongAnswers = intent.getIntExtra("wrongAnswers", 0);

        // Update the UI with the results
        TextView scoreText = findViewById(R.id.scoreText);
        TextView triesText = findViewById(R.id.triesText);
        scoreText.setText("Your Score: " + score);
        triesText.setText("Tries: " + (correctAnswers + wrongAnswers));

        // Restart the game when the button is clicked
        Button restartButton = findViewById(R.id.restartButton);
        restartButton.setOnClickListener(v -> {
            Intent restartIntent = new Intent(EndGameActivity.this, MainActivity.class);
            restartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(restartIntent);
            finish();
        });
    }
}
