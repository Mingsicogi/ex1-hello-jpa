package hellojpa;

import javax.persistence.*;
import java.util.List;

public class JPAMain {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

    private static EntityManager em;

    public static void main(String[] args) {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
//        EntityManager em = emf.createEntityManager();
//        EntityTransaction tx = em.getTransaction();
//
//        tx.begin();
//        Member member = new Member();
//        member.setId(2L);
//        member.setName("minseok2");
//
//        em.persist(member);
//        tx.commit();
//
//        em.close();
//        emf.close();

        Member member = new Member();
        member.setId(3L);
        member.setName("minseok3");

        add(member); // 추가

        em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Member dbInfo = em.find(Member.class, 3L);
        dbInfo.setName("minssogi2");

        tx.commit();

        // JPQL
        List<Member> result = em.createQuery("select m from Member as m", Member.class).getResultList();
        for (Member m : result){
            System.out.println("name = " + m.getName());
        }

        em.close();
        emf.close();

        System.out.println("done");
    }

    // JPA 의 모든 데이터 변경은 트랜잭션 안에서 일어나야함.
    public static void add(Member member){
        em = emf.createEntityManager();
        EntityTransaction tx = JPAMain.em.getTransaction();
        tx.begin();

        try{
            JPAMain.em.persist(member);
            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
