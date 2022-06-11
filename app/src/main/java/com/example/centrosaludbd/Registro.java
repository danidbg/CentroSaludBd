package com.example.centrosaludbd;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Registro extends AppCompatActivity {

    EditText fname, lapellido, dd, yy, city, pincode, mobno,  password,ldni;
    String sname, sapellido,  sexs, dds, yys, mms, citys, telefono,  passwords,dni,scp ;
    Button register;
    Spinner  mm, sex;
    String fecha;
    Paciente paciente;

    PruebaDb p=new PruebaDb();
    int res;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //DEFINING ALL VIEWS
        fname = (EditText) findViewById(R.id.iNombre);
        lapellido = (EditText) findViewById(R.id.iApellidos);

        dd = (EditText) findViewById(R.id.iDd);
        yy = (EditText) findViewById(R.id.iYy);
        city = (EditText) findViewById(R.id.iCiudad);
        pincode = (EditText) findViewById(R.id.iCP);
        mobno = (EditText) findViewById(R.id.iNT);

        password = (EditText) findViewById(R.id.iContraseña);
        register = (Button) findViewById(R.id.btn_registro);
        mm = (Spinner) findViewById(R.id.iMes);
        sex = (Spinner) findViewById(R.id.iSexo);
        ldni=(EditText) findViewById(R.id.iDni);


        //SET UP THE SPINNER DROOPDOWN
        String[] sexc;
        sexc=getResources().getStringArray(R.array.tipo_personas);

        String[] months;
        months=getResources().getStringArray(R.array.meses);

        ArrayAdapter<String> adapterm = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);
        ArrayAdapter<String> adapterb = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,sexc);



        adapterm.setDropDownViewResource(android.R.layout.simple_spinner_item);
        adapterb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mm.setAdapter(adapterm);
        sex.setAdapter(adapterb);

        p=new PruebaDb();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarTelefono()==true) {
                    if(validarDni()==true) {
                        new AsyncRegistro().execute();
                    }else{
                        ldni.setError("Formato Dni no correcto");
                    }
                }else{
                    mobno.setError("No valido. Debe empezar por 6 o 7 y tener 9 digitos");
                }
            }
        });
    }

    private boolean validarTelefono() {
        return mobno.getText().toString().matches("^[67]\\d{8}$");
    }


    private boolean validarDni() {
        return ldni.getText().toString().matches("[0-9]{8}[A-Z a-z]");
    }

    class AsyncRegistro extends AsyncTask<Void, Void, Void> {


        String records = "",error="";

        @Override

        protected Void doInBackground(Void... voids) {

            try

            {

                sname=fname.getText().toString();
                sapellido=lapellido.getText().toString();
                dds=dd.getText().toString();
                yys=yy.getText().toString();
                scp=pincode.getText().toString();
                citys=city.getText().toString();
                telefono=mobno.getText().toString();
                passwords=password.getText().toString();
                dni=ldni.getText().toString();
                sexs=sex.getSelectedItem().toString();
                fecha=dds+"/"+mm.getSelectedItem().toString()+"/"+yys;
                System.out.println(sname+" "+sapellido+" "+scp+" "+citys+" "+telefono+" "+passwords+" "+dni+" "+sexs+" "+fecha+" ");
                paciente=new Paciente(dni, sname, sapellido, passwords, citys, sexs,scp,fecha);

                res=p.registrarUser(paciente);

                System.out.println(res);



            }

            catch(Exception e)

            {

                error = e.toString();

            }

            return null;

        }



        @Override

        protected void onPostExecute(Void aVoid) {
            if (res > 0) {
                Toast.makeText(Registro.this,"Registrado",Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(Registro.this,"No Registrado",Toast.LENGTH_LONG).show();
            }


        }

    }

    }



