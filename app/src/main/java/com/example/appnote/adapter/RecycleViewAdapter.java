package com.example.appnote.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnote.R;
import com.example.appnote.model.Note;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.NoteViewHolder> {
    private List<Note> list;
    private NoteListener noteListener;

    public RecycleViewAdapter() {
        list = new ArrayList<>();
    }

    public void setList(List<Note> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public Note getNote(int position){
        return list.get(position);
    }

    public void setNoteListener(NoteListener noteListener) {
        this.noteListener = noteListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new RecycleViewAdapter.NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapter.NoteViewHolder holder, int position) {
        Note note = list.get(position);
        holder.tieude.setText(note.getTieude());
        holder.ghichu.setText(note.getGhichu());
        holder.dateTime.setText(note.getDateTime());
        holder.dateTime.setVisibility(View.GONE);
        holder.color.setBackgroundColor(note.getColor());
    }

    @Override
    public int getItemCount() {
        if(list == null){
            return 0;
        }
        return list.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tieude, ghichu, dateTime;
        private View color;

        public NoteViewHolder(@NonNull View view) {
            super(view);
            tieude = view.findViewById(R.id.tieude_loinhac);
            ghichu = view.findViewById(R.id.ghichu_loinhac);
            dateTime = view.findViewById(R.id.time_loinhac);
            color = view.findViewById(R.id.backgroundcolor_item);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(noteListener != null){
                noteListener.onNoteClick(view,getAdapterPosition());
            }
        }
    }
    public interface NoteListener{
        void onNoteClick(View view,int position);
    }
}
