package com.example.calculator_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button AC;
    Button cross;
    Button minus;
    Button plus;
    Button equal;
    Button clear;
    Button zero;
    Button one;
    Button two;
    Button three;
    Button four;
    Button five;
    Button six;
    Button seven;
    Button eight;
    Button nine;

    TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_2);

        AC = (Button) findViewById(R.id.BT_AC);
        cross = (Button) findViewById(R.id.BT_cross);
        minus = (Button) findViewById(R.id.BT_minus);
        plus = (Button) findViewById(R.id.BT_plus);
        equal = (Button) findViewById(R.id.BT_equal);
        clear = (Button) findViewById(R.id.BT_clear);
        zero = (Button) findViewById(R.id.BT_zero);
        one = (Button) findViewById(R.id.BT_one);
        two = (Button) findViewById(R.id.BT_two);
        three = (Button) findViewById(R.id.BT_three);
        four = (Button) findViewById(R.id.BT_four);
        five = (Button) findViewById(R.id.BT_five);
        six = (Button) findViewById(R.id.BT_six);
        seven = (Button) findViewById(R.id.BT_seven);
        eight = (Button) findViewById(R.id.BT_eight);
        nine = (Button) findViewById(R.id.BT_nine);

        display = (TextView) findViewById(R.id.TV_display);

        AC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText("");
            }
        });

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText().toString() + "×");
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText().toString() + "-");
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText().toString() + "+");
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText().toString().substring(0, display.getText().toString().length() - 1));
            }
        });

        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText().toString() + "0");
            }
        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText().toString() + "1");
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText().toString() + "2");
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText().toString() + "3");
            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText().toString() + "4");
            }
        });

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText().toString() + "5");
            }
        });

        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText().toString() + "6");
            }
        });

        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText().toString() + "7");
            }
        });

        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText().toString() + "8");
            }
        });

        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText().toString() + "9");
            }
        });

        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    display.setText(calculate(display.getText().toString()));
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "invalid input", Toast.LENGTH_LONG).show();
                }
            }
        });



    }

    private String calculate(String string) {
        char plus = '+';
        char minus = '-';
        char cross = '×';
        String[] numbers = string.split("[-+×]+");
        ArrayList<Integer> listOfNumbers = new ArrayList<>();
        for (int i = 0; i < numbers.length; i++) {
            listOfNumbers.add(Integer.parseInt(numbers[i]));
        }
        ArrayList<Character> operations = new ArrayList<>();
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == plus) {
                operations.add(plus);
            } else if (string.charAt(i) == minus) {
                operations.add(minus);
            } else if (string.charAt(i) == cross) {
                operations.add(cross);
            }
        }

        while (true) {
            if (operations.contains('×')) {
                int index = operations.indexOf('×');
                int result = listOfNumbers.get(index) * listOfNumbers.get(index + 1);
                listOfNumbers.remove(index + 1);
                listOfNumbers.remove(index);
                listOfNumbers.add(index, result);
                operations.remove(index);
            } else {
                break;
            }
        }

        int finalResult = listOfNumbers.get(0);
        listOfNumbers.remove(0);

        int total = operations.size();
        for (int i = 0; i < total; i++) {
            if (operations.get(0) == plus) {
                finalResult += listOfNumbers.get(0);
            } else {
                finalResult -= listOfNumbers.get(0);
            }
            operations.remove(0);
            listOfNumbers.remove(0);
        }
        return String.valueOf(finalResult);

    }
}