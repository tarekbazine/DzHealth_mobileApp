package com.example.seadev.dzhealth_mobileapp;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.triggertrap.seekarc.SeekArc;

public class PrelevementActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    Spinner listTypePrelv;
    SeekArc seekArc;
    TextView resultat;
    double result;
    int entier;
    Button btn_prelevement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prelevement);

        listTypePrelv = (Spinner) findViewById(R.id.type_prelevement);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_prelevement, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        listTypePrelv.setAdapter(adapter);
        listTypePrelv.setOnItemSelectedListener(this);


        seekArc = (SeekArc) findViewById(R.id.seekArc);
        seekArc.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {
            @Override
            public void onProgressChanged(SeekArc seekArc, int i, boolean b) {
                switch (listTypePrelv.getSelectedItemPosition()) {
                    case 0:
                        result = (double) (seekArc.getProgress() + 40) / 100;
                        if (result < 0.6 || result > 1.8) {
                            seekArc.setProgressColor(getResources().getColor(R.color.resultat_dangereux));
                            resultat.setTextColor(getResources().getColor(R.color.resultat_dangereux));
                        } else{
                            seekArc.setProgressColor(getResources().getColor(R.color.colorAccent));
                            resultat.setTextColor(getResources().getColor(R.color.colorAccent));
                        }
                        resultat.setText(result + "");
                        break;
                    case 1:
                        entier = seekArc.getProgress() + 10;
                        resultat.setText(entier + "");
                        break;
                    case 2:
                        entier = (int) ((seekArc.getProgress() + 50) / 1.5);
                        if (entier < 60 || entier > 120){
                            seekArc.setProgressColor(getResources().getColor(R.color.resultat_dangereux));
                            resultat.setTextColor(getResources().getColor(R.color.resultat_dangereux));
                        } else{
                            seekArc.setProgressColor(getResources().getColor(R.color.colorAccent));
                            resultat.setTextColor(getResources().getColor(R.color.colorAccent));
                        }
                        resultat.setText(entier + "/" + (entier + 30));
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekArc seekArc) {
            }

            @Override
            public void onStopTrackingTouch(SeekArc seekArc) {
            }
        });


        btn_prelevement = (Button) findViewById(R.id.btn_envoyer_prelev);
        btn_prelevement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Prelevement prelevement=new Prelevement(PrelevementActivity.this);

                switch (listTypePrelv.getSelectedItemPosition()) {
                    //// TODO: 09/06/2017 phppppppppppppp
                    case 0:
                        prelevement.execute(MainActivity.idUser,"G",result+"");
                        Toast.makeText(PrelevementActivity.this,
                                "Taux glycémie = " + result + "\n a été envoyé",
                                Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        prelevement.execute(MainActivity.idUser,"P",entier+"");
                        Toast.makeText(PrelevementActivity.this,
                                "Poids = " + entier + "\n a été envoyé",
                                Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        String s=entier + ""; //////////***************
                        prelevement.execute(MainActivity.idUser,"T",s);
                        Toast.makeText(PrelevementActivity.this,
                                "Tension artérielle = " + entier + "/" + (entier + 30) + "\n a été envoyé",
                                Toast.LENGTH_LONG).show();
                        break;
                }
                finish();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        switch (position) {
            case 0:
                findViewById(R.id.result_glycemie).setVisibility(View.VISIBLE);
                findViewById(R.id.result_tension).setVisibility(View.GONE);
                findViewById(R.id.result_poids).setVisibility(View.GONE);
                resultat = (TextView) findViewById(R.id.taux_glycemie);
                break;
            case 1:
                findViewById(R.id.result_glycemie).setVisibility(View.GONE);
                findViewById(R.id.result_tension).setVisibility(View.GONE);
                findViewById(R.id.result_poids).setVisibility(View.VISIBLE);
                resultat = (TextView) findViewById(R.id.poids);
                break;
            case 2:
                findViewById(R.id.result_glycemie).setVisibility(View.GONE);
                findViewById(R.id.result_tension).setVisibility(View.VISIBLE);
                findViewById(R.id.result_poids).setVisibility(View.GONE);
                resultat = (TextView) findViewById(R.id.tension);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
