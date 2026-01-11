# SmartLibraryPlus - Akıllı Kütüphane Sistemi

Bu proje, Nesneye Yönelik Programlama (OOP) prensipleri ve Hibernate ORM kullanılarak geliştirilmiş, konsol tabanlı bir kütüphane yönetim sistemidir.

## Öğrenci Bilgileri
* **Ad Soyad:** Emir Ecrin MALKOÇ
* **Öğrenci No:** 20240108044
* **Ders:** Bilgisayar Programcılığı - Final Ödevi

## Kullanılan Teknolojiler
* **Dil:** Java (JDK 17+)
* **ORM:** Hibernate 6.4.0
* **Veritabanı:** SQLite
* **Build Tool:** Maven

## Proje Yapısı
* `src/entity`: Veritabanı tablolarına karşılık gelen sınıflar (Book, Student, Loan).
* `src/dao`: Veritabanı işlemlerini yapan katman (CRUD operasyonları).
* `src/app`: Uygulamanın çalıştığı ana konsol arayüzü.
* `src/util`: Hibernate ayar ve bağlantı sınıfı.

## Kurulum ve Çalıştırma
1.  Projeyi klonlayın veya indirin.
2.  Maven bağımlılıklarının yüklenmesini bekleyin.
3.  `src/app/Main.java` dosyasını çalıştırın.
4.  Veritabanı (`smartlibrary.db`) otomatik olarak oluşturulacaktır.

## Önemli Geliştirme Notu

Proje geliştirilirken ödev metnindeki **Book - Loan** arasındaki `OneToOne` ilişki isteği test edildiğinde mantıksal bir kısıtlama fark edilmiştir. 

Eğer ilişki `OneToOne` kurulursa, bir kitap ödünç verilip iade edilse bile veritabanında `Loan` tablosunda kaydı kaldığı için (Unique Constraint), aynı kitap **ikinci kez başka bir öğrenciye verilememektedir.**

Gerçek bir kütüphane senaryosunda bir kitap tarihçesi boyunca defalarca ödünç verilebileceği için, uygulamanın sürdürülebilirliği adına bu ilişki **`ManyToOne`** (Bir kitap, ödünç tablosunda tarihsel olarak birden fazla kez yer alabilir) şeklinde güncellenmiştir.

Bu sayede:
1. Kitap iade edildiğinde geçmiş kayıt silinmez (Tarihçe korunur).
2. Aynı kitap tekrar tekrar ödünç verilebilir.
