package app;

import dao.BookDao;
import dao.LoanDao;
import dao.StudentDao;
import entity.Book;
import entity.Loan;
import entity.Student;

import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // Türkçe karakter sorunu olmasın diye Scanner ayarı
        Scanner scanner = new Scanner(System.in);

        // DAO sınıflarımızı hazırlıyoruz
        BookDao bookDao = new BookDao();
        StudentDao studentDao = new StudentDao();
        LoanDao loanDao = new LoanDao();

        while (true) {
            System.out.println("\n=================================");
            System.out.println("=== SMART LIBRARY PLUS SİSTEMİ ===");
            System.out.println("=================================");
            System.out.println("1 - Kitap Ekle");
            System.out.println("2 - Kitapları Listele");
            System.out.println("3 - Öğrenci Ekle");
            System.out.println("4 - Öğrencileri Listele");
            System.out.println("5 - Kitap Ödünç Ver");
            System.out.println("6 - Ödünç Listesini Görüntüle");
            System.out.println("7 - Kitap Geri Teslim Al");
            System.out.println("0 - Çıkış");
            System.out.print("Seçiminiz: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Lütfen sadece sayı giriniz!");
                continue;
            }

            if (choice == 0) {
                System.out.println("Sistemden çıkılıyor... İyi günler!");
                break;
            }

            switch (choice) {
                case 1: // KİTAP EKLE
                    System.out.print("Kitap Adı: ");
                    String title = scanner.nextLine();
                    System.out.print("Yazar: ");
                    String author = scanner.nextLine();
                    System.out.print("Basım Yılı: ");
                    int year = Integer.parseInt(scanner.nextLine());

                    Book newBook = new Book(title, author, year, "AVAILABLE");
                    bookDao.save(newBook);
                    System.out.println(">>> Başarılı: Kitap sisteme eklendi.");
                    break;

                case 2: // KİTAPLARI LİSTELE
                    List<Book> books = bookDao.getAll();
                    System.out.println("\n--- KÜTÜPHANE ENVANTERİ ---");
                    if (books.isEmpty()) System.out.println("Hiç kitap yok.");
                    for (Book b : books) {
                        System.out.println(b);
                    }
                    break;

                case 3: // ÖĞRENCİ EKLE
                    System.out.print("Öğrenci Adı Soyadı: ");
                    String sName = scanner.nextLine();
                    System.out.print("Bölümü: ");
                    String dept = scanner.nextLine();

                    Student newStudent = new Student(sName, dept);
                    studentDao.save(newStudent);
                    System.out.println(">>> Başarılı: Öğrenci kaydedildi.");
                    break;

                case 4: // ÖĞRENCİLERİ LİSTELE
                    List<Student> students = studentDao.getAll();
                    System.out.println("\n--- ÖĞRENCİ LİSTESİ ---");
                    if (students.isEmpty()) System.out.println("Hiç öğrenci yok.");
                    for (Student s : students) {
                        System.out.println(s);
                    }
                    break;

                case 5: // ÖDÜNÇ VER
                    System.out.print("Öğrenci ID giriniz: ");
                    int stuId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Kitap ID giriniz: ");
                    int bookId = Integer.parseInt(scanner.nextLine());

                    Book bookToBorrow = bookDao.getById(bookId);
                    Student borrower = studentDao.getById(stuId);

                    if (bookToBorrow != null && borrower != null) {
                        if ("BORROWED".equals(bookToBorrow.getStatus())) {
                            System.out.println("HATA: Bu kitap şu an başkasında!");
                        } else {
                            String date = LocalDate.now().toString();
                            Loan loan = new Loan(date, bookToBorrow, borrower);
                            loanDao.save(loan);

                            bookToBorrow.setStatus("BORROWED");
                            bookDao.update(bookToBorrow);

                            System.out.println(">>> İşlem Tamam: Kitap öğrenciye verildi.");
                        }
                    } else {
                        System.out.println("HATA: Öğrenci veya Kitap bulunamadı! ID'leri kontrol edin.");
                    }
                    break;

                case 6: // ÖDÜNÇ LİSTESİ
                    List<Loan> loans = loanDao.getAll();
                    System.out.println("\n--- ÖDÜNÇ HAREKETLERİ ---");
                    if (loans.isEmpty()) System.out.println("Kayıt yok.");
                    for (Loan l : loans) {
                        String rDate = (l.getReturnDate() == null) ? "Teslim Edilmedi" : l.getReturnDate();
                        System.out.println("Öğrenci: " + l.getStudent().getName() +
                                " | Kitap: " + l.getBook().getTitle() +
                                " | Veriliş: " + l.getBorrowDate() +
                                " | İade: " + rDate);
                    }
                    break;

                case 7: // İADE AL
                    System.out.print("İade edilen Kitap ID: ");
                    int returnBookId = Integer.parseInt(scanner.nextLine());

                    Loan activeLoan = loanDao.getActiveLoanByBookId(returnBookId);

                    if (activeLoan != null) {
                        activeLoan.setReturnDate(LocalDate.now().toString());
                        loanDao.update(activeLoan);

                        Book b = activeLoan.getBook();
                        b.setStatus("AVAILABLE");
                        bookDao.update(b);

                        System.out.println(">>> Başarılı: Kitap iade alındı, rafa kaldırıldı.");
                    } else {
                        System.out.println("HATA: Bu kitap şu an kimsede görünmüyor!");
                    }
                    break;

                default:
                    System.out.println("Geçersiz seçim, tekrar deneyin.");
            }
        }
    }
}