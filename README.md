# book-management-api
# 📚 Book Comment System

Spring Boot ile geliştirilmiş, kullanıcıların kitap ekleyip yorum yapabileceği, JWT tabanlı kimlik doğrulama ve rol yönetimi içeren **RESTful API** projesi.  
Proje, **admin-user rol hiyerarşisi**, **pagination**, **custom exception handling** , **Swagger UI** , **Authentication ve **refresh_token destegi ile zenginleştirilmiştir.

---

## 🚀 Özellikler

- **JWT Authentication & Authorization**
  - Kullanıcı kayıt & giriş sistemi
  - Access token ve refresh token mekanizması
  - Token içerisinde kullanıcı rolleri (`roles` claim)
- **Role Hierarchy**
  - `ADMIN > USER` yetki zinciri
- **Pagination & Sorting**
  - Kitap ve yorum listelerinde sayfalama desteği
- **Custom Exception Handling**
  - Global exception handler ile standart hata formatı
- **Swagger UI Entegrasyonu**
  - API endpointlerini kolayca test etme
- **.env ile Config Yönetimi**
  - DB, JWT secret gibi hassas veriler environment variable olarak yönetilir
- **JPA & Hibernate**
  - PostgreSQL üzerinde tablo yönetimi
- **Entity-DTO Mapping**
  - `BeanUtils` ile DTO dönüşümleri

---

## 🛠 Kullanılan Teknolojiler

- **Java 21**
- **Spring Boot 3.x**
  - Spring Web
  - Spring Security
  - Spring Data JPA
- **PostgreSQL**
- **Lombok**
- **Swagger / OpenAPI**
- **JJWT (Java JWT)**
- **Maven**
- **HikariCP Connection Pool**

---

## 📂 Proje Yapısı
```
src
├─ main
│ ├─ java/com/kadiryasar
│ │ ├─ config # Güvenlik, JWT, RoleHierarchy, Properties config
│ │ ├─ controller # REST Controller sınıfları
│ │ ├─ dto # Data Transfer Object'ler
│ │ ├─ entity # JPA Entity sınıfları
│ │ ├─ exception # BaseException, ErrorMessage
│ │ ├─ handler # GlobalExceptionHandler ve API error response yapısı
│ │ ├─ jwt # JWT token üretim/doğrulama
│ │ ├─ repository # JPA Repository arayüzleri
│ │ ├─ service # Business logic katmanı
│ │ └─ starter # Main application starter
│ └─ resources
│ ├─ application.properties
│ 
└─ test # Unit testler
```

## ⚙️ Kurulum ve Çalıştırma

1️⃣ **Projeyi klonla**
```bash
git clone https://github.com/<kullanici-adi>/book-comment-system.git
cd book-comment-system

2️⃣ .env dosyasını oluştur
DB_URL=jdbc:postgresql://localhost:5432/postgres
DB_USERNAME=postgres
DB_PASSWORD=1234
JWT_SECRET=your-secret-key

3️⃣ application.properties içinde .env değişkenlerini kullan
spring.application.name=bookCommentSystem
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
jwt.secret-key=${JWT_SECRET}

4️⃣ Maven ile bağımlılıkları yükle
mvn clean install

5️⃣ Projeyi çalıştır
mvn spring-boot:run
```

📜 API Dökümantasyonu
```
Projeyi çalıştırdıktan sonra Swagger UI’a buradan erişebilirsin:
🔗 http://localhost:8080/swagger-ui/index.html

Örnek Endpointler:
POST /api/auth/register → Yeni kullanıcı kaydı
POST /api/auth/login → Giriş yap ve token al
GET /api/books?page=0&size=5 → Sayfalı kitap listesi
POST /api/books → Kitap ekle (Yetki: USER veya ADMIN)
DELETE /api/books/{id} → Kitap sil (Yetki: ADMIN)
```

🔐 Role Hierarchy
```
Rol	    Yetkiler
------------------------------------------------------
ADMIN	|  Tüm USER yetkilerine ek olarak CRUD işlemleri
USER	|  Yorum ekleme, kitap listeleme
```

🔑 Authentication Yapısı
```
Access Token
Kısa ömürlü (2 saat)
Her API isteğinde Authorization: Bearer <token> başlığı ile gönderilir.

Refresh Token
Daha uzun ömürlü (4 saat)
Veritabanındaki refresh_token tablosunda saklanır.

Access Token süresi dolduğunda /refreshToken ile yeni Access Token üretilir.
```

Author 
------------------------
```
Name = Abdulkadir Yaşar
Gmail = kadirysr652@gmail.com
Github = ysrkad1r
```
