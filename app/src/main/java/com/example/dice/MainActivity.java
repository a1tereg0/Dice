package com.example.dice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        if (!sharedPref.contains("all_dice_types")){
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("all_dice_types", "d6,d10,d12,d18,d24,d30");
            editor.apply();
        }

        String allDiceTypes = sharedPref.getString("all_dice_types", "");
        ArrayList<String> diceTypes = new ArrayList<>(Arrays.asList(allDiceTypes.split(",")));
        TextView textView = (TextView) findViewById(R.id.result_textView);

        Spinner spinner = (Spinner) findViewById(R.id.dropdown_menu);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, diceTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        
        Button button = (Button) findViewById(R.id.roll_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String diceType = spinner.getSelectedItem().toString();
                Die die = new Die(diceType);
                die.roll();
                String result = String.valueOf(die.getSideUp());
                result = (diceType.equals("d10") && result.equals("0")) ? "00" : result;
                textView.setText(result);
            }
        });

        Button rollTwoButton = findViewById(R.id.roll_two_button);

        rollTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String diceType = spinner.getSelectedItem().toString();
                Die die1 = new Die(diceType);
                Die die2 = new Die(diceType);
                die1.roll();
                die2.roll();
                String result1 = String.valueOf(die1.getSideUp());
                String result2 = String.valueOf(die2.getSideUp());
                result1 = (diceType.equals("d10") && result1.equals("0")) ? "00" : result1;
                result2 = (diceType.equals("d10") && result2.equals("0")) ? "00" : result2;
                textView.setText(String.format("%s | %s", result1 , result2 ));
            }
        });

        EditText editTextDiceType = (EditText) findViewById(R.id.dice_sides_edit_text);
        Button addDiceButton = (Button) findViewById(R.id.add_dice_button);
        addDiceButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (editTextDiceType.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Enter number of sides", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(editTextDiceType.getText().toString()) > 120) {
                    Toast.makeText(MainActivity.this, "Largest side number is 120", Toast.LENGTH_SHORT).show();
                } else {
                    String diceType = "d"+editTextDiceType.getText().toString();
                    if (!diceTypes.contains(diceType)) {
                        adapter.add(diceType);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("all_dice_types", allDiceTypes+","+diceType);
                        editor.apply();
                        adapter.notifyDataSetChanged();
                        editTextDiceType.getText().clear();
                    } else {
                        Toast.makeText(MainActivity.this, "Already have that dice", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}