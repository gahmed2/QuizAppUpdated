package com.example.gehad.quizapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class MainActivity extends AppCompatActivity {

    static final String STATE_Name = "Score";
    static final String STATE_Color = "ScoreColor";

    private EditText nameField;
    String name;

    private RadioGroup Q1;
    private RadioButton Q1a;

    private RadioGroup Q2;
    private RadioButton Q2b;

    private RadioGroup Q3;
    private RadioButton Q3a;

    private RadioGroup Q4;
    private RadioButton Q4a;

    private EditText Q5Field;
    String Q5Text;

    private CheckBox Q6a;
    private CheckBox Q6b;
    private CheckBox Q6c;
    private CheckBox Q6d;

    private TextView finalScoreView;

    int finalScore = 0;

    String message;

    private boolean Q5Result = FALSE;
    private boolean Q6Result = FALSE;

    private int NoOfQuestions;

    private String ResMessage;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameField = findViewById(R.id.Name);

        Q1 = findViewById(R.id.Q1);
        Q1a = findViewById(R.id.Q1a);

        Q2 = findViewById(R.id.Q2);
        Q2b = findViewById(R.id.Q2b);

        Q3 = findViewById(R.id.Q3);
        Q3a = findViewById(R.id.Q3a);

        Q4 = findViewById(R.id.Q4);
        Q4a = findViewById(R.id.Q4a);

        Q5Field = findViewById(R.id.Q5);

        Q6a = findViewById(R.id.Q6a);
        Q6b = findViewById(R.id.Q6b);
        Q6c = findViewById(R.id.Q6c);
        Q6d = findViewById(R.id.Q6d);

        finalScoreView = findViewById(R.id.Score);

        NoOfQuestions = getResources().getInteger(R.integer.NoOfQuestions);
        submitButton = findViewById(R.id.submitResult);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current score state
        savedInstanceState.putInt(STATE_Name, finalScore);
        savedInstanceState.putInt(STATE_Color, finalScoreView.getCurrentTextColor());

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        finalScore = savedInstanceState.getInt(STATE_Name);
        finalScoreView.setTextColor(savedInstanceState.getInt(STATE_Color));

        displayScore();
    }

    public void SubmitResults(View view) {

        name = nameField.getText().toString().trim();

        if (Q1a.isChecked()) finalScore++;

        if (Q2b.isChecked()) finalScore++;

        if (Q3a.isChecked()) finalScore++;

        if (Q4a.isChecked()) finalScore++;

        Q5Text = Q5Field.getText().toString().trim();

        if (Q5Text.equals("ImageView") || Q5Text.equals("Image View")) {
            finalScore++;
            Q5Result = TRUE;
        }

        if ((Q6a.isChecked() && Q6c.isChecked()) && !(Q6b.isChecked() || Q6d.isChecked())) {
            finalScore++;
            Q6Result = TRUE;
        }

        if(finalScore >= NoOfQuestions / 2.0){
            ResMessage = getResources().getString(R.string.messageScore) + " " + finalScore + "\n" +
                    getResources().getString(R.string.Success);
            finalScoreView.setTextColor(this.getResources().getColor(R.color.GreenSuccess));
        }
        else{
            ResMessage = getResources().getString(R.string.messageScore) + " " + finalScore + "\n" +
                    getResources().getString(R.string.Failed);
            finalScoreView.setTextColor(this.getResources().getColor(R.color.RedFailure));
        }

        displayScore();

        Toast.makeText(MainActivity.this,ResMessage,Toast.LENGTH_LONG).show();

        scoreMessage();

        submitButton.setEnabled(false);
        submitButton.setTextColor(getResources().getColor(R.color.DisabledGray));
    }

    public void ResetResults(View view) {

        nameField.getText().clear();
        name = "";
        finalScore = 0;
        displayScore();

        Q1.clearCheck();
        Q1a.setChecked(false);

        Q2.clearCheck();
        Q2b.setChecked(false);

        Q3.clearCheck();
        Q3a.setChecked(false);

        Q4.clearCheck();
        Q4a.setChecked(false);

        Q5Field.getText().clear();
        Q5Text = "";
        Q5Result = FALSE;

        Q6a.setChecked(false);
        Q6b.setChecked(false);
        Q6c.setChecked(false);
        Q6d.setChecked(false);
        Q6Result = FALSE;

        message = "";

        ResMessage = "";

        finalScoreView.setTextColor(this.getResources().getColor(R.color.TextWhite));

        submitButton.setEnabled(true);
        submitButton.setTextColor(getResources().getColor(R.color.TextWhite));
    }

    public void ShareResults(View view) {

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Final Results for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    public void displayScore() {

        finalScoreView.setText(String.valueOf(finalScore));
    }

    public void scoreMessage() {
        message = getResources().getString(R.string.messageName) + " " + name;
        message += "\n" + getResources().getString(R.string.messageQ1) + " " + Q1a.isChecked();
        message += "\n" + getResources().getString(R.string.messageQ2) + " " + Q2b.isChecked();
        message += "\n" + getResources().getString(R.string.messageQ3) + " " + Q3a.isChecked();
        message += "\n" + getResources().getString(R.string.messageQ4) + " " + Q4a.isChecked();
        message += "\n" + getResources().getString(R.string.messageQ5) + " " + Q5Result;
        message += "\n" + getResources().getString(R.string.messageQ6) + " " + Q6Result;
        message += "\n" + "\n" + ResMessage + "\n";
        message += "\n" + getResources().getString(R.string.messageBest);
    }
}
