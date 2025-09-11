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
Hệ thống Game Cờ Caro 3x3 sử dụng giao thức TCP được xây dựng theo mô hình Client/Server.  
Server chịu trách nhiệm:
- Quản lý danh sách người chơi đang kết nối.  
- Đồng bộ lượt đánh và gửi thông tin ván đấu tới tất cả client.  
- Lưu lịch sử ván chơi (thắng/thua/hòa) vào cơ sở dữ liệu.  

Client có giao diện Java Swing, cho phép người dùng:
- Đăng nhập/nhập tên người chơi.  
- Chơi cờ Caro 3x3 trực tuyến theo thời gian thực.  
- Xem thông báo khi thắng, thua hoặc hòa.  

Giao thức TCP được chọn vì tính đảm bảo truyền tin cậy:  
- Không mất gói dữ liệu (các nước đi được truyền đầy đủ, chính xác).  
- Duy trì kết nối liên tục cho đến khi trận đấu kết thúc.  

---

## 🔧 2. Công nghệ sử dụng
[![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)](https://www.java.com/)
[![SQLite](https://img.shields.io/badge/SQLite-07405E?style=for-the-badge&logo=sqlite&logoColor=white)](https://www.sqlite.org/)

---

## 🚀 3. Hình ảnh các chức năng
_

---

## ⚙️ 4. Các bước cài đặt & Chạy ứng dụng

1. **Clone dự án**  
   git clone https://github.com/NguyenDanhPhong270604/LTM-Game-Tic-Tac-Toe-Caro-3x3-.git   
   cd LTM-Game-Tic-Tac-Toe-Caro-3x3-

### 🖥️ Yêu cầu hệ thống
- **Java Development Kit (JDK)** phiên bản **8** hoặc cao hơn.  
- **IDE** (IntelliJ IDEA / NetBeans / Eclipse) hoặc **Terminal/Command Prompt** để biên dịch và chạy mã.  

---

### 📂 Chuẩn bị mã nguồn
- Đảm bảo bạn đã có đầy đủ các file trong package `Cờ caro (3x3)`, bao gồm:
  - `Server.java`
  - `Client.java`
  - `DatabaseManager`
  - `GameSession`)

---

## 📞 5. Liên hệ
- Email: danhphong200412@gmail.com 
- Github: https://github.com/NguyenDanhPhong270604/LTM-Game-Tic-Tac-Toe-Caro-3x3-

© 2025 AIoTLab, Faculty of Information Technology, DaiNam University. All rights reserved.
