package vn.edu.vhu.ltdd.a11dialog;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/** Lab A11 — Menu, Context menu, Dialog, Toast và Snackbar. MSSV 231A010308. */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "A11_231A010308";

    private final List<MonAn> danhSach = new ArrayList<>();
    private MonAnAdapter adapter;
    private ListView lvMonAn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        lvMonAn = findViewById(R.id.lvMonAn);
        TextView tvTrong = findViewById(R.id.tvTrong);

        taoDuLieuMau();
        adapter = new MonAnAdapter(this, danhSach);
        lvMonAn.setAdapter(adapter);
        lvMonAn.setEmptyView(tvTrong);

        // Giữ lâu một dòng sẽ mở context menu
        registerForContextMenu(lvMonAn);

        lvMonAn.setOnItemClickListener((parent, view, position, id) ->
                hienChiTiet(danhSach.get(position)));
    }

    private void taoDuLieuMau() {
        danhSach.add(new MonAn("Phở bò tái", "Nước dùng ninh xương 8 giờ", 45000, R.drawable.mon1));
        danhSach.add(new MonAn("Bún chả Hà Nội", "Chả nướng than hoa", 50000, R.drawable.mon2));
        danhSach.add(new MonAn("Cơm tấm sườn", "Sườn nướng mật ong", 40000, R.drawable.mon3));
        danhSach.add(new MonAn("Bánh mì thịt nguội", "Pate nhà làm", 25000, R.drawable.mon4));
    }

    // ===================== 1. OPTIONS MENU =====================

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true; // false thì menu không hiện
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_them) {
            hienDialogThemSua(-1);
            return true;
        } else if (id == R.id.action_muc_gia) {
            hienDialogMucGia();
            return true;
        } else if (id == R.id.action_sap_xep_ten) {
            Collections.sort(danhSach, Comparator.comparing(MonAn::getTen));
            adapter.notifyDataSetChanged();
            Toast.makeText(this, R.string.da_sap_xep_ten, Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_sap_xep_gia) {
            Collections.sort(danhSach, (a, b) -> Integer.compare(a.getGia(), b.getGia()));
            adapter.notifyDataSetChanged();
            Toast.makeText(this, R.string.da_sap_xep_gia, Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_xoa_het) {
            xacNhanXoaHet();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // ===================== 2. CONTEXT MENU =====================

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo info) {
        super.onCreateContextMenu(menu, v, info);
        if (v.getId() == R.id.lvMonAn) {
            AdapterView.AdapterContextMenuInfo i = (AdapterView.AdapterContextMenuInfo) info;
            menu.setHeaderTitle(danhSach.get(i.position).getTen());
            getMenuInflater().inflate(R.menu.menu_context, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (info == null) return super.onContextItemSelected(item);

        int viTri = info.position;
        int id = item.getItemId();
        if (id == R.id.ctx_sua) {
            hienDialogThemSua(viTri);
            return true;
        } else if (id == R.id.ctx_xoa) {
            xacNhanXoaMot(viTri);
            return true;
        }
        return super.onContextItemSelected(item);
    }

    // ===================== 3. DIALOG =====================

    private void hienChiTiet(MonAn mon) {
        new MaterialAlertDialogBuilder(this, R.style.DialogCam)
                .setTitle(mon.getTen())
                .setMessage(getString(R.string.chi_tiet_format,
                        mon.getMoTa(), MonAn.dinhDangGia(mon.getGia())))
                .setPositiveButton(R.string.dong, null)
                .show();
    }

    private void xacNhanXoaMot(int viTri) {
        MonAn mon = danhSach.get(viTri);
        new MaterialAlertDialogBuilder(this, R.style.DialogCam)
                .setTitle(R.string.xac_nhan_xoa)
                .setMessage(getString(R.string.hoi_xoa, mon.getTen()))
                .setPositiveButton(R.string.xoa, (d, w) -> xoaCoHoanTac(viTri))
                .setNegativeButton(R.string.huy, null)
                .show();
    }

    private void xacNhanXoaHet() {
        if (danhSach.isEmpty()) {
            Toast.makeText(this, R.string.danh_sach_trong, Toast.LENGTH_SHORT).show();
            return;
        }
        new MaterialAlertDialogBuilder(this, R.style.DialogCam)
                .setTitle(R.string.xac_nhan_xoa)
                .setMessage(getString(R.string.hoi_xoa_het, danhSach.size()))
                .setPositiveButton(R.string.xoa_het, (d, w) -> {
                    List<MonAn> banSaoLuu = new ArrayList<>(danhSach);
                    danhSach.clear();
                    adapter.notifyDataSetChanged();
                    Snackbar.make(lvMonAn, R.string.da_xoa_het, Snackbar.LENGTH_LONG)
                            .setAction(R.string.hoan_tac, v -> {
                                danhSach.addAll(banSaoLuu);
                                adapter.notifyDataSetChanged();
                            })
                            .show();
                })
                .setNegativeButton(R.string.huy, null)
                .show();
    }

    private void xoaCoHoanTac(int viTri) {
        MonAn daXoa = danhSach.remove(viTri);
        adapter.notifyDataSetChanged();
        Log.d(TAG, "Đã xóa " + daXoa.getTen() + " tại vị trí " + viTri);

        Snackbar.make(lvMonAn, getString(R.string.da_xoa, daXoa.getTen()), Snackbar.LENGTH_LONG)
                .setAction(R.string.hoan_tac, v -> {
                    danhSach.add(viTri, daXoa); // trả về đúng vị trí cũ
                    adapter.notifyDataSetChanged();
                    lvMonAn.smoothScrollToPosition(viTri);
                })
                .show();
    }

    private int mucDangChon = 0;

    private boolean trongMuc(int gia, int muc) {
        if (muc == 0) return gia < 30000;
        if (muc == 1) return gia >= 30000 && gia <= 45000;
        return gia > 45000;
    }

    /** NC2: chọn một mức giá bằng setSingleChoiceItems, bấm Áp dụng mới có hiệu lực. */
    private void hienDialogMucGia() {
        String[] muc = {"Dưới 30.000 đ", "30.000 – 45.000 đ", "Trên 45.000 đ"};
        final int[] chon = {mucDangChon};
        new MaterialAlertDialogBuilder(this, R.style.DialogCam)
                .setTitle(R.string.muc_gia)
                .setSingleChoiceItems(muc, mucDangChon, (d, which) -> chon[0] = which)
                .setPositiveButton(R.string.ap_dung, (d, w) -> {
                    mucDangChon = chon[0];
                    Collections.sort(danhSach, (a, b) -> Boolean.compare(
                            trongMuc(b.getGia(), mucDangChon),
                            trongMuc(a.getGia(), mucDangChon)));
                    adapter.notifyDataSetChanged();
                    lvMonAn.smoothScrollToPosition(0);
                    Toast.makeText(this, R.string.da_chon_muc, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.huy, null)
                .show();
    }

    /** viTri == -1: thêm mới; ngược lại: sửa món tại vị trí đó. */
    private void hienDialogThemSua(int viTri) {
        boolean themMoi = (viTri < 0);
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_mon_an, null, false);
        EditText edtTen = v.findViewById(R.id.edtTen);
        EditText edtMoTa = v.findViewById(R.id.edtMoTa);
        EditText edtGia = v.findViewById(R.id.edtGia);

        if (!themMoi) {
            MonAn mon = danhSach.get(viTri);
            edtTen.setText(mon.getTen());
            edtMoTa.setText(mon.getMoTa());
            edtGia.setText(String.valueOf(mon.getGia()));
        }

        AlertDialog dialog = new MaterialAlertDialogBuilder(this, R.style.DialogCam)
                .setTitle(themMoi ? R.string.them_mon : R.string.sua_mon)
                .setView(v)
                .setPositiveButton(R.string.luu, null) // ghi đè listener ở dưới
                .setNegativeButton(R.string.huy, null)
                .create();

        dialog.show();

        // Gán lại listener SAU show() để dialog không tự đóng khi dữ liệu sai
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(btn -> {
            String ten = edtTen.getText().toString().trim();
            String moTa = edtMoTa.getText().toString().trim();
            String giaStr = edtGia.getText().toString().trim();

            if (ten.isEmpty()) {
                edtTen.setError(getString(R.string.err_ten));
                return;
            }
            int gia;
            try {
                gia = Integer.parseInt(giaStr);
            } catch (NumberFormatException e) {
                edtGia.setError(getString(R.string.err_gia));
                return;
            }
            if (gia <= 0) {
                edtGia.setError(getString(R.string.err_gia_duong));
                return;
            }

            if (themMoi) {
                danhSach.add(0, new MonAn(ten, moTa, gia, R.drawable.mon1));
                lvMonAn.smoothScrollToPosition(0);
            } else {
                MonAn mon = danhSach.get(viTri);
                mon.setTen(ten);
                mon.setMoTa(moTa);
                mon.setGia(gia);
            }
            adapter.notifyDataSetChanged();
            dialog.dismiss();
            Toast.makeText(this, themMoi ? R.string.da_them : R.string.da_sua,
                    Toast.LENGTH_SHORT).show();
        });
    }
}
// commit2