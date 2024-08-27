package hellojpa;

import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
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
//            Movie movie= new Movie();
//            movie.setDirector("a");
//            //만약 item에 세터 개터가 없으면 item쪽에 넣어야 하는 객체 정보는
//            //없기에 item에도 세터와 게터 생성이 필요
//            movie.setActor("BBBB");
//            movie.setName("ABC");
//            movie.setPrice(10000);
//            entityManager.persist(movie);
//            //싱글테이블 전략이면 쿼리가 심플하게 하나만 생성
//
//            entityManager.flush();
//            entityManager.clear();
//
////            Movie findMovie = entityManager.find(Movie.class, movie.getId());
////            System.out.println("findMovie = " + findMovie);
//
//            //구현 클래스 분할 테이블 전략을 사용한 이후 부모 객체로 조회한다면?
//            Item item =entityManager.find(Item.class,movie.getId());
//            System.out.println("item = " + item);

            //MappedSuperclass
//            Member member =new Member();
//            member.setName("user1");
//            member.setCreateBy("kim");
//            member.setCreateDate(LocalDateTime.now());
//            entityManager.persist(member);
//            entityManager.flush();
//            entityManager.clear();

            //이렇게 데이터를 넣으면 create테이블에 상속한 엔티티 속성이 추가된 것을 볼 수 있다.
//            create table Member (
//                    LOCKER_ID bigint unique,
//                    MEMBER_ID bigint not null,
//                    TEAM_ID bigint,
//                    createDate timestamp(6),
//                    lastModifiedDate timestamp(6),
//                    USERNAME varchar(255),
//                    createBy varchar(255),
//                    lastmodifiedBy varchar(255),
//                    primary key (MEMBER_ID)
//    ) //이렇게 상속된 속성도 존재한다.

            //프록시란?
//            Member member = entityManager.find(Member.class,1L);
////            printMemberAndTeam(member);
//            //이렇게 팀 정보를 출력하려고 할 경우
//            //쿼리가 DB에 맴버를 찾을 때 연관된 Team도 한번에 가져오면 좋을텐데
//            printMember(member);
//            Member member=new Member();
//            member.setName("hello");
//            entityManager.persist(member);
//
//            entityManager.flush();
//            entityManager.clear();
            //영속성을 비우고

            //find
//            Member findMember = entityManager.find(Member.class, member.getId());
//            System.out.println("findMember.getId() = " + findMember.getId());
//            System.out.println("findMember.getName() = " + findMember.getName());

            //getReference
//            Member findMember = entityManager.getReference(Member.class, member.getId());
//            System.out.println("findMember = " + findMember.getClass());
            //그렇다면 셀렉트문으로 정보를 안가져오는데 findMember이 객체는 무엇인가?
            //class hellojpa.Member$HibernateProxy$boexxlZu
            //위와 같이 하이버네이트에서 생성한 프록시 클래스로 값이 존재하는 것을 확인할 수 있다.
            //레퍼런스는 맴버 객체를 주는 게 아니라 하이버네이트에서 자기 내부의 라이브러를 사용하여
            //가짜 엔티티(프록시 엔티티)를 반환해준다.
            //겉은 같은데 내부 객체들이 다 비여있는 것
            //그리고 엔티티 내부에 target이라는 객체가 존재하는데 이게 진짜 객체를 가리키게 된다.
            //프록시의 특징으로는
            //실제 클래스를 상속받아서 만들어진다.
            //실제 클래스와 겉모양은 같다. >>하이버네이트에서 내부적으로 프록시라이브러리로
            // 상속을 하는 것
            //사용하는 입장에서는 구분하지 않고 그냥 사용하면 됨
            //상속 관계는 부모타입으로 보고 쓰는 것과 같은데
//            프록시 객체의 초기화라고 하는 겟 러퍼런스를 통해 프록시 객체를 가져오면 프록시 객체를 가져와서
//            겟 네임을 호출하면 처음에 타겟에 값이 없으니 영속성 컨텍스트에 값을 요청한다.
//                    그러면 영속성이 DB를 조회해서 실제 엔티티 객체를 생성해서 준다. 그러면 타겟이라는 맴버 변수의 겟 네임과 매핑을 시키는 것 그걸 통해 실제 객체를 연결해준다.
//            그래서 getName을 했을 때 타겟의 갯네임으로 반환
            //결국 중요한 것은 프록시에 값이 없으면 영속성 컨텍스트한테 초기화 요청을 해서
            //실제 엔티티를 생성하는 것
            //실제 하이버네이트 구현체마다 이러한 메커니즘은 다르지만 보통은 위와 같다.
