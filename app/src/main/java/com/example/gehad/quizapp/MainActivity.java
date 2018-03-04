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
    String name;
    String questionFiveText;            //Q5Text
    int finalScore;                     //default value of global integer is zero, so we don't need to initialize it.
    String message;
    Button submitButton;
    private EditText nameField;
    private RadioGroup questionOne;     //Q1
    private RadioButton questionOneA;   //Q1a
    private RadioGroup questionTwo;     //Q2
    private RadioButton questionTwoB;   //Q2b
    private RadioGroup questionThree;   //Q3
    private RadioButton questionThreeA; //Q3a
    private RadioGroup questionFour;    //Q4
    private RadioButton questionFourA;  //Q4a
    private EditText questionFiveField; //Q5Field
    private CheckBox questionSixA;      //Q6a
    private CheckBox questionSixB;      //Q6b
    private CheckBox questionSixC;      // Q6c
    private CheckBox questionSixD;      // Q6d
    private TextView finalScoreView;
    //default value of global boolean is false, so we don't need to initialize it.
    private boolean questionFiveResult; //Q5Result
    private boolean questionSixResult;  // Q6Result
    private int noOfQuestions;          //NoOfQuestions
    private String resultMessage;       //ResMessage

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameField = findViewById(R.id.Name);

        questionOne = findViewById(R.id.Q1);
        questionOneA = findViewById(R.id.Q1a);

        questionTwo = findViewById(R.id.Q2);
        questionTwoB = findViewById(R.id.Q2b);

        questionThree = findViewById(R.id.Q3);
        questionThreeA = findViewById(R.id.Q3a);

        questionFour = findViewById(R.id.Q4);
        questionFourA = findViewById(R.id.Q4a);

        questionFiveField = findViewById(R.id.Q5);

        questionSixA = findViewById(R.id.Q6a);
        questionSixB = findViewById(R.id.Q6b);
        questionSixC = findViewById(R.id.Q6c);
        questionSixD = findViewById(R.id.Q6d);

        finalScoreView = findViewById(R.id.Score);

        noOfQuestions = getResources().getInteger(R.integer.NoOfQuestions);
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

        if (questionOneA.isChecked()) finalScore++;

        if (questionTwoB.isChecked()) finalScore++;

        if (questionThreeA.isChecked()) finalScore++;

        if (questionFourA.isChecked()) finalScore++;

        questionFiveText = questionFiveField.getText().toString().trim();

        if (questionFiveText.equals("ImageView") || questionFiveText.equals("Image View")) {
            finalScore++;
            questionFiveResult = TRUE;
        }

        if ((questionSixA.isChecked() && questionSixC.isChecked()) &&
                !(questionSixB.isChecked() || questionSixD.isChecked())) {
            finalScore++;
            questionSixResult = TRUE;
        }

        if (finalScore >= noOfQuestions / 2.0) {
            resultMessage = getResources().getString(R.string.messageScore) + " " + finalScore + "\n" +
                    getResources().getString(R.string.Success);
            finalScoreView.setTextColor(this.getResources().getColor(R.color.GreenSuccess));
        } else {
            resultMessage = getResources().getString(R.string.messageScore) + " " + finalScore + "\n" +
                    getResources().getString(R.string.Failed);
            finalScoreView.setTextColor(this.getResources().getColor(R.color.RedFailure));
        }

        displayScore();

        Toast.makeText(MainActivity.this, resultMessage, Toast.LENGTH_LONG).show();

        scoreMessage();

        submitButton.setEnabled(false);
        submitButton.setTextColor(getResources().getColor(R.color.DisabledGray));
    }

    public void ResetResults(View view) {

        nameField.getText().clear();
        name = "";
        finalScore = 0;
        displayScore();

        questionOne.clearCheck();
        questionOneA.setChecked(false);

        questionTwo.clearCheck();
        questionTwoB.setChecked(false);

        questionThree.clearCheck();
        questionThreeA.setChecked(false);

        questionFour.clearCheck();
        questionFourA.setChecked(false);

        questionFiveField.getText().clear();
        questionFiveText = "";
        questionFiveResult = FALSE;

        questionSixA.setChecked(false);
        questionSixB.setChecked(false);
        questionSixC.setChecked(false);
        questionSixD.setChecked(false);
        questionSixResult = FALSE;

        message = "";

        resultMessage = "";

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

    //Email Message
    public void scoreMessage() {
        message = getResources().getString(R.string.messageName) + " " + name;
        message += "\n" + getResources().getString(R.string.messageQ1) + " " + questionOneA.isChecked();
        message += "\n" + getResources().getString(R.string.messageQ2) + " " + questionTwoB.isChecked();
        message += "\n" + getResources().getString(R.string.messageQ3) + " " + questionThreeA.isChecked();
        message += "\n" + getResources().getString(R.string.messageQ4) + " " + questionFourA.isChecked();
        message += "\n" + getResources().getString(R.string.messageQ5) + " " + questionFiveResult;
        message += "\n" + getResources().getString(R.string.messageQ6) + " " + questionSixResult;
        message += "\n" + "\n" + resultMessage + "\n";
        message += "\n" + getResources().getString(R.string.messageBest);
    }
}
