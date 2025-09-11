<h2 align="center">
    <a href="https://dainam.edu.vn/vi/khoa-cong-nghe-thong-tin">
    🎓 Faculty of Information Technology (DaiNam University)
    </a>
</h2>

<h2 align="center">
   Game Tic Tac Toe (Caro 3x3)
</h2>

<div align="center">
    <p align="center">
        <img src="docs/aiotlab_logo.png" alt="AIoTLab Logo" width="170"/>
        <img src="docs/fitdnu_logo.png" alt="FIT Logo" width="180"/>
        <img src="docs/dnu_logo.png" alt="DaiNam University Logo" width="200"/>
    </p>

[![AIoTLab](https://img.shields.io/badge/AIoTLab-green?style=for-the-badge)](https://www.facebook.com/DNUAIoTLab)
[![Faculty of Information Technology](https://img.shields.io/badge/Faculty%20of%20Information%20Technology-blue?style=for-the-badge)](https://dainam.edu.vn/vi/khoa-cong-nghe-thong-tin)
[![DaiNam University](https://img.shields.io/badge/DaiNam%20University-orange?style=for-the-badge)](https://dainam.edu.vn)

</div>

---

## 📖 1. Giới thiệu hệ thống
Hệ thống **Game Cờ Caro 3x3 sử dụng giao thức TCP** được xây dựng theo mô hình **Client/Server**.  
Server chịu trách nhiệm:
- Quản lý danh sách người chơi đang kết nối.  
- Đồng bộ lượt đánh và gửi thông tin ván đấu tới tất cả client.  
- Lưu lịch sử ván chơi (thắng/thua/hòa) vào cơ sở dữ liệu.  

Client có giao diện **Java Swing**, cho phép người dùng:
- Đăng nhập/nhập tên người chơi.  
- Chơi cờ Caro 3x3 trực tuyến theo thời gian thực.  
- Xem thông báo khi thắng, thua hoặc hòa.  

Giao thức **TCP** được chọn vì tính **đảm bảo truyền tin cậy**:  
- Không mất gói dữ liệu (các nước đi được truyền đầy đủ, chính xác).  
- Duy trì kết nối liên tục cho đến khi trận đấu kết thúc.  

---

## 🔧 2. Công nghệ sử dụng

| Thành phần | Công nghệ / Công cụ | Mô tả |
|-----------|------------------|-------|
| **Ngôn ngữ lập trình** | [![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)](https://www.java.com/) | Xây dựng logic Client/Server và giao diện người dùng. |
| **Giao thức mạng** | **TCP Socket** | Truyền dữ liệu tin cậy giữa client và server. |
| **Giao diện người dùng** | **Java Swing** | Hiển thị bàn cờ, nước đi, trạng thái game. |
| **Cơ sở dữ liệu** | [![SQLite](https://img.shields.io/badge/SQLite-07405E?style=for-the-badge&logo=sqlite&logoColor=white)](https://www.sqlite.org/) + JDBC | Lưu trữ thông tin lịch sử ván chơi (người thắng/thua, thời gian). |
| **Thư viện** | **SQLite JDBC** | Kết nối và thao tác cơ sở dữ liệu từ Java. |
| **Quản lý mã nguồn** | [![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)](https://git-scm.com/) | Theo dõi và quản lý phiên bản mã nguồn. |
| **Công cụ phát triển** | IntelliJ IDEA / NetBeans / Eclipse | IDE hỗ trợ code, gỡ lỗi và chạy ứng dụng. |

---

## 🚀 3. Hình ảnh các chức năng
_(Thêm ảnh minh họa giao diện client, server, bàn cờ tại đây nếu có)_

---

## ⚙️ 4. Các bước cài đặt

1. **Clone dự án**  
   ```bash
   git clone https://github.com/NguyenDanhPhong270604/LTM-Game-Tic-Tac-Toe-Caro-3x3-.git
   cd LTM-Game-Tic-Tac-Toe-Caro-3x3-
