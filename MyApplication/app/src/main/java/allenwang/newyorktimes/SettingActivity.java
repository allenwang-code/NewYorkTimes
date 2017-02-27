package allenwang.newyorktimes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;

import java.util.Date;

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
        ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(SettingActivity.this,
                R.array.order,
                android.R.layout.simple_spinner_item);
        spinner.setAdapter(list);
        artsCheckBox = (CheckBox) findViewById(R.id.checkBox_art);
        fashionCheckBox = (CheckBox) findViewById(R.id.checkBox_fashion);
        sportsCheckBox = (CheckBox) findViewById(R.id.checkBox_sport);
        btnSave = (Button) findViewById(R.id.btn_save);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();

                Bundle b = new Bundle();
                String d = handleDate();
                String s = handleSort();
                String f = handleFq();
                b.putString(Constant.BEGIN_DATE, d);
                b.putString(Constant.SORT, s);
                b.putString(Constant.FQ, f);

                i.putExtras(b);
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }

    private String handleDate() {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth()+1;
        int year =  datePicker.getYear();

        Date d = new Date(year, month, day);

        return android.text.format.DateFormat.format("yyyyMMdd", d).toString();
    }

    private String handleFq() {
        //news_desk:("Education"%20"Health")
        String result = "news_desk:(";
        if (artsCheckBox.isChecked()) { result += "\"Arts\""; }
        if (sportsCheckBox.isChecked()) { result += "\"Sports\"";    }
        if (fashionCheckBox.isChecked()) { result += "\"fashion\"";    }
        result += ")";
        return result;
    }

    private String handleSort() {
        return spinner.getSelectedItem().toString();
    }


}
