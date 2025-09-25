<h2 align="center">
    <a href="https://dainam.edu.vn/vi/khoa-cong-nghe-thong-tin">
    🎓 Faculty of Information Technology (DaiNam University)
    </a>
</h2>

<h2 align="center">
   GAME TIC TAC TOE (CARO 3x3)
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
Ứng dụng Game Caro 3x3 sử dụng giao thức TCP cho phép nhiều người chơi thách đấu và thi đấu với nhau qua mạng.<br>
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

 Các chức năng chính:
### **Server**
🔌 **Kết nối & Quản lý** – Port `8000`, hỗ trợ đa luồng  
🎯 **Quản lý trận đấu** – Logic game Caro 3x3  
📊 **Theo dõi thống kê** – Thắng/thua của người chơi  
💾 **Lưu trữ lịch sử** – File `player_history.txt`  
👥 **Quản lý Client** – Danh sách người chơi online  

### **Client**
🔗 **Kết nối đến Server** – Giao tiếp qua TCP  
🎨 **Giao diện đồ họa** – Java Swing  
⚡ **Thách đấu real-time** – Chọn người chơi online  
🎮 **Chơi game Caro** – X màu xanh, O màu đỏ  
📊 **Xem lịch sử** – Thống kê người chơi  
🔄 **Làm mới** – Reset bàn cờ và trạng thái  

### **Hệ Thống**
🌐 **Giao thức TCP** – `ServerSocket` và `Socket`, đa luồng  
💾 **Lưu trữ dữ liệu** – File I/O cho lịch sử người chơi  
🛡️ **Xử lý Lỗi** – Thông báo lỗi trong GUI, debug log  
🏆 **Logic game** – Kiểm tra thắng thua 3x3  

### 🎲 **Luật Chơi**
🟩 **Bàn cờ:** 3x3 (9 ô)  
🏆 **Thắng:** khi có 3 quân liên tiếp theo **hàng**, **cột** hoặc **chéo**  
🤝 **Hòa:** khi bàn cờ đã đầy mà **không có ai thắng**  
🎨 **Ký hiệu:**  
  - ❌ X = màu xanh  
  - ⭕ O = màu đỏ   
    
📌 **Ví dụ bàn cờ thắng:**

        ❌ ⬜ ⭕
        ⬜ ❌ ⭕
        ⬜ ⬜ ❌
---

## 🔧 2. Công nghệ sử dụng
<p align="center">
  <a href="https://www.java.com/">
    <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" />
  </a>
  <a href="https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html">
    <img src="https://img.shields.io/badge/JDK-8+-green?style=for-the-badge" />
  </a>
  <a href="https://docs.oracle.com/javase/tutorial/networking/sockets/index.html">
    <img src="https://img.shields.io/badge/TCP-Socket-blue?style=for-the-badge" />
  </a>
  <a href="https://docs.oracle.com/javase/tutorial/uiswing/">
    <img src="https://img.shields.io/badge/Java-Swing-orange?style=for-the-badge" />
  </a>
</p>

## 🚀 3. Hình ảnh các chức năng

<p align="center">
  <img src="docs/anh1.jpg" alt="Ảnh 1" width="600"/>
</p>

<p align="center">
  <em>Hình 1: Giao diện đăng nhập </em>
</p>

<p align="center">
  <img src="docs/anhdangky.jpg" alt="Ảnh 1" width="600"/>
</p>

<p align="center">
  <em>Hình 2: Giao diện đăng ký </em>
</p>

<p align="center">
  <img src="docs/anh2.jpg" alt="Ảnh 1" width="600"/>
</p>

<p align="center">
  <em>Hình 3: Giao diện Game cờ caro(3x3) </em>
</p>

<p align="center">
  <img src="docs/anhonline.jpg" alt="Ảnh 1" width="600"/>
</p>

<p align="center">
  <em>Hình 4: Giao diện người chơi online </em>
</p>

<p align="center">
  <img src="docs/gd2nguoichs.jpg" alt="Ảnh 2" width="600"/>
</p>
<p align="center">
  <em> Hình 5: Giao diện 2 người chơi với nhau</em>
</p>


<p align="center">
  <img src="docs/banthang.jpg" alt="Ảnh 3" width="600"/>
 
