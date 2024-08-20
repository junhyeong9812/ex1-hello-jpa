package hellojpa;

import jakarta.persistence.*;

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
//            Member findMember = entityManager.find(Member.class, 1L);
//            System.out.println("findMember.getId = " + findMember.getId());
//            System.out.println("findMember.getName() = " + findMember.getName());

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
//            List<Member> resultList =
//                    entityManager.
//                            createQuery
//                                    ("select m from Member as m", Member.class)
//                            .setFirstResult(1)//1부터 10개를 가져오라는 코드로
//                            .setMaxResults(10)//페이지 네이션을 쉽게 할 수 있다.
//                            .getResultList();
//            //이렇게 쿼리를 사용해서 쿼리문을 통해 데이터를 가져오는데
//            for(Member member:resultList){
//                System.out.println("member.getName() = " + member.getName());
//            }
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

            //member는 비영속 상태
//            Member member=new Member();
//            member.setId(14L);
//            member.setName("HelloJPA");
//
//            //member는 영속 상태
//            System.out.println("==BEFORE==");
//            entityManager.persist(member);
//            System.out.println("==AFTER==");
            //이때는 DB에 저장되지 않는다.
            //단순히 위 상황에서는 쿼리가 안들어간다.

            //같은 컨텍스트에서 조회를 해보면
//            Member findMember = entityManager.find(Member.class, 12L);
//            System.out.println("findMember = " + findMember.getId());
//            System.out.println("findMember.getName() = " + findMember.getName());
//            //조회용 sql이 나가는 지가 중요하다.
//
//            //이후
//            Member findMember1 = entityManager.find(Member.class, 12L);
//            System.out.println("findMember1 = " + findMember1.getId());
//            Member findMember2 = entityManager.find(Member.class, 12L);
//            System.out.println("findMember2 = " + findMember2.getId());
//            System.out.println("(findMember2==findMember1) = " + (findMember2==findMember1));

            //쓰기 지연 기능 확인
//            Member member1=new Member(150L,"A");
//            Member member2=new Member(160L,"B");
//            entityManager.persist(member1);
//            entityManager.persist(member2);
            //이 순간 DB에 저장되는 것이 아니라 영속성 컨텍스트에 쓰기 지연 SQL저장소에
            //저장되는 것
            //그리고 아래 commit을 하는 순간 쿼리가 날라가기 때문에
            System.out.println("-----------------------------");
            //위 선을 통해 전인 지 후인지 확인
            //이렇게 하면 데이터베이스에 이 쿼리문을 한번에 보낼 수 있는데
            //jdbc패치라 이야기 하는데 하이버네이트같은 경우
            //batchSize라는 기능을 통해 이 사이즈만큼 모아서 데이터베이스에 한번에 쿼리를
            //보내고 커밋을 해버린다.
            //쉽게 말해서 버퍼링 같은 것
            //데이터를 보았다가 DB에 한번에 보내는 것이다.
            //중요한 것은 이런 이점을 활용할 수 있다. 그래서 JPA를 써서 성능적인 면에서
            //오히려 옵션을 통해 성능을 먹고 들어갈 수 있다.
            //마이바티스나 이런 것은 커밋직전에 한번에 넣는 게 힘들다.

            //변경 감지
            //더치체크
//            Member member = entityManager.find(Member.class, 150L);
//            System.out.println("----------------------");
//            member.setName("AA");
            //JPA는 자바 컬랙션처럼 다루기 위한 것인데
            //자바도 값을 꺼내서 변경하면 그대로 변경이 이뤄진 상태가 된다.
            //그렇기 때문에 수정할 때 퍼시스트로 하는게 애초에 객체 지향 코드와 맞지 않는다.
//            JPA는 변경 감지를 통해 엔티티를 변경할 수 있도록 기능이 제공이 된다.

            //가장 중요한 점은 엔티티를 변경할 때는 변경감지를 활용하고 persist나 머지를
            //사용하지 않는게 중요하다.

            //예제로
//            if(member.getName().equals("AA")){
//                entityManager.persist(member);
//            }
            //이렇게 코드를 작성한다고 했을 때 내 의도는 맴버가 변경된 경우만
            //업데이트 쿼리를 날릴꺼야 그게 아닌 경우 안날릴꺼야
            //이때 이런 조건을 넣지 않아도 쿼리가 날라간다.

            //Flush활용
