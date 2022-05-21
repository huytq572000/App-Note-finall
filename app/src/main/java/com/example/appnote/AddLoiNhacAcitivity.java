package com.example.appnote;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appnote.dal.SQLiteHelper;
import com.example.appnote.fragment.FragmentAddColor;
import com.example.appnote.model.Note;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddLoiNhacAcitivity extends AppCompatActivity implements View.OnClickListener{

    private EditText eTieude, eGhichu;
    private TextView date_time_in;
    private ImageButton btSave, btDelete, btBack, btAddColor;
    private View view;
    private AlarmManager alarmManager;
    private Integer color;
    private Calendar calendar;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_loi_nhac);
        initView();
        btSave.setOnClickListener(this);
        btDelete.setOnClickListener(this);
        btBack.setOnClickListener(this);
        color = getResources().getColor(R.color.white);
        btAddColor.setOnClickListener(view3 -> {
            FragmentAddColor fragmentAddColor = new FragmentAddColor();
            fragmentAddColor.setAddColorListener(x -> {
                view.setBackgroundColor(x);
                color = x;
            });
            fragmentAddColor.show(getSupportFragmentManager(), null);
        });
        date_time_in.setInputType(InputType.TYPE_NULL);
        date_time_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimeDialog(date_time_in);
            }
        });
    }
    private void initView() {
        eTieude = findViewById(R.id.tvTieude);
        eGhichu = findViewById(R.id.tvGhichu);
        date_time_in = findViewById(R.id.date_time);
        btSave = findViewById(R.id.btSave);
        btDelete = findViewById(R.id.btDelete);
        btBack = findViewById(R.id.btBack);
        btAddColor = findViewById(R.id.btAdd_color);
        view = findViewById(R.id.backgroundcolor);
    }

    private void showDateTimeDialog(TextView date_time_in) {
         calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        date_time_in.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };
                new TimePickerDialog(AddLoiNhacAcitivity.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }
        };
        new DatePickerDialog(AddLoiNhacAcitivity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    @Override
    public void onClick(View view) {
        if (view == btBack) {
            finish();
        }
        if (view == btSave) {
            if(calendar.getTime().getTime()<=System.currentTimeMillis()){
                Toast.makeText(this, "Thời gian không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            String tieude = eTieude.getText().toString();
            String ghichu = eGhichu.getText().toString();
            String dateTime = date_time_in.getText().toString();
            Note ln = new Note(color,tieude,ghichu,dateTime);
            Intent intent = new Intent(AddLoiNhacAcitivity.this,AlarmRecevier.class);
            intent.setAction("MyAction");
            intent.putExtra("tieude",ln.getTieude());
            intent.putExtra("ghichu",ln.getGhichu());
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            pendingIntent = PendingIntent.getBroadcast(getBaseContext(),0,
                    intent,PendingIntent.FLAG_UPDATE_CURRENT);
            calendar.set(Calendar.MILLISECOND,0);
            calendar.set(Calendar.SECOND,0);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
            if (!tieude.isEmpty() && !ghichu.isEmpty()) {
                 ln = new Note(color,tieude,ghichu,dateTime);
                SQLiteHelper db = new SQLiteHelper(this);
                db.insert(ln);
                finish();
            }
        }
    }
}
