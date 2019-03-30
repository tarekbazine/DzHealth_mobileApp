package com.example.seadev.dzhealth_mobileapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ReservationActivity extends AppCompatActivity
        implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    TextView txtDate, txtTime, labelT, labelD;
    EditText de, vers;
    Switch aSwitch;
    int mYear, mMonth, mDay, mHour, mMinute;
    Button btn_reserver;
    Spinner listTypeVehicule;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        listTypeVehicule = (Spinner) findViewById(R.id.type_vehicule);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_véhicule, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        listTypeVehicule.setAdapter(adapter);
        listTypeVehicule.setOnItemSelectedListener(this);

        de = (EditText) findViewById(R.id.lieu_de);
        vers = (EditText) findViewById(R.id.lieu_vers);

        aSwitch = (Switch) findViewById(R.id.switch1);

        txtDate = (TextView) findViewById(R.id.textViewDate);
        txtDate.setOnClickListener(this);
        txtTime = (TextView) findViewById(R.id.textViewTime);
        txtTime.setOnClickListener(this);
        labelD = (TextView) findViewById(R.id.label2);
        labelD.setOnClickListener(this);
        labelT = (TextView) findViewById(R.id.label3);
        labelT.setOnClickListener(this);

        btn_reserver = (Button) findViewById(R.id.btn_reservation);
        btn_reserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 0;
                if (aSwitch.isChecked()) i = 1;

//                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
//                Date date = new GregorianCalendar(mYear,mMonth,mDay,mHour,mMinute).getTime();
//                String strDate = sdfDate.format(date);
                String strDate =mYear+"-"+mMonth+"-"+mDay+" "+mHour+":"+mMinute ;

                Reservation reservation = new Reservation(ReservationActivity.this);
                reservation.execute(MainActivity.idUser,
                        listTypeVehicule.getSelectedItem().toString(),
                        strDate,
                        de.getText().toString(), vers.getText().toString(),
                        i + "");
                Toast.makeText(ReservationActivity.this, listTypeVehicule.getSelectedItem().toString()+
                        strDate+
                        "\n La réservation \n a été envoyée", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v == txtDate || v == labelD) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            mYear=year;
                            mMonth=monthOfYear+1;
                            mDay=dayOfMonth;

                            txtDate.setText(dayOfMonth + " - " + (monthOfYear + 1) + " - " + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();

        } else if (v == txtTime || v == labelT) {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            mHour=hourOfDay;
                            mMinute=minute;
                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, true);
            timePickerDialog.show();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
