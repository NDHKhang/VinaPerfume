# HƯỚNG DẪN CÀI ĐẶT VÀ CHẠY ỨNG DỤNG VINAPERFUME
## 1.	Cài đặt
JAVA version 17
Docker
Một ứng dụng quản lý cơ sở dữ liệu (Azure, Mysql Workbench, …).
IDE IntelliJ IDEA
## 2.	Chạy ứng dụng với IntelliJ IDEA
Mở thư mục code trong IntelliJ IDEA, có thể bấm vào chữ M và reload lại maven projects, hoặc configure cho WebApp chạy như hình: 
## 3.	Docker
Để deploy các service và mysql lên docker chạy lệnh:
docker-compose up -d
Sau khi chạy thì cơ sở dữ liệu sẽ trống, có thể dùng file database.sql để import lấy dữ liệu.
## 4.	Các dịch vụ
### 4.1.	Cloudinary
https://cloudinary.com/ để truy cập và kho ảnh.

username: truongtanphat6302@gmail.com
password: Code11082022@
### 4.2.	Auth0
https://auth0.com/ để xem và quản lý users

username: truongtanphat6302@gmail.com
password: Code11082022
### 5.	Thông tin về ứng dụng
#### 5.1.	Swagger
Product-Service: http://localhost:8001/swagger-ui/index.html
Order-Service: http://localhost:8101/swagger-ui/index.html
Image-Service: http://localhost:8201/swagger-ui/index.html
#### 5.2.	API Gateway
API Gateway có cổng là http://localhost:8000/
Một ví dụ về cổng API Gateway lấy ra dữ liệu list sản phẩm: 
http://localhost:8000/api/products/product/list
#### 5.3.	Discovery Eureka
http://localhost:8761/
username: admin
password: 123456
#### 6.	Các tài khoản test
ADMIN
username: admin@gmail.com | password: M@tkhau1
USER
username: tanphat@gmail.com | password: M@tkhau1
username: test@gmail.com | password: M@tkhau1
