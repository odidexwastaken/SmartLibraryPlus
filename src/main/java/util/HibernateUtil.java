package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // hibernate.cfg.xml dosyasını okur ve ayarları yükler
                sessionFactory = new Configuration().configure().buildSessionFactory();
            } catch (Exception e) {
                System.err.println("Veritabanı bağlantısı kurulamadı!");
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}