</p>
<p align="center">
  <em> Hình 3: Giao diện bạn thắng </em>
</p>

<p align="center">
    <img src="docs/banthua.jpg" alt="Ảnh 4" width="600"/>
</p>
<p align="center">
  <em> Hình 4: Giao diện bạn thua</em>
</p>

<p align="center">
  <img src="docs/bxh.jpg" alt="Ảnh 5" width="600"/>
</p>
<p align="center">
  <em> Hình 5: Bảng xếp hạng TOP</em>
</p>

---

## ⚙️ 4. Các bước cài đặt & Chạy ứng dụng 

### 🛠️ 4.1. Yêu cầu hệ thống
- **Java Development Kit (JDK)**: Phiên bản 8 trở lên
- **Hệ điều hành**: Windows, macOS, hoặc Linux
- **Môi trường phát triển**: IDE (IntelliJ IDEA, Eclipse)
- **Bộ nhớ**: Tối thiểu 512GB và 16GB RAM
  
### 📥 4.2. Các bước cài đặt
#### 🧰 Bước 1: Chuẩn bị môi trường
1. **Cài đặt Java**  
   Dự án yêu cầu **JDK 8** trở lên (JDK 21 cũng chạy được).  
   Kiểm tra bằng:
   ```bash
   java -version
   javac -version
Đảm bảo cả hai lệnh hiển thị phiên bản >= 8. <br>
2. **Cấu trúc thư mục dự án** <br>
```bash
        BTLTicTacToe/<br>
    └── src/ <br>
         ├── client/        # Code giao diện và logic phía client 
         ├── server/        # Code xử lý server & quản lý kết nối 
         └── shared/        # Các class dùng chung giữa client & server 
```
   
#### 🏗 Bước 2: Biên dịch mã nguồn
1. Mở terminal và điều hướng đến thư mục dự án: <br>
```bash
   cd D:\Download\BTLTicTacToe>
```
2. Biên dịch tất cả file Java <br>
```bash
   javac *.java
```

#### ▶️ Bước 3: Chạy ứng dụng 
**Khởi động Server:**
```bash
java -cp bin server.CaroServer
```
- Server sẽ khởi động trên port mặc định (8000) bạn có thể thay đổi.
- Giao diện server sẽ hiển thị và sẵn sàng nhận kết nối từ client.

**Khởi động Server:**
```bash
java -cp bin client.CaroClient
```
- Mở terminal mới cho mỗi client muốn tham gia
- Nhập tên người dùng khi được yêu cầu đăng nhập (ví dụ: Phóng, Trường, Long)
- Client sẽ kết nối đến server và hiển thị giao diện Cờ Caro (3x3)
  
### 🚀 Cách Chơi
1. Đăng nhập: Nhập tên người chơi khi mở client.
2. Xem danh sách online: Chọn người chơi khác và bấm nút Thách Đấu.
### 🎮 Chơi game:
- 🟩 **<span style="color:green; font-weight:700;">X</span>** = Xanh lá (người chơi 1)
- 🟥 **<span style="color:red; font-weight:700;">O</span>** = Đỏ (người chơi 2)
- 🏆 **Thắng:** Khi có 3 ô liên tiếp hàng / cột / chéo
- 🤝 **Hòa:** Khi bàn cờ đầy mà không ai thắng
4. Lịch sử người chơi: Mở cửa sổ thống kê để xem số trận thắng/thua.
5. Kết thúc: Đóng cửa sổ để thoát.
## 📞 5. Liên hệ
<img src="https://cdn-icons-png.flaticon.com/512/1946/1946429.png" width="20"/> **Họ và tên**: Nguyễn Danh Phóng  
<img src="https://cdn-icons-png.flaticon.com/512/732/732200.png" width="20"/> **Email**: [danhphong200412@gmail.com](mailto:danhphong200412@gmail.com)  
<img src="https://cdn-icons-png.flaticon.com/512/25/25231.png" width="20"/> **GitHub**: [NguyenDanhPhong270604](https://github.com/NguyenDanhPhong270604/LTM-Game-Tic-Tac-Toe-Caro-3x3-)

© 2025 AIoTLab, Faculty of Information Technology, DaiNam University. All rights reserved.
