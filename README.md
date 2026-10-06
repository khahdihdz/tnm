# Sổ Bán Hàng — TNM

Ứng dụng Android quản lý bán hàng dành cho cửa hàng cá nhân và hộ kinh doanh.

## APK
Bản phát hành chính thức: **SoBanHang.apk**. Không sử dụng `app-debug.apk`.

## Ký bằng apksign
Sử dụng signing identity chung của `khahdihdz/apksign`.

GitHub Repository Secrets cần có:
- `ANDROID_SIGNING_KEYSTORE_BASE64`
- `ANDROID_SIGNING_STORE_PASSWORD`
- `ANDROID_SIGNING_KEY_ALIAS`
- `ANDROID_SIGNING_KEY_PASSWORD`

Workflow build release, zipalign, ký bằng keystore của apksign và chạy `apksigner verify` trước khi phát hành.

## Chức năng
- Dashboard doanh thu, đơn hàng, tồn kho.
- Bán hàng/POS.
- Quản lý sản phẩm.
- Quản lý khách hàng.
- Báo cáo.
- Lưu dữ liệu cục bộ, hoạt động offline.
- Giao diện tiếng Việt.

## GitHub Actions
Push `main` sẽ build và ký APK. Pull request chỉ kiểm tra build release. Tag `v*` sẽ tạo GitHub Release với **SoBanHang.apk**.

© 2026 khahdihdz
