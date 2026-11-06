Mô tả nâng cao

NC2: Dialog chọn mức giá bằng setSingleChoiceItems.
Thêm mục “Chọn mức giá” vào options menu. 
Hộp thoại hiển thị 3 mức (Dưới 30.000 đ, 30.000 – 45.000 đ, Trên 45.000 đ) dưới dạng danh sách có nút tròn, mặc định chọn mức đang áp dụng. 
Việc chạm vào một mức chỉ ghi nhớ lựa chọn, chỉ khi bấm Áp dụng thì các món thuộc mức giá đó mới được đưa lên đầu danh sách. 
Bấm Hủy thì không thay đổi gì.

NC3: Dùng MaterialAlertDialogBuilder thay cho AlertDialog.Builder.
Thay AlertDialog.Builder bằng MaterialAlertDialogBuilder ở tất cả hộp thoại (chi tiết, xác nhận xóa, xóa tất cả, Thêm/Sửa, chọn mức giá). 
API giữ nguyên nên chỉ cần đổi tên lớp. 
Khác biệt: hộp thoại theo Material 3, bo góc lớn hơn, nút dạng chữ phẳng, màu lấy từ theme. 
Để dễ nhận thấy hơn, em tạo thêm style DialogCam (kế thừa ThemeOverlay.Material3.MaterialAlertDialog) đổi nền sang kem cam nhạt (#FFF3E0), nút và nút chọn màu cam đậm (#E65100), chữ màu nâu đậm, rồi truyền vào MaterialAlertDialogBuilder(this, R.style.DialogCam).
