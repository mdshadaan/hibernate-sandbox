package org.shadaan;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.shadaan.entities.Book;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.cfg.AvailableSettings.*;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration()
                .addAnnotatedClass(Book.class)
                .setProperty(DRIVER,"org.h2.Driver")
                .setProperty(URL, "jdbc:h2:mem:db1")
                .setProperty(USER, "sa")
                .setProperty(PASS, "")
                .setProperty(SHOW_SQL, true)
                .setProperty(FORMAT_SQL, true)
                .setProperty(HIGHLIGHT_SQL, true)
                .setProperty(HBM2DDL_AUTO,"create")
                .buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        List<Book> books = createBooks();
        for(Book book:books)
            session.save(book);
        Book book = session.get(Book.class,1);
        System.out.println(book);
        executeCriteriaQuery(session);
        session.getTransaction().commit();
        session.close();
    }

    public static List<Book> createBooks(){
        Book b1 = new Book(1, "The Hobbit", "J.R.R Tolkien", 350);
        Book b2 = new Book(2, "Infinite Jest", "David Foster Wallace", 1200);
        List<Book> books = new ArrayList<>();
        books.add(b1); books.add(b2);
        return books;
    }

    public static void executeCriteriaQuery(Session session){
        //select * from book where author like '%David%' or 'Tolkien' and numberOfPages between 250 and 1000;
        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        JpaCriteriaQuery<Book> criteria = builder.createQuery(Book.class);
        Root<Book> root = criteria.from(Book.class);
        criteria.select(root);
        //Like predicates
        Predicate likeDavid = builder.like(root.get("author"),"%David%");
        Predicate likeTolkien = builder.like(root.get("author"),"%Tolkien%");
        //author predicate
        Predicate authorPredicate = builder.or(likeDavid,likeTolkien);
        //between predicate
        Predicate betweenClause =  builder.between(root.get("numberOfPages"),250,1000);

        criteria.where(authorPredicate,betweenClause);

        List<Book> result = session.createQuery(criteria).getResultList();
        System.out.println(result);
    }
}