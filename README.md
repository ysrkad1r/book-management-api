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

## 📌 Swagger UI
Swagger UI ile API’yi test etmek için:

![Swagger UI](https://i.imgur.com/35R568e.png)

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
### ✅ Başarılı Örnek (Tek Kayıt)
```json
{
  "status": 200,
  "payload": {
    "id": 13,
    "createTime": "2025-08-14T21:58:39.447+00:00",
    "username": "dummy",
    "password": "$2a$10$7CcOOtjzW.YApdrfP4j3UOsg0KUIltytF41mZXD4L1H5PDHpUMHs6",
    "roleType": "USER"
  },
  "errorMessage": null
}
```

✅ Başarılı Örnek (Token Oluşturma)
```
{
  "status": 200,
  "payload": {
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJVU0VSIl0sInN1YiI6ImR1bW15IiwiaWF0IjoxNzU1MjA4NzU5LCJleHAiOjE3NTUyMTU5NTl9.RcgD-_J5UE2xRM8hb88rraZ9WuWVxoMcEcBhbryAU3g",
    "refreshToken": "ff2b5d75-b5f5-4718-afaa-02206e7aadec"
  },
  "errorMessage": null
}
```

✅ Başarılı Örnek (Pageable Liste)
```
{
  "status": 200,
  "payload": {
    "content": [
      {
        "id": 6,
        "createTime": "2025-08-10T19:20:09.916+00:00",
        "title": "The cat in hat",
        "author": "Dr. Seuss",
        "description": "The Cat in the Hat is a 1957 children's book written and illustrated by American author Dr. Seuss.",
        "createdBy": {
          "id": 3,
          "createTime": "2025-08-07T18:14:34.811+00:00",
          "username": "user1",
          "password": "$2a$10$bPUXJPI.p06bU8fh1JoJku/XENF0MM/msXP/5KHSjxBGmprcRLjo6",
          "roleType": "USER"
        }
      },
      {
        "id": 7,
        "createTime": "2025-08-10T19:38:05.772+00:00",
        "title": "The Great Adventure",
        "author": "John Smith",
        "description": "A thrilling story of survival and friendship in the wilderness.",
        "createdBy": {
          "id": 3,
          "createTime": "2025-08-07T18:14:34.811+00:00",
          "username": "user2",
          "password": "$2a$10$bPUXJPI.p06bU8fh1JoJku/XENF0MM/msXP/5KHSjxBGmprcRLjo6",
          "roleType": "USER"
        }
      },
      {
        "id": 8,
        "createTime": "2025-08-10T19:38:15.290+00:00",
        "title": "Hidden Truths",
        "author": "Emily Carter",
        "description": "A mystery novel uncovering secrets buried for decades.",
        "createdBy": {
          "id": 4,
          "createTime": "2025-08-07T18:25:16.126+00:00",
          "username": "user3",
          "password": "$2a$10$vwH4h1cj5KAGZodO4lDFAe3inbqVMFFtBeJtxAvyE8r5ttKLjZfUi",
          "roleType": "ADMIN"
        }
      },
      {
        "id": 9,
        "createTime": "2025-08-10T19:38:43.467+00:00",
        "title": "Ocean's Whisper",
        "author": "Michael Johnson",
        "description": "A poetic journey across the vast and mysterious seas.",
        "createdBy": {
          "id": 5,
          "createTime": "2025-08-09T00:07:09.657+00:00",
          "username": "user4",
          "password": "$2a$10$5pgtnvWhCYvqeJpk.MMk9Ouc6PaKZaYz0EDEL86piZTbHVaAf10ey",
          "roleType": "USER"
        }
      },
      {
        "id": 10,
        "createTime": "2025-08-10T19:38:52.794+00:00",
        "title": "Code of Shadows",
        "author": "Laura Bennett",
        "description": "A cyber-thriller about hacking, betrayal, and redemption.",
        "createdBy": {
          "id": 3,
          "createTime": "2025-08-07T18:14:34.811+00:00",
          "username": "user5",
          "password": "$2a$10$bPUXJPI.p06bU8fh1JoJku/XENF0MM/msXP/5KHSjxBGmprcRLjo6",
          "roleType": "USER"
        }
      }
    ],
    "pageNumber": 0,
    "pageSize": 5,
    "totalElements": 10,
    "totalPages": 2,
    "first": true,
    "last": false,
    "hasNext": true,
    "hasPrevious": false,
    "sort": "id: ASC"
  },
  "errorMessage": null
}
```

❌ Hata Örneği
```
{
  "status": 404,
  "exception": {
    "path": "/rest/api/user/books/save-book",
    "createTime": "2025-08-14T22:01:38.670+00:00",
    "hostName": "DESKTOP-hostName",
    "message": {
      "messageType": "USERNAME_NOT_FOUND",
      "ofStatic": "14"
    }
  }
}
```

Author 
------------------------
```
Name = Abdulkadir Yaşar
Gmail = kadirysr652@gmail.com
Github = ysrkad1r
```
