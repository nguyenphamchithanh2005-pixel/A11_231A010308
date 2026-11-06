package vn.edu.vhu.ltdd.a11dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MonAnAdapter extends ArrayAdapter<MonAn> {

    public MonAnAdapter(@NonNull Context context, @NonNull List<MonAn> ds) {
        super(context, 0, ds);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_mon_an, parent, false);
        }
        MonAn mon = getItem(position);
        ImageView img = convertView.findViewById(R.id.imgMon);
        TextView tvTen = convertView.findViewById(R.id.tvTen);
        TextView tvMoTa = convertView.findViewById(R.id.tvMoTa);
        TextView tvGia = convertView.findViewById(R.id.tvGia);

        if (mon != null) {
            img.setImageResource(mon.getAnh());
            tvTen.setText(mon.getTen());
            tvMoTa.setText(mon.getMoTa());
            tvGia.setText(MonAn.dinhDangGia(mon.getGia()));
        }
        return convertView;
    }
}