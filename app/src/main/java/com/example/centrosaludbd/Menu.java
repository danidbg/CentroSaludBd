package com.example.centrosaludbd;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.sql.SQLOutput;

public class Menu extends AppCompatActivity {
    private static String caso,nombre;
    private TextView menu;
    ImageView bGeneral,bUrgencias,bDentista,bTelematica;
    PruebaDb p=new PruebaDb();
    MainActivity m=new MainActivity();


    public static String getNombre() {
        return nombre;
    }

    public static String getCaso() {
        return caso;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        menu=findViewById(R.id.menu);
        bGeneral=findViewById(R.id.iMedicina_General);
        bUrgencias=findViewById(R.id.iUrgencias);
        bDentista=findViewById(R.id.iDentista);
        bTelematica=findViewById(R.id.iCita_telematica);

        new AsyncMenu().execute();

        bUrgencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "112"));
                if (ActivityCompat.checkSelfPermission(Menu.this,
                        android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    startActivity(intent);
                    finish();
                }
            }
        });

        bGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this,CalendarMain.class);
                startActivity(intent);
                caso="Medicina General";
            }
        });

        bTelematica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this,CalendarMain.class);
                startActivity(intent);
                caso="Cita telematica";
            }
        });

        bDentista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this,CalendarMain.class);
                startActivity(intent);
                caso="Dentista";
            }
        });

    }

    class AsyncMenu extends AsyncTask<Void, Void, Void> {


        String records = "",error="";

        @Override

        protected Void doInBackground(Void... voids) {

            try

            {
               nombre=p.seleccionarNombre(MainActivity.sUser);


            }

            catch(Exception e)

            {
                error = e.toString();

            }

            return null;

        }



        @Override

        protected void onPostExecute(Void aVoid) {
            System.out.println(nombre);
           menu.setText("Bienvenido "+nombre);

        }

    }
}