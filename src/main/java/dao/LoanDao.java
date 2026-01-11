package dao;

import entity.Loan;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;
import java.util.List;

public class LoanDao {

    public void save(Loan loan) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(loan);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void update(Loan loan) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(loan);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<Loan> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Kitap ve Öğrenci bilgilerini de çekerek getirir
            return session.createQuery("from Loan l join fetch l.book join fetch l.student", Loan.class).list();
        }
    }

    // Kitap ID'sine göre henüz iade edilmemiş kaydı bulur
    public Loan getActiveLoanByBookId(int bookId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Loan where book.id = :bid and returnDate is null", Loan.class)
                    .setParameter("bid", bookId)
                    .uniqueResult();
        }
    }
}