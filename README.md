# SmartLibraryPlus - Akıllı Kütüphane Sistemi

Bu proje, Nesneye Yönelik Programlama (OOP) prensipleri ve Hibernate ORM kullanılarak geliştirilmiş, konsol tabanlı bir kütüphane yönetim sistemidir.

## 👨‍💻 Öğrenci Bilgileri
* **Ad Soyad:** [BURAYA ADINI SOYADINI YAZ]
* **Öğrenci No:** [BURAYA NUMARANI YAZ]
* **Ders:** Bilgisayar Programcılığı - Final Ödevi

## 🚀 Kullanılan Teknolojiler
* **Dil:** Java (JDK 17+)
* **ORM:** Hibernate 6.4.0
* **Veritabanı:** SQLite
* **Build Tool:** Maven

## 📂 Proje Yapısı
* `src/entity`: Veritabanı tablolarına karşılık gelen sınıflar (Book, Student, Loan).
* `src/dao`: Veritabanı işlemlerini yapan katman (CRUD operasyonları).
* `src/app`: Uygulamanın çalıştığı ana konsol arayüzü.
* `src/util`: Hibernate ayar ve bağlantı sınıfı.

## ⚙️ Kurulum ve Çalıştırma
1.  Projeyi klonlayın veya indirin.
2.  Maven bağımlılıklarının yüklenmesini bekleyin.
3.  `src/app/Main.java` dosyasını çalıştırın.
4.  Veritabanı (`smartlibrary.db`) otomatik olarak oluşturulacaktır.