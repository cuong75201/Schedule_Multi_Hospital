# CarePath — Nền tảng đặt lịch khám đa bệnh viện

CarePath là dự án xây dựng nền tảng kết nối bệnh nhân với nhiều bệnh viện để **tra cứu và đăng ký khung tiếp nhận khám**. Bệnh nhân có thể tìm theo bệnh viện, chuyên khoa, dịch vụ hoặc bác sĩ (nếu bệnh viện cho phép), sau đó theo dõi, đổi hoặc hủy lịch theo chính sách của từng cơ sở. Nền tảng hỗ trợ điều phối đăng ký; bệnh viện vẫn chủ động về năng lực tiếp nhận và quy trình khám thực tế.

## Định hướng chức năng

- **Bệnh nhân:** tra cứu thông tin, đăng ký nhiều lịch tại các bệnh viện khác nhau, quản lý lịch hẹn, nhận thông báo và lựa chọn thanh toán online hoặc tại quầy theo chính sách bệnh viện.
- **Bệnh viện:** công bố cơ sở, chuyên khoa, dịch vụ, bác sĩ và các khung tiếp nhận; cấu hình sức chứa, chính sách đặt/đổi/hủy lịch; xác nhận bệnh nhân đã đến bằng check-in và xử lý các trường hợp phát sinh.
- **Quản trị nền tảng:** quản lý các bệnh viện tham gia, theo dõi giao dịch và tính phí sử dụng nền tảng theo kỳ tháng. Thanh toán online được định hướng chuyển trực tiếp cho bệnh viện; nền tảng không giữ tiền của bệnh nhân.

Một khung tiếp nhận có thể nhận **nhiều bệnh nhân** theo sức chứa do bệnh viện cấu hình. Giờ đăng ký là **thời gian tiếp nhận dự kiến**, không phải cam kết bác sĩ sẽ bắt đầu khám đúng giờ. Việc ưu tiên, xếp hàng sau check-in và các quyết định chuyên môn thuộc bệnh viện.

## Công nghệ và cấu trúc

| Đường dẫn | Nội dung |
| --- | --- |
| `client/` | Giao diện React, TypeScript, Vite và Tailwind CSS |
| `server/` | Backend Java Spring Boot, Maven |
| `index.html`, `tailwind.src.css`, `tailwind.css` | Trang giới thiệu độc lập và CSS đã biên dịch |

## Chạy thử giao diện

Yêu cầu Node.js và npm tương thích với phiên bản Vite trong `client/package.json`.

```bash
cd client
npm ci
npm run dev
```

## Chạy bằng Docker Compose

Từ thư mục gốc dự án:

```bash
cp .env.example .env
docker compose up --build -d
```

- Client (Nginx): http://localhost:3000
- Server (Spring Boot): http://localhost:8080
- PostgreSQL: `localhost:5432`, database `carepath`
- Request tới `/api/` qua client được chuyển tiếp sang server.

Có thể đổi cổng và thông tin PostgreSQL trong `.env`; hãy đổi `POSTGRES_PASSWORD` trước khi triển khai ngoài máy cá nhân. Dữ liệu PostgreSQL được giữ trong Docker volume `postgres_data`. Dừng các container bằng `docker compose down` (không xóa volume).

Hiện backend chưa có API và chưa có migration tạo bảng. Compose đặt `SPRING_JPA_HIBERNATE_DDL_AUTO=none`, nên database mới tạo vẫn rỗng; cần bổ sung migration trước khi dùng các entity. Docker build tạm bỏ qua biên dịch test vì `HelloControllerTest` cũ chưa tương thích với dependencies hiện tại.
