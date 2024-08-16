package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpaMain {
    //psvm을 통해 바로 생성 가능
    public static void main(String[] args) {
        //동작 확인
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("hello");
        //unitName>><persistence-unit name="hello"> 여기 name을 생성
        //이렇게 엔티티매니져 팩토리를 반환하게 된다.
        //그리고 entityManagerFactory는 App로딩 시점에 딱 하나만 만들어야한다.
        //그리고 실제 DB에 저장하거나 트랜잭션 단위에 대해서 DB커넥션을 얻어서 쿼리를 날릴 때
        //엔티티 메니져를 꼭 만들어줘야한다.
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //이후 엔티티 매니져를 가져와서 이걸 통해 쿼리 생성 가능

        //DB커넥션을 생성해서 트랜잭션을 호출해야 된다.
        EntityTransaction transaction = entityManager.getTransaction();
        //트랜잭션 시작
        transaction.begin();

        try {
            //맴버 생성
//        Member member =new Member();
//        //맴버 ID가 없으면 저장 안됨
//        //이렇게 되면 구버전에서는 아이디가 없다고 나오지만 현재 버전에서는
//        //트렌잭션이 활성화된 상태가 아니라 에러도 안나오고 트랜잭션을 활성화하고
//        //커밋단계까지 가면 id값을 Long타입은 그냥 0으로 초기화 해버리고 저장한다.
//        member.setId(1L);
//        member.setName("helloA");
////        그리고 이렇게 해도 저장이 안되는 이유는 위와 마찬가지로 트랜잭션을 활성화해야
////        저장이 된다
////        jpa는 트랜잭션 내부에서 작업을 해야된다.
//        //맴버 저장
//        System.out.println(" 맴버 저장 시작");
//        entityManager.persist(member);
//        System.out.println(" 맴버 저장 완료 ");

        //맴버 단건 조회
            //데이터 수정을 한다면? 엔티티를 자바 컬렉션처럼 이해하면 편하다.
            Member findMember = entityManager.find(Member.class, 1L);
            System.out.println("findMember.getId = " + findMember.getId());
            System.out.println("findMember.getName() = " + findMember.getName());

        //맴버 삭제
//            entityManager.remove(findMember);
//            //찾은 객체를 리무브 함수에 넣으면 엔티티 객체를 삭제하면서 DB의 데이터도 삭제

            //맴버 수정
//            findMember.setName("helloJp");
            //수정은 별도의 퍼시스트르 저장하지 않아도 데이터를 변경하면 트랜잭션을 끝낼때
            //변경된 사항을 감지하여 그 변화에 대해 update문으로 넣어준다.
            //jpa를 통해 엔티티를 가져오면 이 변경 유무를 트랜잭션 커밋시점에서 확인 후
            //변경이 됬다면 업데이트

            //만약 전체 회원을 조회하고 싶다면?
//            List<Member> resultList =
//                    entityManager.
//                            createQuery("select m from Member as m", Member.class).getResultList();
//            //이렇게 쿼리를 사용해서 쿼리문을 통해 데이터를 가져오는데
            //하지만 이때 기존 쿼리랑 좀 다른데 JPA입장에서는 테이블을 대상으로 코드를 짜지 않는다.
            //Member객체를 대상으로 쿼리를 만들기 때문에 맴버 객체를 전부 가져오도록 하고'
            //그 결과를 출력을 해보면
//            for(Member member:resultList){
//                System.out.println("member.getName() = " + member.getName());
//            }
            //
//            select
//        m
//    from
//        Member as m  select
//            m1_0.id,
//                    m1_0.name
//            from
//            Member m1_0
//            member.getName() = helloJp
//        위처럼 SQL이 나가는데 select하고 필드를 전부 나열해놨다.
//            맴버 엔티티를 선택하고 SQL을 알면 JPQL을 금방 작성하고
            //예시로 페이징을 하고 싶을 때
            List<Member> resultList =
                    entityManager.
                            createQuery
                                    ("select m from Member as m", Member.class)
                            .setFirstResult(1)//1부터 10개를 가져오라는 코드로
                            .setMaxResults(10)//페이지 네이션을 쉽게 할 수 있다.
                            .getResultList();
            //이렇게 쿼리를 사용해서 쿼리문을 통해 데이터를 가져오는데
            for(Member member:resultList){
                System.out.println("member.getName() = " + member.getName());
            }
//            select
//            m1_0.id,
//                    m1_0.name
//            from
//            Member m1_0
//            offset
//                    ? rows
//            fetch
//            first ? rows only\
            //위처럼 오프셋이 자동으로 반영된다.
            //오라클로 변경하면 DiaLect를 오라클로 변경하면 로우넘같은 쿼리로 자동 변경된다.
//            또한 기본적으로 지원하는 쿼리는 다 적용이 된다.
//            이런 쿼리를 많이 사용하게 되는데 데이터를 최대한 필터링을 해야되고 테이블에서 데이터를 가져오는 것이 아니라 엔티티 객체를 대상으로 쿼리를 짤 수 있는 문법이 생기게 된 것
//
//            모든 DB데이터를 객체로 변환해서 검색하는 것은 불가능 결국
//
//            애플리케이션에서 필요한 데이터만 DB에서 불러오려면 결국 검색 조건이 포함된 sQL이 필요하다.
//
//                    실제 RDB를 대상으로 쿼리를 만들면 DB에 종속이 되기 때문에 그게 아니라 엔티티 객체를 대상으로 쿼리를 할 수 있는 JPQL이 제공되는 것

            //트랜잭션을 커밋해야 데이터가 저장된다.
        transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }finally {
            //메니져와 팩토리 닫기
            entityManager.close();
            //엔티티메니져가 내부적으로 데이터베이스 커넥션을 물고 동작하기 때문에 성능을 위해서는
            //호출 후 닫아줘야 한다.
        }
        entityManagerFactory.close();


    }
}
