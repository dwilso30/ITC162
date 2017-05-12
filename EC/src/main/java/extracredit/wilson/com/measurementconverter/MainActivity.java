package extracredit.wilson.com.measurementconverter;

import android.support.v7.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.content.SharedPreferences.Editor;

public class MainActivity extends AppCompatActivity
        implements OnEditorActionListener, OnItemSelectedListener {

    // variables
    private Spinner conversionSpinner;
    private EditText measurementEditText;
    private TextView startTextView;
    private TextView endTextView;
    private TextView resultTextView;

    private String measurementString = "";
    private float result = 0f;
    private int conversionType = 0;


    private SharedPreferences saved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get ref from widgets
        conversionSpinner = (Spinner) findViewById(R.id.conversionSpinner);
        measurementEditText = (EditText) findViewById(R.id.measurementEditText);
        startTextView = (TextView) findViewById(R.id.start);
        endTextView = (TextView) findViewById(R.id.end);
        resultTextView = (TextView) findViewById(R.id.result);


        // array adapter for spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.measurements, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conversionSpinner.setAdapter(adapter);

        //set listeners
        measurementEditText.setOnEditorActionListener(this);
        conversionSpinner.setOnItemSelectedListener(this);

        //get SharedPreferences
        saved = getSharedPreferences("Saved Values", MODE_PRIVATE);
        SharedPreferences sharedPref = getSharedPreferences("FileName",MODE_PRIVATE);
        int spinnerValue = sharedPref.getInt("userChoiceSpinner",-1);
        if(spinnerValue != -1) {
            // set the  spinner
            conversionSpinner.setSelection(spinnerValue);
        }
    }

    @Override
    public void onPause() {
        Editor editor = saved.edit();
        editor.putString("measurementString", measurementString);
        editor.putFloat("result", result);
        editor.apply();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        measurementString = saved.getString("measurementString", "");
        measurementEditText.setText(measurementString);
        result = saved.getFloat("result", result);
        resultTextView.setText(String.format("%.3f", result));

        setTexts();
        convert();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            convert();
        }
        return false;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        conversionType = position + 1;
        int userChoice = conversionSpinner.getSelectedItemPosition();
        SharedPreferences sharedPref = getSharedPreferences("FileName", 0);
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putInt("userChoiceSpinner",userChoice);
        prefEditor.apply();

        convert();
        setTexts();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //
    }

    private void setTexts()
    {
        if(conversionType == 1)
        {
            //M to Km
            startTextView.setText(R.string.miles);
            endTextView.setText(R.string.kilometers);
        }
        else
        {
            if(conversionType == 2)
            {
                //Km to M
                startTextView.setText(R.string.kilometers);
                endTextView.setText(R.string.miles);
            }

            else
            {
                if(conversionType == 3)
                {
                    //In to Cm
                    startTextView.setText(R.string.inches);
                    endTextView.setText(R.string.centimeters);
                }

                else
                {
                    if(conversionType == 4)
                    {
                        //Cm to In
                        startTextView.setText(R.string.centimeters);
                        endTextView.setText(R.string.inches);
                    }
                }
            }
        }
    }

    private void convert() {
        measurementString = measurementEditText.getText().toString().trim();
        float measurement;
        if(measurementString.equals(" "))
        {
            measurement = 0;
        }
        else
        {
            measurement = Float.parseFloat(measurementString);
        }

        if(conversionType == 1)
        {
            //Miles to Kilometers
            measurement = measurement * 1.6093f;
            result = measurement;
            resultTextView.setText(String.format("%.3f", measurement));
        }
        else
        {
            if(conversionType == 2)
            {
                //Km to M
                measurement = measurement * 0.6214f;
                result = measurement;
                resultTextView.setText(String.format("%.3f", measurement));
            }

            else
            {
                if(conversionType == 3)
                {
                    //In to Cm
                    measurement = measurement * 2.54f;
                    result = measurement;
                    resultTextView.setText(String.format("%.3f", measurement));
                }

                else
                {
                    if(conversionType == 4)
                    {
                        //Cm to In
                        measurement = measurement * 0.3937f;
                        result = measurement;
                        resultTextView.setText(String.format("%.3f", measurement));
                    }
                }
            }
        }
    }
}