//            프록시 특징
//            1.프록시 객체는 처음 사용할 때 한 번만 초기화
//            2. 프록시 객체를 초기화 할 때, 프록시 객체가 실제 엔티티로 바뀌는 것은 아님, 초
//            기화되면 프록시 객체를 통해서 실제 엔티티에 접근 가능
//            3. 프록시 객체는 원본 엔티티를 상속받음, 따라서 타입 체크시 주의해야함 (== 비
//            교 실패, 대신 instance of 사용)
//            4. 영속성 컨텍스트에 찾는 엔티티가 이미 있으면 em.getReference()를 호출해
//            도 실제 엔티티 반환
//            5. 영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때, 프록시를 초기화하면
//            문제 발생
//            (하이버네이트는 org.hibernate.LazyInitializationException 예외를 터트림)


//            System.out.println("findMember.getId() = " + findMember.getId());
//            //이 레퍼런스를 찾는 시점에 아이디 값을 이미 넣었기 때문에 값이 있어서 DB에 쿼리를
//
//            System.out.println("Before_findMember = " + findMember.getClass());
//            //Before_findMember = class hellojpa.Member$HibernateProxy$revpGj5Z
////            System.out.println("findMember.getName() = " + findMember.getName());
//            //쿼리를 안하지만 Name은 DB에만 존재해서
//            //실제 이름을 확인하기 위해서 이때 DB에 쿼리를 날려서 member내부에 값을 채운다.
//            //실제 메커니즘은
//            //
//            System.out.println("After_findMember = " + findMember.getClass());
//            //이때 Before_findMember의 맴버랑 After_findMember이때의 맴버는 바뀌는 게 아니다.
            //After_findMember = class hellojpa.Member$HibernateProxy$revpGj5Z
            //이처럼 둘다 똑같은 객체이다.
            //교체되는 게 아니라 타겟에만 값이 채워져서 값을 호출할 떄 가져온다.

            //==비교가 될까?
//            Member member1=entityManager.find(Member.class, member.getId());
//            System.out.println("(member1.getClass()==findMember.getClass()) = " + (member1.getClass()==findMember.getClass()));
//            System.out.println("member1 = " + member1.getClass());
//            System.out.println("findMember = " + findMember.getClass());
            //이렇게 비교하면 확인 불가능
            //System.out.println("findMember.getName() = " + findMember.getName());
            //find도 같은 프록시를 가리키는데
//            영속성 컨텍스트는 동일한 식별자를 가지는 엔티티는 하나의 객체로 관리합니다.
//            따라서, 이미 getReference() 메서드에 의해 특정 엔티티에 대한
//            프록시 객체가 영속성 컨텍스트에 존재하고 있다면, 그 엔티티의 실제 객체를
//            find() 메서드를 통해 조회하려고 해도 영속성 컨텍스트는 새로운 객체를 반환하지
//            않고 기존의 프록시 객체를 반환합니다.

            //==비교 확인
//            Member member1=new Member();
//            member1.setName("member1");
//            entityManager.persist(member1);
//
//            Member member2=new Member();
//            member2.setName("member2");
//            entityManager.persist(member2);
//
//            entityManager.flush();
//            entityManager.clear();
//            Member m1=entityManager.find(Member.class, member1.getId());
//            Member m2=entityManager.getReference(Member.class, member2.getId());
//            System.out.println("m1 = " + m1.getClass());
//            System.out.println("m2 = " + m2.getClass());
//            System.out.println("(m1.getClass()==m2.getClass()) = " +
//                    (m1.getClass()==m2.getClass()));
//            m1 = class hellojpa.Member
//            m2 = class hellojpa.Member$HibernateProxy$eii0I4dz
            //이렇게 객체가 다르다.
//            이때 이러한 비교는 실질적으로 로직에 의해 동작할텐데
//    logic(m1,m2);
            //만약 먼저 find한 객체를 레퍼런스한다면?
