# Sổ Bán Hàng — TNM

Ứng dụng Android quản lý bán hàng dành cho cửa hàng cá nhân/hộ kinh doanh.

## Chức năng
- Dashboard doanh thu, số đơn, tồn kho.
- Tạo đơn bán hàng nhanh.
- Quản lý sản phẩm, giá bán và tồn kho.
- Quản lý khách hàng.
- Báo cáo doanh thu và đơn hàng.
- Giao diện tiếng Việt, tối ưu điện thoại.
- Hoạt động offline, không cần máy chủ.
- Có thể mở rộng thêm công nợ, nhập hàng, chi phí, CSV và sao lưu.

## Build APK
Dự án dùng Android Gradle Plugin và Java, không cần Node.js/npm.

```bash
./gradlew assembleDebug
```

APK debug: `app/build/outputs/apk/debug/app-debug.apk`

## GitHub Actions
Mỗi push vào `main` hoặc chạy thủ công workflow **Build Android APK** sẽ build APK và upload artifact. Khi tạo tag dạng `v*`, workflow tự tạo GitHub Release kèm APK.

© 2026 khahdihdz
