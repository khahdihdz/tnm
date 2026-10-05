# Asset pipeline

Nguồn tham khảo: minhsang290775/vltkunity/client/Assets/

Quy trình nhập asset:
1. Kiểm tra LICENSE và README của nguồn.
2. Xác minh quyền tái phân phối.
3. Quét texture, sprite atlas, prefab, animation, audio và font.
4. Chuyển đổi asset Unity sang định dạng Web.
5. Sinh metadata sprite/animation.
6. Đưa asset hợp lệ vào public/assets/.

Không đưa asset có quyền sử dụng không rõ ràng vào bản public. Game phải tải asset cục bộ để vẫn hoạt động offline.