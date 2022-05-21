package com.example.appnote.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appnote.AddLoiNhacAcitivity;
import com.example.appnote.R;
import com.example.appnote.Update_Delete_Loi_Nhac_Activity;
import com.example.appnote.adapter.RecycleViewAdapter;
import com.example.appnote.dal.SQLiteHelper;
import com.example.appnote.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragmentLoiNhac extends Fragment implements RecycleViewAdapter.NoteListener{

    private RecyclerView recyclerView;
    private RecycleViewAdapter adapter;
    private SQLiteHelper db;
    private FloatingActionButton add;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_loinhac,container,false);
    }

        @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rcv_loinhacs);
        adapter = new RecycleViewAdapter();
        db = new SQLiteHelper(getContext());
        List<Note> list = db.getAll();
        List<Note> listchuaxoa = new ArrayList<>();
        for (Note ln: list ) {
            if(!ln.getDaxoa()&& ln.getDateTime() != null){
                listchuaxoa.add(ln);
            }
        }
        adapter.setList(listchuaxoa);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setNoteListener(this);
        add = view.findViewById(R.id.add);
        add.setOnClickListener(v1 -> {
            Intent intent = new Intent(getContext(), AddLoiNhacAcitivity.class);
            getActivity().startActivity(intent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Note> list = db.getAll();
        List<Note> listchuaxoa = new ArrayList<>();
        for (Note ln: list ) {
            if(!ln.getDaxoa()&& ln.getDateTime() != null){
                listchuaxoa.add(ln);
            }
        }
        adapter.setList(listchuaxoa);
    }

    @Override
    public void onNoteClick(View view, int position) {
        Note loinhac = adapter.getNote(position);
        Intent intent = new Intent(getActivity(), Update_Delete_Loi_Nhac_Activity.class);
        intent.putExtra("note",loinhac);
        startActivity(intent);
    }


}