//            Member member = new Member(200L,"member200");
//            entityManager.persist(member);
//            entityManager.flush();
//            //이렇게 flush를 통해 commit 이전에 미리 쿼리를 확인할 수 있다.
//            //그러면 flush매커니즘이 즉시 일어난다.
//            System.out.println("-------commit-------------");
//          플러시는 영속성 컨텍스트를 비우지 않는다.
//            영속성 컨텍스트의 변경내용을 데이터베이스에 동기화한다.
//            트랜잭션이라는 작업 단위가 중요-> 커밋 직전에만 동기화하면 된다.
//            이 플러시가 동작할 수 있는 이유는 트랜잭션이라는 작업 단위가 존재하기 때문이다.
//            JPA에서 업데이트를 날리고 하는게 가장 중요한게 커밋 직전에만 쿼리를 날리면 되기
//                    때문에 이런 메커니즘이 중요
//                    동시성 같은 것을 전 부 트랜잭션에 위임해서 사용

            //준영속 상태
//            영속->준영속
            //영소게서 준영속으로 갈 수 있는데 이때 em.persist를 하면 그 데이터는
            //영속 상태가 되며 em.find를 통해 데이터를 가져올 경우에도 이 데이터가
            //영속성 컨텍스트에 존재하지 않는다면 DB에서 그 데이터를 조회해서 1차캐시에 올린다.

            //준영속 상태란 영속 상태의 엔티티가 영속성 컨텍스트에서 분리된 상태이다.
            //한마디로 1차 캐시에서 없애는 것
            //이 상태면 더티체크가 안된다.
            //만약 데이터를 준영속 상태로 변경하고 싶다면
//            entityManager.detach("entity명");
//            >>특정 엔티티만 준영속 상태로 전환
//              entityManager.clear();
//              >>영속성 컨텍스트를 완전히 초기화/내부를 전부 초기화시키는 것
//            Member member = entityManager.find(Member.class, 150L);
            //다시 셀렉트해서 셀렉트가 두번 일어난다.
            //1차캐시와 관계없이 테스트 케이스를 작성하거나 할 때 눈으로 확인할 때 clear를 쓰면
            //유용하다.
//                entityManager.close();
//                >>영속성 컨텍스트를 종료
            //이렇게 닫아버리면 더이상 관리가 안되기 때문에 데이터가 업데이트 되지 않는다.
            //위와 같은 케이스 일 경우 준영속 상태로 데이터의 상태가 변경된다.
            //예시
//            Member member = entityManager.find(Member.class, 150L);
            System.out.println("----------------------");
//            member.setName("AAA");
            //150을 조회해서 이름을 변경하는데
            //민약 여기서 member가 영속 상태이지만
//            entityManager.detach(member);
            //이렇게 member데이터를 변경하고 준영속 상태로 전환하게 된다면
            //셀렉트 쿼리만 나오는 것을 볼 수 있다.
            //결국 영속에서 관리하지 않아서 더치체크가 안되는 것을 알 수 있다.
            //직접 쓸 일은 없지만 웹에서 복잡해지면
            //왜 쓰이는 지 알 수 있다.

//            EnumType.ORDINAL일 경우
//            Member member=new Member();
//            member.setId(1L);
//            member.setUsername("AA");
//            member.setRoleType(RoleType.USER);
//            entityManager.persist(member);

            //기본키 매핑
//            Member member=new Member();
////            member.setId("ID_A");
////            GenerationType.AUTO를 사용한다면 setId는 하면 안된다.
//            member.setUsername("AA");
//            member.setRoleType(RoleType.USER);
//
//            System.out.println(" ==============w===== ");
//            entityManager.persist(member);
//            //PK값만 얻어서 영속성 컨텍스트에 넣어두고 이후 커밋하는 시점에 인서트가 되는 것
//            //시퀀스 방식은 버퍼링이 가능하다.
//            //네트워크를
//            System.out.println("member.getId() = " + member.getId());
//            System.out.println("====================");

            //allocationSize확인
            Member member1 =new Member();
            member1.setUsername("A");
            Member member2 =new Member();
            member2.setUsername("B");
            Member member3 =new Member();
            member3.setUsername("C");
            System.out.println("================");
            //Select가 두번 호출되는데 이떄
            //DB SEQ=1이되고 다음 호출에서 50개씩 써야되는데 처음 호출된게 0이 아닌 1이기 때문에
            //다시 호출해보는 것
            //DB SEQ=51이 된다.
            //다시 호출해서 50개가 맞으면 
//            결국 아래와 같이 되는 것
            //DB SEQ=1 |    1
            //DB SEQ=51|    2
            //DB SEQ=51|    3
            //결국 이렇게 동작하게 되는 것
            entityManager.persist(member1);//이 시점에 최적화 51개로 맞추고 이후에 더미로호출
            entityManager.persist(member2);//MEM
            entityManager.persist(member3);//MEM
            //이후 두개는 메모리에서 데이터를 호출하는 것

            System.out.println("member1 = " + member1.getId());
            System.out.println("member2 = " + member2.getId());
            System.out.println("member3 = " + member3.getId());
            System.out.println("================");
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
