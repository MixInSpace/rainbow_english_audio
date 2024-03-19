package ru.mixinspace.r_en_audio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.MyViewHolder> {

    private final List<AudioList> list;
    private final Context context;
    private int playingPosition = 0;
    private final AudioChangeListener audioChangeListener;

    public AudioAdapter(List<AudioList> list, Context context) {
        this.list = list;
        this.context = context;
        this.audioChangeListener = ((AudioChangeListener)context);
    }

    @NonNull
    @Override
    public AudioAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.audio_adapter_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull AudioAdapter.MyViewHolder holder, int position) {
        AudioList list2 = list.get(holder.getAdapterPosition());

        if (list2.isPlaying()){
            playingPosition = holder.getAdapterPosition();
            holder.rootLayout.setBackgroundResource(R.drawable.selected_rounded_10);
        } else {
            holder.rootLayout.setBackgroundResource(R.drawable.unselected_rounded_10);
        }

        holder.title.setText(list2.getTitle());
        holder.rootLayout.setOnClickListener(view -> {
            list.get(playingPosition).setPlaying(false);
            list2.setPlaying(true);
            audioChangeListener.onChanged(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout rootLayout;
        private final TextView title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            rootLayout = itemView.findViewById(R.id.root_layout);
            title = itemView.findViewById(R.id.audio_title);
        }
    }
}
