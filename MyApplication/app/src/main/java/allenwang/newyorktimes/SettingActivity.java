package allenwang.newyorktimes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;

public class SettingActivity extends AppCompatActivity {

    DatePicker datePicker;
    Spinner spinner;
    CheckBox artsCheckBox;
    CheckBox fashionCheckBox;
    CheckBox sportsCheckBox;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        spinner = (Spinner) findViewById(R.id.spinner);
        artsCheckBox = (CheckBox) findViewById(R.id.checkBox_art);
        fashionCheckBox = (CheckBox) findViewById(R.id.checkBox_fashion);
        sportsCheckBox = (CheckBox) findViewById(R.id.checkBox_sport);
        btnSave = (Button) findViewById(R.id.btn_save);


    }
}
