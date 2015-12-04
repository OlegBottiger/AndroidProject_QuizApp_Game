package com.example.iths.asobi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class CustomCategoryActivity extends AppCompatActivity {

    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_category);


        TextView question = (TextView) findViewById(R.id.debug_question);
        TextView ans1 = (TextView) findViewById(R.id.debug_ans1);
        TextView ans2 = (TextView) findViewById(R.id.debug_ans2);
        TextView ans3 = (TextView) findViewById(R.id.debug_ans3);
        TextView ans4 = (TextView) findViewById(R.id.debug_ans4);
        TextView correct = (TextView) findViewById(R.id.debug_correct_answer);


 /*       question.setText();
        ans1.setText();
        ans2.setText();
        ans3.setText();
        ans4.setText();
        correct.setText();
*/


    }

    public void goToAddQuestions(View view) {
        Intent i = new Intent(this, CustomQuestionAddActivity.class);
        startActivity(i);

    }

    public void goToQuestions(View view) {
        Intent i = new Intent(this, CustomQuestionActivity.class);
        startActivity(i);
    }
}
