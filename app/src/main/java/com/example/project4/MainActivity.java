package com.example.project4;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private AssetManager assetManager;
    private List<String> flags = new ArrayList<>();
    private int level = 1; // Start at Level 1
    private int numberRight = 0; // Track total correct answers
    private int numberWrong = 0; // Track total wrong answers
    private int score = 0; // Track user score
    private String correctFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assetManager = getAssets();
        flags = loadFlags();

        startGame();
    }

    private List<String> loadFlags() {
        // List of all continents (subfolders)
        String[] continents = {"Africa", "Asia", "Europe", "North_America", "Oceania", "South_America"};
        List<String> flags = new ArrayList<>();

        // Loop through each continent folder and load the flag filenames
        for (String continent : continents) {
            try {
                String[] files = assetManager.list(continent);
                if (files != null) {
                    for (String file : files) {
                        flags.add(continent + "/" + file); // Add the full path (continent + file name)
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return flags;
    }

    private String formatCountryName(String filePath) {
        // Extract the file name (e.g., "south_africa.png")
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);

        // Remove the file extension (e.g., "south_africa")
        String countryName = fileName.replace(".png", "");

        // Replace underscores with spaces and capitalize the first letter of each word
        countryName = countryName.replace("_", " ");
        String[] words = countryName.split(" ");
        StringBuilder formattedName = new StringBuilder();
        for (String word : words) {
            formattedName.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1))
                    .append(" ");
        }

        // Return the formatted country name, trimmed of trailing spaces
        return formattedName.toString().trim();
    }


    private void startGame() {
        if (level > 4) { // End the game after Level 4
            Intent intent = new Intent(MainActivity.this, EndGameActivity.class);
            intent.putExtra("score", score); // Pass the score
            intent.putExtra("correctAnswers", numberRight); // Pass correct answers
            intent.putExtra("wrongAnswers", numberWrong); // Pass wrong answers
            startActivity(intent);
            finish(); // Close MainActivity
            return;
        }

        // Determine the number of flags to show based on the level
        int numFlagsToShow;
        switch (level) {
            case 1:
                numFlagsToShow = 3; // Level 1: 3 flags
                break;
            case 2:
                numFlagsToShow = 6; // Level 2: 6 flags
                break;
            case 3:
                numFlagsToShow = 9; // Level 3: 9 flags
                break;
            case 4:
                numFlagsToShow = 12; // Level 4: 12 flags
                break;
            default:
                numFlagsToShow = 3;
        }

        // Randomly select flags to display
        Collections.shuffle(flags);
        List<String> selectedFlags = flags.subList(0, numFlagsToShow);
        correctFlag = selectedFlags.get(new Random().nextInt(selectedFlags.size()));

        // Format the country name for display
        String countryName = formatCountryName(correctFlag);

        // Get references to ImageViews (all 12 ImageViews)
        ImageView[] flagImageViews = new ImageView[] {
                findViewById(R.id.flag1),
                findViewById(R.id.flag2),
                findViewById(R.id.flag3),
                findViewById(R.id.flag4),
                findViewById(R.id.flag5),
                findViewById(R.id.flag6),
                findViewById(R.id.flag7),
                findViewById(R.id.flag8),
                findViewById(R.id.flag9),
                findViewById(R.id.flag10),
                findViewById(R.id.flag11),
                findViewById(R.id.flag12)
        };

        // Assign images to ImageViews
        for (int i = 0; i < flagImageViews.length; i++) {
            if (i < selectedFlags.size()) {
                try {
                    InputStream inputStream = assetManager.open(selectedFlags.get(i));
                    Drawable drawable = Drawable.createFromStream(inputStream, null);
                    flagImageViews[i].setImageDrawable(drawable);
                    flagImageViews[i].setVisibility(ImageView.VISIBLE);

                    final String selectedFlag = selectedFlags.get(i);
                    flagImageViews[i].setOnClickListener(v -> checkAnswer(selectedFlag));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                flagImageViews[i].setVisibility(ImageView.GONE); // Hide unused ImageViews
            }
        }

        // Update the question text with the formatted country name
        TextView questionText = findViewById(R.id.questionText);
        questionText.setText("The Flag Of: " + countryName +" " + "is?");

        // Update the scores
        updateScores();
    }

    private void checkAnswer(String selectedFlag) {
        if (selectedFlag.equals(correctFlag)) {
            numberRight++; // Increment total correct answers

            // Add points based on the level
            switch (level) {
                case 1:
                    score += 1; // Level 1: +1 point
                    break;
                case 2:
                    score += 2; // Level 2: +2 points
                    break;
                case 3:
                    score += 4; // Level 3: +4 points
                    break;
                case 4:
                    score += 8; // Level 4: +8 points
                    break;
            }

            Toast.makeText(this, "Correct! Moving to the next level.", Toast.LENGTH_SHORT).show();
            level++; // Move to the next level
        } else {
            numberWrong++; // Increment total wrong answers
            score = Math.max(score - 1, 0); // Deduct 1 point, but ensure score doesn't go below 0
            Toast.makeText(this, "Wrong! Try again. -1 point.", Toast.LENGTH_SHORT).show();
            // Stay on the same level
        }

        updateScores();
        startGame(); // Restart the game for the next level or retry the current level
    }

    private void updateScores() {
        TextView scoreText = findViewById(R.id.scoreText);
        scoreText.setText("Number Right: " + numberRight + "\nNumber Wrong: " + numberWrong + "\nLevel: " + level + "\nScore: " + score);
    }

    private void resetGame() {
        level = 1;
        numberRight = 0;
        numberWrong = 0;
        score = 0; // Reset score
        startGame();
    }
}
