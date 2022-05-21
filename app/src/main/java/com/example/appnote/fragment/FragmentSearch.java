package com.example.appnote.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appnote.R;
import com.example.appnote.adapter.RecycleViewAdapter;
import com.example.appnote.dal.SQLiteHelper;
import com.example.appnote.model.Note;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class FragmentSearch extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private ImageButton btSearch;
    private SearchView searchView;
    private EditText eDateFrom, eDateTo;
    private RecycleViewAdapter adapter;
    private SQLiteHelper db;
    private Calendar calendar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        adapter = new RecycleViewAdapter();
        db = new SQLiteHelper(getContext());
//        List<Note> list = db.getAll();
//        adapter.setList(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s != null && s.length()>0){
                    List<Note> list = db.searchByTieuDe(s);
                    adapter.setList(list);
                }
                else{
                    adapter.setList(null);
                }
                return true;
            }
        });
        eDateFrom.setOnClickListener(this);
        eDateTo.setOnClickListener(this);
        btSearch.setOnClickListener(this);
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.rcv_search);
        btSearch = view.findViewById(R.id.btSearch);
        eDateFrom = view.findViewById(R.id.dateFrom);
        eDateTo = view.findViewById(R.id.eDateTo);
        searchView = view.findViewById(R.id.search);
    }

    @Override
    public void onClick(View view) {
        if (view == eDateFrom) {
            calendar = Calendar.getInstance();
            DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    eDateFrom.setText(simpleDateFormat.format(calendar.getTime()));
                }
            };
            new DatePickerDialog(getContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        }
        if(view==eDateTo){
            calendar = Calendar.getInstance();
            DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    eDateTo.setText(simpleDateFormat.format(calendar.getTime()));
                }
            };
            new DatePickerDialog(getContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        }
        if(view == btSearch){
            String dateFrom = eDateFrom.getText().toString();
            String dateTo = eDateTo.getText().toString();
            if(!dateFrom.isEmpty()&&!dateTo.isEmpty()){
                List<Note> list = db.searchByDate(dateFrom,dateTo);
                adapter.setList(list);
            }
        }
    }
}