//            Member m1=entityManager.find(Member.class, member1.getId());
//            Member m2=entityManager.getReference(Member.class, member1.getId());
//            System.out.println("m1 = " + m1);
//            System.out.println("m2 = " + m2);
            //이렇게 되면
            //==비교에 대해서 같은 영속성 컨텍스트 안에서 조회하면 항상 같다고 나와야 한다.
            //m1과 m2가 같다고 나오는데
            //1.이미 맴버를 영속성 컨텍스트에 올려놨기 때문에 이미올라온 데이터를 프록시로
            //가져올 필요가 없어서
            //2.jpa에서는 a==a는 항상 true가 나와야 한다.
            //결국 m1과 레퍼런스는 항상 ==이 성립해야 된다.
            //쉽게 말해서 m1과 m2는 컬렉션마냥 ==비교가 항상 한 영속성 컨텍스트에서
            //PK가 같으면 항상 true를 반환해야 된다.
            //가장 중요한 것은 같은 트랜잭션 안에서 같은 것에 대한 보장을 해줘야 하기 떄문에 이런
            //메커니즘인 것

            //준영속성 상태 관리
//            Member member1=new Member();
//            member1.setName("member1");
//            entityManager.persist(member1);
//
//            Member member2=new Member();
//            member2.setName("member2");
//            entityManager.persist(member2);
//
//            entityManager.flush();
//            entityManager.clear();

//            Member m1=entityManager.getReference(Member.class, member1.getId());
//            Member m2=entityManager.getReference(Member.class, member1.getId());
//            Member m2=entityManager.find(Member.class, member1.getId());
            //만약 둘다 프록시로 가져왔을 때 같은 프록시가 반환된다.
//            System.out.println("m1 = " + m1.getClass());
//            System.out.println("m2 = " + m2.getClass());
//            m1 = class hellojpa.Member$HibernateProxy$3stjOvr5
//            m2 = class hellojpa.Member$HibernateProxy$3stjOvr5
            //재초기화가 안된다.
            //또한 레퍼런스로 그 객체를 가져온 이후 find로 다시한번 그 객체를 호출하면
//            m1 = class hellojpa.Member$HibernateProxy$5dfTpXKF
//            m2 = class hellojpa.Member$HibernateProxy$5dfTpXKF
            //이렇게 똑같은 프록시를 호출하는 것을 알 수 있다.
            //프록시를 한번 조회하고
            //프록시에 문제없도록 개발하는 게 중요한데
            //JPA에서는 처음 조회된 것에 대해 클래스 타입을 맞춰준다.
            //프록신ㄴ 부모엔티티를 상속받는 것이기 때문에 우리는 ==대신 인스텐스 오브로 비교하고
            //이런 작업이 필요하다.
            //가장 중요한 것>> 영속성 컨텍스트에 도움받을 수 없는 준영속일 때, 프록시를 초기화하면
            //문제발생
//            Member m1=entityManager.getReference(Member.class, member1.getId());
//            System.out.println("m1 = " + m1.getClass());//proxy
            //이렇게 가져왔을 떄
//            m1.getName();
            //이때 초기화될텐데
//            entityManager.close();
            //이때 영속성 컨텍스트를 끄거나
//            entityManager.detach(m1);
            //이렇게 강제로 준영속으로 만들 경우
//            m1.getName();
//            System.out.println("m1 = " + m1.getName());
            //동작이 안된다.>>익셉션을 확인해보면
//            m1 = class hellojpa.Member$HibernateProxy$1DfBUUeo
//            e = org.hibernate.LazyInitializationException: could not initialize proxy [hellojpa.Member#1] - no Session
//            8월 26, 2024 9:18:46 오후 org.hibernate.engine.jdbc.connections.internal
            //이렇게 detach를 하면 프록시를 초기화할 수 없다고 나온다.
            //no Session이라고 나오는데
            //영속성 컨텍스트 세션에 없다고 나오는 것
            //close로 닫아도 똑같다.

            //프록시 초기화 확인
//            Member member1=new Member();
//            member1.setName("member1");
//            entityManager.persist(member1);
//
//            Member member2=new Member();
//            member2.setName("member2");
//            entityManager.persist(member2);
//
//            entityManager.flush();
//            entityManager.clear();
//            Member m1=entityManager.getReference(Member.class, member1.getId());
//            System.out.println("m1 = " + m1.getClass());//proxy

//            1.isLoad :프록시 인스턴스의 초기화 여부 확인
//            System.out.println("isLoad = " +entityManagerFactory.getPersistenceUnitUtil().isLoaded(m1) );
//            //이렇게 프록시 초기화를 안한 상태에서 로딩 유무를 파악 후 isLoad는 false가 나온다.
//            //호출하여 초기화하면 true가 나온다.
////            2. getClass : 프록시 클래스 확인
//            System.out.println("m1 = " + m1.getClass());//
////            3. 프록시 강제 초기화 m1.getName()을 통해 강제 초기화를 진행한 것인데
//            //별도 하이버네이트 별도 메소드가 존재하는데
//            Hibernate.initialize(m1);//이렇게 강제 초기화가 가능하다.
            //셀렉트 쿼리가 나가는 것을 볼 수 있다.

            //참고 JPA는 강제초기화가 없어서
            //m1.getName()이런식으로 강제 호출해야 된다.

            //지연로딩

