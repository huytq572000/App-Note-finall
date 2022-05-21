package com.example.appnote;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnote.dal.SQLiteHelper;
import com.example.appnote.fragment.FragmentAddColor;
import com.example.appnote.model.Note;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Update_Delete_Loi_Nhac_Activity extends AppCompatActivity implements View.OnClickListener {
    private EditText eTieude, eGhichu;
    private TextView date_time;
    private ImageButton btUpdate, btDelete, btBack, btAddColor;
    private Note note;
    private View view;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Integer color;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendar = Calendar.getInstance();
        setContentView(R.layout.activity_update_delete_loi_nhac);
        initView();
        btUpdate.setOnClickListener(this);
        btDelete.setOnClickListener(this);
        btBack.setOnClickListener(this);
        Intent intent = getIntent();
        note = (Note) intent.getSerializableExtra("note");
        eTieude.setText(note.getTieude());
        eGhichu.setText(note.getGhichu());
        date_time.setText(note.getDateTime());
        view.setBackgroundColor(note.getColor());
        color = note.getColor();
        btAddColor.setOnClickListener(view3 -> {
            FragmentAddColor fragmentAddColor = new FragmentAddColor();
            fragmentAddColor.setAddColorListener(x -> {
                view.setBackgroundColor(x);
                color = x;
            });
            fragmentAddColor.show(getSupportFragmentManager(), null);
        });
        date_time.setInputType(InputType.TYPE_NULL);
        date_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimeDialog(date_time);
            }
        });
    }

    private void showDateTimeDialog(TextView date_time_in) {
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
                new TimePickerDialog(Update_Delete_Loi_Nhac_Activity.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }
        };
        new DatePickerDialog(Update_Delete_Loi_Nhac_Activity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void initView() {
        eTieude = findViewById(R.id.tvTieude);
        eGhichu = findViewById(R.id.tvGhichu);
        date_time = findViewById(R.id.tvdate_time);
        btUpdate = findViewById(R.id.btSave);
        btDelete = findViewById(R.id.btDelete);
        btBack = findViewById(R.id.btBack);
        btAddColor = findViewById(R.id.btAdd_color);
        view = findViewById(R.id.backgroundcolor);
    }

    @Override
    public void onClick(View view) {
        SQLiteHelper db = new SQLiteHelper(this);
        if (view == btBack) {
            finish();
        }
        if (view == btUpdate) {
            if(calendar.getTime().getTime()<=System.currentTimeMillis()){
                Toast.makeText(this, "Thời gian không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            String tieude = eTieude.getText().toString();
            String ghichu = eGhichu.getText().toString();
            String dateTime = date_time.getText().toString();
            Note ln = new Note(color, tieude, ghichu, dateTime);
            Intent intent = new Intent(Update_Delete_Loi_Nhac_Activity.this, AlarmRecevier.class);
            intent.setAction("MyAction");
            intent.putExtra("tieude", ln.getTieude());
            intent.putExtra("ghichu", ln.getGhichu());
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            pendingIntent = PendingIntent.getBroadcast(Update_Delete_Loi_Nhac_Activity.this, 0,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            if (!tieude.isEmpty() && !ghichu.isEmpty()) {
                int id = note.getId();
                Note n = new Note(id, color, tieude, ghichu, dateTime);
                SQLiteHelper db1 = new SQLiteHelper(this);
                db.update(n);
                finish();
            }
        }
        if (view == btDelete) {
            int id = note.getId();
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Thông báo xóa");
            builder.setMessage("Bạn có muốn xóa " + note.getTieude() + " không?");
            builder.setIcon(R.drawable.ic_thung_rac);
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SQLiteHelper db2 = new SQLiteHelper(getApplicationContext());
                    db2.update_delete(note);
                    finish();
                }
            });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
