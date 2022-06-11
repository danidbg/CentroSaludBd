package com.example.centrosaludbd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    boolean exito;
    TextView user,pass;
    public static String sUser,sPass;
    Button show,registro;

    PruebaDb p=new PruebaDb();



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        user = (TextView) findViewById(R.id.et_user);

        pass = (TextView) findViewById(R.id.et_password);

        show = (Button) findViewById(R.id.btn_login);

        registro = (Button) findViewById(R.id.btn_registro);


            show.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View view) {

                    new AsyncLogin().execute();


                }

            });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent i = new Intent(MainActivity.this, Registro.class);
                    startActivity(i);

            }
        });

    }



    class AsyncLogin extends AsyncTask<Void, Void, Void> {


        String records = "",error="";

        @Override

        protected Void doInBackground(Void... voids) {

            try

            {
                sUser=user.getText().toString();
                sPass=pass.getText().toString();
                System.out.println("Usuario"+sUser+"Contraseña"+sPass);
                exito=p.accesoLogin(sUser,sPass);
                System.out.println(exito);

            }

            catch(Exception e)

            {

                error = e.toString();

            }

            return null;

        }



        @Override

        protected void onPostExecute(Void aVoid) {
            if(!(user.getText().toString().isEmpty() || pass.getText().toString().isEmpty())) {
            if(exito){
                Intent i=new Intent(MainActivity.this,Menu.class);
                startActivity(i);
                exito=false;
            }else{
                Toast.makeText(MainActivity.this,"Credenciales no correctas",Toast.LENGTH_LONG).show();
                exito=false;
            }

          }else{

            Toast.makeText(MainActivity.this,"Credenciales vacias",Toast.LENGTH_LONG).show();
                exito=false;
        }
        }
    }

    }