//            Team team =new Team();
//            team.setName("teamA");
//            entityManager.persist(team);
//
//            Member member1=new Member();
//            member1.setTeam(team);
//            member1.setName("member1");
//            entityManager.persist(member1);
//
//
//
//
//            entityManager.flush();
//            entityManager.clear();
//
//            Member m=entityManager.find(Member.class, member1.getId());
//            System.out.println("m1 = " + m.getClass());//proxy
//            //Lazy상태를 Team객체에 걸어놓으니 member테이블만 select하지만
//            System.out.println("m.getTeam() = " + m.getTeam().getClass());
//            //이렇게 Team을 Team은 m.getTeam() = class hellojpa.Team$HibernateProxy$IQL1By90
//            //프록시 타입인 것을 알 수 있다.
//            System.out.println("m.getTeam().getName() = " + m.getTeam().getName());
//            //이렇게 팀을 조회하는 시점에 팀을 조회하는 쿼리가 나가는 것을 알 수 있다.
//            System.out.println("m.getTeam() = " + m.getTeam().getClass());
//            //이렇게 팀 이름까지 가져온다면?
////            m.getTeam().getName() = teamA
////            m.getTeam() = class hellojpa.Team$HibernateProxy$sn0ZBN6H
//            //이미 프록시로 맴버에 넣었기 떄문에 가져와도 타겟만 연결되고 여전히 프록시
//            //결국 지연세팅은 연관된 것을 프록시로 가져오는 것
//
//            //실무에서는 jpql을 많이 사용하는데
//            List<Member> resultList = entityManager.createQuery("select m from Member m", Member.class)
//                    .getResultList();
            //jpql로 호출하면
//            Hibernate:
//            select
//            m1_0.MEMBER_ID,
//                    m1_0.INSERT_MEMBER,
//                    m1_0.createDate,
//                    m1_0.lastModifiedDate,
//                    m1_0.UPDATE_MEMBER,
//                    l1_0.LOCKER_ID,
//                    l1_0.name,
//                    m1_0.USERNAME,
//                    t1_0.TEAM_ID,
//                    t1_0.name
//            from
//            Member m1_0
//            left join
//            Locker l1_0
//            on l1_0.LOCKER_ID=m1_0.LOCKER_ID
//            left join
//            Team t1_0
//            on t1_0.TEAM_ID=m1_0.TEAM_ID
//            where
//            m1_0.MEMBER_ID=?
//            m1 = class hellojpa.Member
//            m.getTeam() = class hellojpa.Team
//            m.getTeam().getName() = teamA
//            m.getTeam() = class hellojpa.Team
//            Hibernate:
//    /* select
//        m
//    from
//        Member m */ select
//            m1_0.MEMBER_ID,
//                    m1_0.INSERT_MEMBER,
//                    m1_0.createDate,
//                    m1_0.lastModifiedDate,
//                    m1_0.UPDATE_MEMBER,
//                    m1_0.LOCKER_ID,
//                    m1_0.USERNAME,
//                    m1_0.TEAM_ID
//            from
//            Member m1_0
            //쿼리가 두번 나간다. em.find라는 것은 pk를 찍어서 가져오기 때문에
            //jpa가 내부 최적화를 하지만 jpql이란 것은 sql을 그대로 sql로 번역해서
            //맴버만 셀렉트 하는 것이고 맴버를 가져왔는데 어라라? 팀이 즉시로딩으로 되어 있으니
            //즉시로딩은 값이 무조건 있어야 되기 떄문에 맴버 쿼리 나가고 
            //10개만큼 팀을 가져오기 위한 쿼리가 나간다. 그 즉시
            //쿼리가 11개가 되어버리는 것
            //SQL :select * from Member
            //SQL :select * from TEAM where TEAM_ID= xxx로 또 갯수만큼 이 쿼리가 나가는 것
            //이러한 문제가 존재하기 때문에 레이지 모드로 설정해서 필요에 맞게 쿼리를 하도록 한다.


            //EAGER 확인
