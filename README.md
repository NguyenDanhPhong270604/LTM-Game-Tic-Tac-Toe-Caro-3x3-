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
Ứng dụng Game Caro 3x3 sử dụng giao thức TCP cho phép nhiều người chơi thách đấu và thi đấu với nhau qua mạng.
Server chịu trách nhiệm:<br>
**Client** : cung cấp giao diện chơi game và thách đấu.<br>
**Server** : đóng vai trò trung tâm, quản lý kết nối, trận đấu và lịch sử người chơi.<br>
**Lưu trữ dữ liệu** : lịch sử người chơi (thắng/thua) được lưu vào file văn bản. 

Client có giao diện Java Swing, cho phép người dùng:
- Đăng nhập/nhập tên người chơi.  
- Chơi cờ Caro 3x3 trực tuyến theo thời gian thực.  
- Xem thông báo khi thắng, thua hoặc hòa.  

Giao thức TCP được chọn vì tính đảm bảo truyền tin cậy:  
- Không mất gói dữ liệu (các nước đi được truyền đầy đủ, chính xác).  
- Duy trì kết nối liên tục cho đến khi trận đấu kết thúc.  

🎮 Các chức năng chính:
**Server**
🔌 Kết nối & Quản lý - Port 8000, đa luồng
🎯 Quản lý trận đấu - Logic game Caro 3x3
📊 Theo dõi thống kê - Thắng/thua của người chơi
💾 Lưu trữ lịch sử - File player_history.txt
👥 Quản lý Client - Danh sách người chơi online
**Client**
🔗 Kết nối đến Server - Giao tiếp qua TCP
🎨 Giao diện đồ họa - Java Swing
⚡ Thách đấu real-time - Chọn người chơi online
🎮 Chơi game Caro - X màu xanh, O màu đỏ
📊 Xem lịch sử - Thống kê người chơi
🔄 Làm mới - Reset bàn cờ và trạng thái
**Hệ Thống**
🌐 Giao thức TCP - ServerSocket và Socket, đa luồng
💾 Lưu trữ dữ liệu - File I/O cho lịch sử người chơi
🛡️ Xử lý Lỗi - Thông báo lỗi trong GUI, debug log
🏆 Logic game - Kiểm tra thắng thua 3x3
**Luật Chơi**
Bàn cờ 3x3
Thắng khi có 3 quân cùng hàng, cột hoặc chéo
Hòa khi bàn cờ đầy không có người thắng
---

## 🔧 2. Công nghệ sử dụng
[![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)](https://www.java.com/)
[![SQLite](https://img.shields.io/badge/SQLite-07405E?style=for-the-badge&logo=sqlite&logoColor=white)](https://www.sqlite.org/)

---

## 🚀 3. Hình ảnh các chức năng

<p align="center">
  <img src="docs/anhGiaoDien.jpg" alt="Ảnh 1" width="800"/>
</p>

<p align="center">
  <em>Hình 1: Giao diện chat giữa Client-Server </em>
</p>

<p align="center">
  <img src="docs/anhClientChatServer.jpg" alt="Ảnh 2" width="700"/>
</p>
<p align="center">
  <em> Hình 2: Client chat với Server</em>
</p>


<p align="center">
  <img src="docs/anhLichSuChatLuuTxt.jpg" alt="Ảnh 3" width="500"/>
 
</p>
<p align="center">
  <em> Hình 3: Ảnh lịch sử chat được lưu vào file txt </em>
</p>

<p align="center">
    <img src="docs/anhServerxoaDL.jpg" alt="Ảnh 4" width="450"/>
</p>
<p align="center">
  <em> Hình 4: Ảnh Server xóa dữ liệu</em>
</p>

<p align="center">
  <img src="docs/anhServerngatKetNoiClient.jpg" alt="Ảnh 5" width="300"/>
</p>
<p align="center">
  <em> Hình 7: Ảnh Server ngắt kết nối với CLient</em>
</p>

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
