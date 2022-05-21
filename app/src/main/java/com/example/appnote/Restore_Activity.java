package com.example.appnote;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appnote.dal.SQLiteHelper;
import com.example.appnote.model.Note;


public class Restore_Activity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton btBack,btRestore;
    private TextView date_time,eTieude,eGhichu;
    private Note note;
    private View view;
    private Integer color;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore);
        initView();
        btRestore.setOnClickListener(this);
        btBack.setOnClickListener(this);
        Intent intent = getIntent();
        note = (Note) intent.getSerializableExtra("note");
        eTieude.setText(note.getTieude());
        eGhichu.setText(note.getGhichu());
        if(note.getDateTime() == null){
            linearLayout.setVisibility(View.GONE);
        }else{
            date_time.setText(note.getDateTime());
        }
        view.setBackgroundColor(note.getColor());
        color = note.getColor();
    }

    private void initView() {
        eTieude = findViewById(R.id.tvTieude);
        eGhichu = findViewById(R.id.tvGhichu);
        linearLayout = findViewById(R.id.clockDateTime);
        date_time = findViewById(R.id.date_time);
        btRestore = findViewById(R.id.btRestore);
        btBack = findViewById(R.id.btBack);
        view = findViewById(R.id.backgroundcolor);
    }

    @Override
    public void onClick(View view) {
        SQLiteHelper db = new SQLiteHelper(this);
        if(view == btBack){
            finish();
        }
        if(view == btRestore){
            int id = note.getId();
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Thông báo khôi phục");
            builder.setMessage("Bạn có muốn khôi phục " + note.getTieude() + " không?");
            builder.setIcon(R.drawable.ic_restore);
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SQLiteHelper db2 = new SQLiteHelper(getApplicationContext());
                    db2.update_restore(note);
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