//            Team team =new Team();
//            team.setName("teamA");
//            entityManager.persist(team);
//
//            Member member1=new Member();
//            member1.setTeam(team);
//            member1.setName("member1");
//            entityManager.persist(member1);
//
//            Team team2 =new Team();
//            team2.setName("teamB");
//            entityManager.persist(team2);
//
//            Member member2=new Member();
//            member2.setTeam(team2);
//            member2.setName("member1");
//            entityManager.persist(member2);
//
//            entityManager.flush();
//            entityManager.clear();

//            List<Member> resultList = entityManager.createQuery("select m from Member m", Member.class)
//                    .getResultList();

//            Hibernate:
    /* select
        m
    from
//        Member m */
//            select
//            m1_0.MEMBER_ID,
//                    m1_0.INSERT_MEMBER,
//                    m1_0.createDate,
//                    m1_0.lastModifiedDate,
//                    m1_0.UPDATE_MEMBER,
//                    m1_0.LOCKER_ID,
//                    m1_0.USERNAME,
//                    m1_0.TEAM_ID
//            from
//            Member m1_0
//            Hibernate:
//            select
//            t1_0.TEAM_ID,
//                    t1_0.name
//            from
//            Team t1_0
//            where
//            t1_0.TEAM_ID=?
//            Hibernate:
//            select
//            t1_0.TEAM_ID,
//                    t1_0.name
//            from
//            Team t1_0
//            where
//            t1_0.TEAM_ID=?
        //이렇게 팀을 조회하는 쿼리가 두개가 나오는 것을 볼 수 있다.
            //1은 최초쿼리이고 나머지 총 갯수 n개 쿼리가 나간다고 해서 N+1문제이다.
        //Lazy로 변경하면 맴버의 쿼리가 안나간다.
            //N+1 해결방안은 모든 연관관계를 지연로딩으로 설정하고
//            1.fetch조인을 사용
            //맴버만 가져오고 싶을 때는 맴버만 셀렉트 팀이 같이 필요하면 패치조인을 통해서
            //맴버랑 팀을 같이 가져온다고 설정
            //패치조인을 하면 한방 쿼리로 날려서 가져온다
//     List<Member> resultList = entityManager.createQuery("select m from Member m join fetch m.team", Member.class)
//                    .getResultList();
           //이렇게 패치 조인으로 값을 가져온다.
//             /* select
//        m
//    from
//        Member m
//    join
//
//    fetch
//        m.team */ select
//            m1_0.MEMBER_ID,
//                    m1_0.INSERT_MEMBER,
//                    m1_0.createDate,
//                    m1_0.lastModifiedDate,
//                    m1_0.UPDATE_MEMBER,
//                    m1_0.LOCKER_ID,
//                    m1_0.USERNAME,
//                    t1_0.TEAM_ID,
//                    t1_0.name
//            from
//            Member m1_0
//            join
//            Team t1_0
//            on t1_0.TEAM_ID=m1_0.TEAM_ID
            //이렇게 한방 조인을 해버린다. 루프로 값을 돌려도 값이 다 채워진다.
//            2.Entity Graph라고 어노테이션이 존재하고

//            3. 배치 사이즈라고 해서 또 다르게 푸는 방법이 존재
            //이건 1+1으로 쿼리가 나가도록 할 수 있는 것
            //하지만 대부분 패치 조인으로 이러한 문제를 해결하게 된다.
            //이때 xToOne은 전부 EAGER로딩이라 LAZY를 걸어야 한다.
            //위에 XtoMany면 뒤에 Lazy로딩로 되어 있어서

            //Carscade활용

            Child child1 =new Child();
            Child child2 =new Child();

            Parent parent=new Parent();
            parent.addChild(child1);
            parent.addChild(child2);
            //이때 persist를 3번 호출해야 되는데
            entityManager.persist(parent);
//            entityManager.persist(child1);
//            entityManager.persist(child2);
            //위와 같이 CARSCADE가 없으면 이렇게 3번 넣어야 한다.
//            이때 parent가 persist될 때 child가 persist가 됬으면 좋겠다
//            라는 생각을 하는데 이걸 해결해주는 게 cascade이다.
            Hibernate:
    /* insert for
        hellojpa.Parent */
//            insert
//                    into
//            Parent (name, id)
//            values
//                    (?, ?)
//            Hibernate:
//    /* insert for
//        hellojpa.Child */
//            insert
//                    into
//            Child (name, parent_id, id)
//            values
//                    (?, ?, ?)
//            Hibernate:
//    /* insert for
//        hellojpa.Child */
//
//                    into
//            Child (name, parent_id, id)
//            values
//                    (?, ?, ?)
        //이렇게 자동으로 persist가 된다.
