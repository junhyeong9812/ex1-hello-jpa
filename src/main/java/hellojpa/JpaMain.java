package hellojpa;

import jakarta.persistence.*;

import java.util.List;
//연관관계 매핑
public class JpaMain {
    //psvm을 통해 바로 생성 가능
    public static void main(String[] args) {
        //동작 확인
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //이후 엔티티 매니져를 가져와서 이걸 통해 쿼리 생성 가능

        //DB커넥션을 생성해서 트랜잭션을 호출해야 된다.
        EntityTransaction transaction = entityManager.getTransaction();
        //트랜잭션 시작
        transaction.begin();

        try {
//            Hibernate:
//            create table Member (
//                    TEAM_ID bigint,
//                    id bigint not null,
//                    USERNAME varchar(255),
//                    primary key (id)
//    )
//            Hibernate:
//            create table Team (
//                    TEAM_ID bigint not null,
//                    name varchar(255),
//                    primary key (TEAM_ID)
//    )
            //만약 위와 같이 RDB에 맞춰 모델링을 하게 된다면
//            Team team =new Team();
//            team.setName("TeamA");
//            entityManager.persist(team);
            //psersist하면 id에 값이 들어가기 때문에 id값이 세팅된다.

//            Member member=new Member();
//            member.setName("member1");
//            member.setTeamId(team.getId());
            //영속상태이기 때문에 id값이 존재
//            entityManager.persist(member);
            //이처럼 객체 지향적이지 않고 id를 통해 외례키 방식으로 매핑된다.

//            Member findMember = entityManager.find(Member.class, member.getId());
//            Long findTeamId = findMember.getTeamId();
//            Team findTeam = entityManager.find(Team.class, findTeamId);
            //이처럼 계속 DB를 통해 흘러가서 직접 꺼내야 된다.
//            이렇게 구성하게 되면 객체지향스럽지 못하다.
            //가장 주용한 것은 이렇게 객체를 테이블에 맞추어 데이터 중심으로 모델링하면
            //협력!!관계를 만들 수 없다.
            //테이블은 외래키로 조인을 사용해서 연관관계 테이블을 탐색
            //객체는 참조를 사용해서 연관된 객체를 찾는다.

            //연관관계 매핑을 한다면?
//            Team team =new Team();
//            team.setName("TeamA");
//            entityManager.persist(team);
//
//            Member member=new Member();
//            member.setName("member1");
//            member.setTeam(team);
//            //이렇게 팀 자체를 넣으면 된다.
//            //이렇게 넣으면 jpa가 알아서 팀의 pk값을 꺼내서 foreign key값에 인서트할 때
//            //값을 넣어준다.
//
//            entityManager.persist(member);
//
//            entityManager.flush();
//            //flush로 쿼리를 DB로 날린 후 검색하도록 한다.
//            entityManager.clear();
//            //영속성 컨텍스트를 clear로 초기화하면 아래부터는 직접 조회 후 데이터를 가져온다.
//
//            Member findMember = entityManager.find(Member.class, member.getId());
//            Team findTeam = findMember.getTeam();
//            //이렇게 바로 Team객체를 꺼낼 수 있다.
//            //참조를 사용해서 연관관계 조회를 할 수 있다.
//            System.out.println("findTeam.getName() = " + findTeam.getName());

            //Team객체를 수정
//            Team newteam = entityManager.find(Team.class, 100);
            //이렇게 100을 가지는 팀 객체를 바로 setTeam으로 넣어주면 된다.
//            findMember.setTeam(newteam);
            //이렇게 하면 영속성 컨텍스트에서 관리하고 있기 떄문에 커밋이 될 떄 flush를 통해
            //변경감지로 수정된다. fk가 수정되는 것

            //양방향 연관관계
//            List<Member> members = findMember.getTeam().getMembers();
//            //이렇게
//            for (Member m :members){
//                System.out.println("m = " + m.getName());
//            }

            //양방향 매핑시 가장 많이 하는 실수
//            Member member=new Member();
//            member.setName("member1");
//            entityManager.persist(member);
//
//            Team team =new Team();
//            team.setName("TeamA");
//            team.getMembers().add(member);
            //팀의 member에 add를 통해 member를 넣었다.
            //맴버 객체를 생성해서 객체를 넣는다면?
            //연관관계의 주인인 member에게 TEAM_ID가 들어가지 않고 null로 나온다.
            //mappedby는 인서트할때 보지 않는다.

//            Team team =new Team();
//            team.setName("TeamA");
//            entityManager.persist(team);
//
//            Member member=new Member();
//            member.setName("member1");
//            member.setTeam(team); //**
//            //이렇게 주인에 값을 넣어서 동작해보면
//            entityManager.persist(member);
////            entityManager.flush();
////            entityManager.clear();
////            team.addMember(member);
//
////            team.getMembers().add(member);
////            이렇게 해도 jpa에서 이 값을 사용하지 않는다
//            //편의 메소드가 양쪽에 다 존재하면 문제가 일어날 수도 있다.
//
//            team.getMembers().add(member); //**
//            //하지만 이렇게 이걸 넣어주면 1차 캐시에 값이 들어가서
//            //1차 캐시를 조회했을 때 아래에 member정보를 불러올 수 있게 되는 것
//            //이때 양방향 매핑을 할 때는 사실 양쪽에 다 값을 넣어주는 게 맞다.
//            Team findTeam = entityManager.find(Team.class, team.getId());
//            //만약
//            //            entityManager.flush();
////            entityManager.clear();
////            이 두개를 주석하면 1차 캐시에 정보가 제대로 없어서
//            List<Member> members =findTeam.getMembers();
//            for(Member m :members){
//                System.out.println("m.getName() = " + m.getName());
//            }
//            System.out.println("m.getName() = "+findTeam);

            ////일대다 관계에서 TEAM이 주인일 경우
//            Member member = new Member();
//            member.setName("member1");
//            entityManager.persist(member);
//
//            Team team = new Team();
//            team.setName("teamA");
            //여기 포인트가 애매해진다. 팀의 테이블에 인서트가 되면 되지만
            //팀 테이블에 인서트될 수 있는 내용이 아니다.
            //이러면 외래키가 바뀌어야 되는데 이 외래키가 맴버 테이블에 존재
//            team.getMembers().add(member);
            //그래서 Member에 업데이트를 하게 된다.
//            entityManager.persist(team);
            //이렇게 하면 맴버의 외래키가 업데이트 될 것이다.
            //팀이 저장이 되는 게 맞지만
            //이때 Member테이블에서 Update문이 나가서 TEAM_ID를 변경하는 것을 볼 수 있다.
            //이때 create one-to-many row 라고 해서 업데이트 쿼리가 나간다.
            //DB값은 정상적으로 들어왔지만 쿼리가 기존보다 많이 나가는 것을 알 수 있다/
            //이러한 업데이트 쿼리가 나가야 되는 이유는 팀 입장에서 팀을 변경할 때 이 부분은
            //팀 테이블에 넣으면 되지만 팀 엔티티를 저장하는데 
            //team.getMembers().add(member);이 부분에서 맴버에 가서 맴버에 정보를 저장할때
            //맴버 테이블에 가서 업데이트를 해야 정보를 저장할 수 있기 때문에 맴버로 가서 업데이트를
            //하는 것
            //이러한 성능상 단점이 조금 있지만
            //실질적으로 문제가 생기는 이유는 JPA를 잘 아는 사람도 기본적으로 코드 리뷰를 할 때
            //코드만 보이지 바로 그 코드에 대한 쿼리까지 생각하지 않는다.
            //그러면 비즈니스 로직을 짜다보면 팀만 손을 댓는데 쿼리를 추적했을 때 맴버까지 업데이트가
            //되어버린다. 팀엔티티를 수정했는데 맴버가 변경되어버리는 상황이 일어나고
            //실무에서 테이블이 엄청 많은데 이렇게 되면 운영이 엄청 힘들어진다.
            //그래서 다대일 단반향에서 필요 시 양방향으로 변경하는 전략을 자주 사용
            //연관관계 주인을 맴버로 가져가는 것
            //그냥 다대일 관계에서 양방향 형식으로 사용하는 게 더 좋다.

            //일대다 단방향 정리
//            1.일대다 단방향은 일대다에서 일이 연관관계의 주인
//            2. 테이블 일대다 관계는 항상 다 쪽에 외래키가 존재
//            3.객체와 테이블의 차이때문에 반대편 테이블의 외래키를 관리하는 특이한 구조
//            4.@JoinColumn을 꼭 사용해야 되며 그렇지 않으면 조인 테이블 방식을
//            사용한다.(중간에 테이블을 하나 추가함)

            //상속관계 매핑 Join 전략
            //movie를 등록한다면?
            Movie movie= new Movie();
            movie.setDirector("a");
            //만약 item에 세터 개터가 없으면 item쪽에 넣어야 하는 객체 정보는
            //없기에 item에도 세터와 게터 생성이 필요
            movie.setActor("BBBB");
            movie.setName("ABC");
            movie.setPrice(10000);
            entityManager.persist(movie);
            //싱글테이블 전략이면 쿼리가 심플하게 하나만 생성

            entityManager.flush();
            entityManager.clear();

//            Movie findMovie = entityManager.find(Movie.class, movie.getId());
//            System.out.println("findMovie = " + findMovie);

            //구현 클래스 분할 테이블 전략을 사용한 이후 부모 객체로 조회한다면?
            Item item =entityManager.find(Item.class,movie.getId());
            System.out.println("item = " + item);

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
        
//엔티티 매핑
//public class JpaMain {
//    //psvm을 통해 바로 생성 가능
//    public static void main(String[] args) {
//        //동작 확인
//        EntityManagerFactory entityManagerFactory =
//                Persistence.createEntityManagerFactory("hello");
//        //unitName>><persistence-unit name="hello"> 여기 name을 생성
//        //이렇게 엔티티매니져 팩토리를 반환하게 된다.
//        //그리고 entityManagerFactory는 App로딩 시점에 딱 하나만 만들어야한다.
//        //그리고 실제 DB에 저장하거나 트랜잭션 단위에 대해서 DB커넥션을 얻어서 쿼리를 날릴 때
//        //엔티티 메니져를 꼭 만들어줘야한다.
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        //이후 엔티티 매니져를 가져와서 이걸 통해 쿼리 생성 가능
//
//        //DB커넥션을 생성해서 트랜잭션을 호출해야 된다.
//        EntityTransaction transaction = entityManager.getTransaction();
//        //트랜잭션 시작
//        transaction.begin();
//
//        try {
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
//            System.out.println("-----------------------------");
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
//            System.out.println("----------------------");
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
//            Member member1 =new Member();
//            member1.setUsername("A");
//            Member member2 =new Member();
//            member2.setUsername("B");
//            Member member3 =new Member();
//            member3.setUsername("C");
//            System.out.println("================");
//            //Select가 두번 호출되는데 이떄
//            //DB SEQ=1이되고 다음 호출에서 50개씩 써야되는데 처음 호출된게 0이 아닌 1이기 때문에
//            //다시 호출해보는 것
//            //DB SEQ=51이 된다.
//            //다시 호출해서 50개가 맞으면
////            결국 아래와 같이 되는 것
//            //DB SEQ=1 |    1
//            //DB SEQ=51|    2
//            //DB SEQ=51|    3
//            //결국 이렇게 동작하게 되는 것
//            entityManager.persist(member1);//이 시점에 최적화 51개로 맞추고 이후에 더미로호출
//            entityManager.persist(member2);//MEM
//            entityManager.persist(member3);//MEM
//            //이후 두개는 메모리에서 데이터를 호출하는 것
//
//            System.out.println("member1 = " + member1.getId());
//            System.out.println("member2 = " + member2.getId());
//            System.out.println("member3 = " + member3.getId());
//            System.out.println("================");
//            transaction.commit();
//        }catch (Exception e){
//            transaction.rollback();
//        }finally {
//            //메니져와 팩토리 닫기
//            entityManager.close();
//            //엔티티메니져가 내부적으로 데이터베이스 커넥션을 물고 동작하기 때문에 성능을 위해서는
//            //호출 후 닫아줘야 한다.
//        }
//        entityManagerFactory.close();
//
//
//    }
//}
