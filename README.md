# Võ Lâm Web — TNM

Game nhập vai võ hiệp 2D chạy trực tiếp trên trình duyệt, ưu tiên offline.

## Công nghệ
TypeScript, Vite, Canvas 2D, LocalStorage và PWA.

## Chạy local
npm install
npm run dev

## Build
npm run build

## GitHub Pages
https://khahdihdz.github.io/tnm/

Mỗi commit vào main sẽ build và deploy bằng GitHub Actions.

## Điều khiển
PC: WASD hoặc phím mũi tên; Space/J để đánh.
Mobile: joystick và các nút hành động.

## Asset
Kiến trúc asset pipeline được chuẩn bị để tiếp nhận asset từ minhsang290775/vltkunity/client/Assets sau khi kiểm tra quyền tái phân phối. Không tải asset nguồn trực tiếp lúc runtime và không sao chép code Unity/Photon.

## Trạng thái
Bản nền playable hiện có di chuyển, combat, quái, EXP, level, Xu, hồi phục, save offline, joystick và PWA. Các hệ thống bản đồ/quest/inventory/skill/shop sẽ được mở rộng trên nền này.

© 2026 khahdihdz
