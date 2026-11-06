package vn.edu.vhu.ltdd.a11dialog;

import java.text.NumberFormat;
import java.util.Locale;

public class MonAn {
    private String ten;
    private String moTa;
    private int gia;
    private final int anh;

    public MonAn(String ten, String moTa, int gia, int anh) {
        this.ten = ten;
        this.moTa = moTa;
        this.gia = gia;
        this.anh = anh;
    }

    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public int getGia() { return gia; }
    public void setGia(int gia) { this.gia = gia; }

    public int getAnh() { return anh; }

    /** 45000 -> "45.000 đ" */
    public static String dinhDangGia(int gia) {
        return NumberFormat.getInstance(Locale.forLanguageTag("vi-VN")).format(gia) + " đ";
    }
}