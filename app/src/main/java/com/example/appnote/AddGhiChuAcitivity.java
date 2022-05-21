package com.example.appnote;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appnote.dal.SQLiteHelper;
import com.example.appnote.fragment.FragmentAddColor;
import com.example.appnote.model.Note;

public class AddGhiChuAcitivity extends AppCompatActivity implements View.OnClickListener{

    private EditText eTieude, eGhichu;
    private ImageButton btSave, btDelete, btBack, btAddColor;
    private View view;
    private Integer color;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ghi_chu);
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

    }
    private void initView() {
        eTieude = findViewById(R.id.tvTieude);
        eGhichu = findViewById(R.id.tvGhichu);
        btSave = findViewById(R.id.btSave);
        btDelete = findViewById(R.id.btDelete);
        btBack = findViewById(R.id.btBack);
        btAddColor = findViewById(R.id.btAdd_color);
        view = findViewById(R.id.backgroundcolor);
    }

    @Override
    public void onClick(View view) {
        if (view == btBack) {
            finish();
        }
        if (view == btSave) {
            String tieude = eTieude.getText().toString();
            String ghichu = eGhichu.getText().toString();
            String dateTime = null;
            if (!tieude.isEmpty() && !ghichu.isEmpty()) {
                Note ln = new Note(color,tieude,ghichu,dateTime);
                SQLiteHelper db = new SQLiteHelper(this);
                db.insert(ln);
                finish();
            }
        }
    }
}
