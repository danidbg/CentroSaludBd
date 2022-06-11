package com.example.centrosaludbd;


import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarView extends LinearLayout {
    private ImageButton nextButton,previousButton;
    private TextView fecha, cabecera;
    private GridView gridView;
    private Locale esLocale = new Locale("es", "ES");
    private static final int MAX_CALENDAR_DAYS=42;
    private Calendar calendar=Calendar.getInstance(esLocale);
    private Context context;
    private Spinner medico;
    private EditText sintomas,horaCita;
    private Button horario,cita;
    private ArrayList <String>medicos=new ArrayList<>();

    private List<Date> dates=new ArrayList<>();
    private List <Events> eventsList=new ArrayList<>();
    private SimpleDateFormat dateFormat=new SimpleDateFormat("MMMM yyyy",esLocale);
    private SimpleDateFormat datesFormat=new SimpleDateFormat("dd",esLocale);
    private SimpleDateFormat monthFormat=new SimpleDateFormat("MM",esLocale);
    private SimpleDateFormat yearFormat=new SimpleDateFormat("yyyy",esLocale);

    private Menu m=new Menu();
    private PruebaDb p=new PruebaDb();

    int t1hora,t1minuto;

    Events e;

    private AlertDialog alertDialog;
    private GridAdapter gridAdapter;

    private static String fechaS,medicoS,nombreS,sintomasS,horaS;


    public CalendarView(Context context) {
        super(context);

    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        inicializeLayout();
        setUpCalendar();

        previousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH,-1);
                setUpCalendar();
            }
        });

        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH,1);
                setUpCalendar();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {



                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setCancelable(true);
                View addView= LayoutInflater.from(adapterView.getContext()).inflate(R.layout.add_event_layout,null);
                cabecera=addView.findViewById(R.id.cabecera);
                medico=addView.findViewById(R.id.listaMedicos);
                horario=addView.findViewById(R.id.btn_horario);
                horaCita=addView.findViewById(R.id.horaSeleccionada);
                sintomas=addView.findViewById(R.id.sintomas);
                cita=addView.findViewById(R.id.btn_Cita);
                cabecera.setText(m.getCaso());

                new AsyncMedicos().execute();





                horario.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TimePickerDialog timePickerDialog=new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourDay, int minutes) {
                                t1hora = hourDay;
                                t1minuto = minutes;

                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0, 0, 0, t1hora, t1minuto);
                                if (t1hora > 10 && t1hora < 20) {
                                    horaCita.setText(t1hora + ":00");
                                }else{
                                    Toast.makeText(context,"El horario es de 10:00 a 20:00",Toast.LENGTH_LONG).show();

                                }
                            }
                        },12,0,true
                                );
                        timePickerDialog.updateTime(t1hora,t1minuto);
                        timePickerDialog.show();

                    }
                });

                cita.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fechaS=datesFormat.format(dates.get(position))+"/"+monthFormat.format(dates.get(position))+"/"+yearFormat.format(dates.get(position));
                        medicoS=medico.getSelectedItem().toString();
                        nombreS=Menu.getNombre();
                        sintomasS=sintomas.getText().toString();
                        horaS=horaCita.getText().toString();
                            e = new Events(sintomasS, medicoS, horaS, nombreS, fechaS);

                            new AsyncCita().execute();
                        }


                });
                builder.setView(addView);
                alertDialog=builder.create();
                alertDialog.show();

            }
        });
    }

    private void inicializeLayout() {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.calendar_layout,this);
        nextButton=view.findViewById(R.id.nextBtn);
        previousButton=view.findViewById(R.id.previousBtn);
        previousButton=view.findViewById(R.id.previousBtn);
        gridView=view.findViewById(R.id.gridview);
        fecha=view.findViewById(R.id.fecha);


    }

    private void setUpCalendar(){
        String currentDate=dateFormat.format(calendar.getTime());
        fecha.setText(currentDate);
        dates.clear();
        Calendar monthCalendar=(Calendar) calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH,1);
        int firstDayofMonth=monthCalendar.get(Calendar.DAY_OF_WEEK)-1;
        monthCalendar.add(Calendar.DAY_OF_MONTH,-firstDayofMonth);

        while(dates.size() < MAX_CALENDAR_DAYS){
            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH,1);

        }


        gridAdapter=new GridAdapter(context,dates,calendar,eventsList);
        gridView.setAdapter(gridAdapter);


    }

    class AsyncMedicos extends AsyncTask<Void, Void, Void> {

        ArrayList <String>pMedicos;
        String records = "",error="";

        @Override

        protected Void doInBackground(Void... voids) {

            try

            {
                System.out.println(m.getCaso());
                pMedicos=p.accederMedicos(m.getCaso());
                for (int i=0;i<pMedicos.size();i++){
                    medicos.add(pMedicos.get(i));
                    System.out.println(pMedicos.get(i));
                }


            }

            catch(Exception e)

            {
                error = e.toString();

            }

            return null;

        }



        @Override

        protected void onPostExecute(Void aVoid) {



            ArrayAdapter<String> adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, medicos);

            medico.setAdapter(adapter);
        }

    }


    class AsyncCita extends AsyncTask<Void, Void, Void> {

        int res;
        String records = "",error="";

        @Override

        protected Void doInBackground(Void... voids) {

            res=p.registrarCita(e);
            return null;

        }



        @Override

        protected void onPostExecute(Void aVoid) {
            if (res > 0) {
                Toast.makeText(context,"Cita Registrada",Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(context,"Cita No Registrada",Toast.LENGTH_LONG).show();
            }
        }

    }
}
