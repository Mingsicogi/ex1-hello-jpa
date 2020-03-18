package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class PrimaryKeyMapping {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager manager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        transaction.begin();
        Member member = new Member();
        member.setName("minssogi");

        System.out.println("=================");
        manager.persist(member);
        System.out.println("member.id = " + member.getId());
        System.out.println("=================");
        transaction.commit();

        manager.close();
        entityManagerFactory.close();
    }
}
