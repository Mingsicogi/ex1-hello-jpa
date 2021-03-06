package hellojpa;

import javax.persistence.*;
import java.util.List;

public class JPAMain {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

    private static EntityManager em;

    public static void main(String[] args) {

        // 비영속
        Member member = new Member();
        member.setId(3L);
        member.setName("minseok3");

        add(member); // 추가

        em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Member dbInfo = em.find(Member.class, 3L);
        dbInfo.setName("minssogi2");
        em.persist(dbInfo);

        tx.commit();

        // JPQL
        List<Member> result = em.createQuery("select m from Member as m", Member.class).getResultList();
        for (Member m : result){
            System.out.println("name = " + m.getName());
        }

        System.out.println("========================================================");
        System.out.println("========================================================");


//        tx.begin();
//        em.remove(em.find(Member.class, 150L));
//        em.remove(em.find(Member.class, 160L));
//        tx.commit();


        tx.begin();
        Member member1 = new Member(150L, "min1");
        Member member2 = new Member(160L, "min2");

        // insert 할 쿼리를 모아서 한번에 쿼리를 수행함.
        em.persist(member1);
        em.persist(member2);

        System.out.println("=== persist complete ===");
        tx.commit();


        tx.begin();
        Member member3 = em.find(Member.class, 150L);
        member3.setName("ZZZZZZZZZZ");

        // 불필요한 코드.
//         em.persist(member3);
        System.out.println("--- update ---");
        tx.commit();



        // flush
        tx.begin();
        Member member4 = new Member(191L, "qweqwe");
        em.persist(member4);

        em.flush();

        System.out.println("===========");
        tx.rollback();

        // 준영속상태
        tx.begin();
        em.detach(member3);
        Member member5 = em.find(Member.class, 150L);

        em.clear();
        Member member6 = em.find(Member.class, 150L);

        em.detach(member6);
        Member member7 = em.find(Member.class, 150L);
        tx.commit();

        em.close();
        emf.close();

        System.out.println("done");
    }

    // JPA 의 모든 데이터 변경은 트랜잭션 안에서 일어나야함.
    private static void add(Member member){
        em = emf.createEntityManager();
        EntityTransaction tx = JPAMain.em.getTransaction();
        tx.begin();

        try{
            // 영속 상태. DB 에 저장된 상태가 아님.
            JPAMain.em.persist(member);
//            em.detach(member); // 영속 상태 해제.

            // 1차 캐쉬 상태이기 때문에 DB select를 하지 않음.
            Member dbInfo = em.find(Member.class, member.getId());
            System.out.println("name : " + dbInfo.getName());
            Member member1 = em.find(Member.class, member.getId());

            System.out.println("[dbInfo == member1] result : " + (dbInfo == member1));


            tx.commit(); // DB에 저장완료
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