//            매핑과는 아무런 관련이 없다.
//            엔티티를 영속화할 때 함께 영속화하는 편리함을 제공할 뿐!
            //고아 객체 삭제
            entityManager.flush();
            entityManager.clear();
            Parent findParent = entityManager.find(Parent.class, parent.getId());
            findParent.getChildList().remove(0);
            //만약 이렇게 0번째 자식을 지운다면?
            //연관관계가 끊어졌기 때문에 자동으로
            Hibernate:
//            /* delete for hellojpa.Child */delete
//                    from
//            Child
//                    where
//            id=?
            //위와 같이 0번 리스트에 연결되어 있던 자식 객체 엔티티가 삭제된 것을 알 수 있다.
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
            System.out.println("e = " + e);
        }finally {
            //메니져와 팩토리 닫기
            entityManager.close();
            //엔티티메니져가 내부적으로 데이터베이스 커넥션을 물고 동작하기 때문에 성능을 위해서는
            //호출 후 닫아줘야 한다.
        }
        entityManagerFactory.close();


    }
    private static void logic(Member m1,Member m2){
//        System.out.println("(m1.getClass()==m2.getClass()) = " +
//                (m1.getClass()==m2.getClass()));
        System.out.println("(m1==m2) = " +
                (m1 instanceof Member));
        System.out.println("(m1==m2) = " +
                (m2 instanceof Member));
    }//둘다 True인 이유
//    이 상황에서는 두 객체 m1과 m2가 프록시 타입이지만 instanceof Member 검사를 했을 때 모두 true를 반환하는 이유를 이해하는 것이 중요합니다.
//
//1. 프록시 객체와 instanceof 연산자
//    Hibernate의 프록시 객체는 실제 엔티티 클래스를 상속받아 만들어집니다. 예를 들어, Member 클래스의 프록시 객체는 Member 클래스를 상속하는 프록시 클래스입니다. 따라서 프록시 객체는 instanceof 연산자를 사용해 부모 클래스인 Member의 인스턴스로 인식됩니다.
//
//            java
//    코드 복사
//System.out.println("(m1==m2) = " + (m1 instanceof Member));
//System.out.println("(m1==m2) = " + (m2 instanceof Member));
//    이 코드에서 m1과 m2는 프록시 객체입니다. 프록시 객체는 실제 엔티티 클래스(Member)를 상속받기 때문에, m1 instanceof Member와 m2 instanceof Member 모두 true를 반환하게 됩니다.
//
//2. == 연산자와 instanceof의 차이
//== 연산자는 두 객체의 레퍼런스가 동일한지를 확인합니다. 즉, 두 변수가 동일한 객체를 참조하고 있는지 확인하는 것입니다. 이 경우 프록시 객체이든 실제 객체이든 상관없이, 두 변수가 동일한 객체를 가리키고 있으면 true를 반환합니다.
//
//            instanceof 연산자는 객체가 특정 클래스나 그 클래스의 서브클래스인지 확인합니다. 프록시 객체는 해당 엔티티 클래스(Member)의 서브클래스이기 때문에, instanceof Member는 true를 반환합니다.
//
//            결론
//instanceof Member가 true를 반환하는 이유는, m1과 m2가 프록시 객체이지만, 프록시 객체가 Member 클래스를 상속받기 때문입니다. 따라서 m1 instanceof Member와 m2 instanceof Member는 모두 true가 됩니다.
//
//    java
//    코드 복사
//System.out.println("(m1==m2) = " + (m1 instanceof Member));
//System.out.println("(m1==m2) = " + (m2 instanceof Member));
//    이 코드에서는 instanceof 연산자를 사용하여 m1과 m2가 Member 클래스의 인스턴스인지를 확인하고 있기 때문에, 둘 다 true로 평가되는 것입니다. m1과 m2가 프록시 객체이더라도, 그들은 Member 클래스를 상속받는 서브클래스이므로, instanceof 연산자는 true를 반환합니다.

    private static void printMember(Member member) {
        System.out.println("member.getName() = " + member.getName());
    }

    private static void printMemberAndTeam(Member member){
        String username= member.getName();
        System.out.println("username = " + username);

        Team team= member.getTeam();
        System.out.println("team = " + team.getName());
        //이렇게 팀 정보를 출력하려고 할 경우
        //쿼리가 DB에 맴버를 찾을 때 연관된 Team도 한번에 가져오면 좋을텐데

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
