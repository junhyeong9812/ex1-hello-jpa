package hellojpa;

import jakarta.persistence.*;

import java.sql.Clob;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Member extends BaseEntity{
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    @Column(name = "USERNAME")
    private String name;

    //여기서부터 중요
//    @Column(name = "TEAM_ID")
//    private Long teamId;
    //teamId를 가지고 와서 직접 타입을 적어준다.
    //왜냐하면 맴버는 맴버랑 팀을 레퍼런스로 가져가야 되는데 그게 아니라 지금
    //형식은 DB의 PK처럼 설계가 된 것이다.


    @ManyToOne//Member가 Many이기 때문에 many가 먼저온다.
            (fetch = FetchType.LAZY )//FetchType.EAGER이게 기본타입이라 한번에 다 가져오는 것
    //FetchType.LAZY는 지연 로딩 전략을 사용하면 쿼리를 나눠서 member를 조회 후
    //team정보를 가져온다.
    @JoinColumn(name = "TEAM_ID")//조인해야 되는 컬럼을 명시해준다.
    //이 조인컬럼은 필수
    private Team team;
    //이렇게 객체로 주입할 경우 이 객체와 현재 객체의 관계를 명시하여 jpa에게 알려줘야 한다.
    //회원과 팀은 DB관점으로 누가 one인 지 누가 many인 지 이러한 관계를 알려줘야 한다.
    //객체 설계에서 맴버가 many이며 팀이 one인 상태이다.
    //관계와 조인할 컬럼명을 명시해주면 끝난다.
    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;
    public Long getId() {
        return id;
    }

    //다대다 관계
//    @ManyToMany
//    @JoinTable(name = "MEMBER_PRODUCT")
//    private List<Product> products=new ArrayList<>();

    //다대다 관계 풀어내기
    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProducts=new ArrayList<>();

    //Mapped SuperClass
//    private String createBy;
//    private LocalDateTime createDate;
//    private String lastmodifiedBy;
//    private LocalDateTime lastModifiedDate;

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
        //결국 this가 자기 자신 객체 자체이다.
    }
    //직관적인 명칭 구성
    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
        //결국 this가 자기 자신 객체 자체이다.
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", team=" + team.toString() +
                '}';
    }// team.toString이처럼 팀.toString을 호출하게 되면
    //팀 객체 내부의 members를 호출하는데 이 컬랙션 안에 투 스트링을 또 만들게 된다.
    //이런식으로 롬복 라이브러리를 투스트링으로 만들면 다 만들어버린다.
    //그래서

}

//일대다 관계에서 TEAM이 주인일 경우
//@Entity
//public class Member {
//    @Id @GeneratedValue
//    @Column(name = "MEMBER_ID")
//    private Long id;
//    @Column(name = "USERNAME")
//    private String name;
//
//    일대다 양방향을 하고 싶을 경우
//    @ManyToOne
//    @JoinColumn(name="TEAM_ID",insertable = false,updatable = false)
//    private Team team;
    //@JoinColumn(name="TEAM_ID")이게 연관관계 주인이라 서로 주인으로 되어 있어서
    //망하는 것 그래서 JPA에서 순서를 제대로 파악 못하기 때문에
//insertable = false,updatable = false 이 두 옵션을 통해 읽기 전용이 되어버린다.
//매핑은 되어있지만 인서트와 업데이트를 안하게 되는 것
//그래서 결과적으로 팀에 있는 맴버가 주인으로 사용되고 맴버의 팀은 읽기 전용으로 걸어버리는 것

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//}

//@Entity(name="Member") //엔티티가 존재해야 jpa가 로딩할 때 jpa를 사용하는 엔티티라고 인식 후 관리하게 된다.
////name속성은 디폴트로 위와 같이 현재 클래스명과 같다.
////@Table(name = "user")//위와 같이 DB테이블명을 명시해서 매핑해줄 수 있다.
////--시퀀스 전략
//@SequenceGenerator(
//        name = "MEMBER_SEQ_GENERATOR",
//        sequenceName = "MEMBER_SEQ", //매핑할 데이터베이스 시퀀스 이름
//        initialValue = 1, allocationSize = 1)
////allocationSize의 디폴트 값은 50
////--테이블 전략
////@TableGenerator(
////        name = "MEMBER_SEQ_GENERATOR",
////        table = "MY_SEQUENCES",
////        pkColumnValue = "MEMBER_SEQ", allocationSize = 1)
////테이블 제너레이터에서 중요한 속성은 InitialValue>>초기값,마지막으로 생성된 값이 기준
////allocationSize>>시퀀스 한번 호출에 증가하는 수 (성능 최적화에 사용) 이 두가지가 중요
//public class Member {
//
//
//    @Id//Id 어노테이션을 통해 Pk가 어떤 값인 지 알려준다.
//    //기본키 매핑
//    @GeneratedValue(
//            //GeneratedValue 전략
////            1.strategy =GenerationType.AUTO
//            //DB방언에 맞춰서 자동으로 생성해준다.
////        strategy = GenerationType.AUTO
//
////                IDENTITY : 기본 키 생성을 데이터베이스에 위임(MySQL/오토인크리먼트)
////            strategy = GenerationType.IDENTITY
//            //SEQUENCE :데이터베이스 시퀸스는 유일한 값을 순서대로 생성하는 데이터베이스 오브젝트
//            //이 오브젝트를 활용
//            strategy = GenerationType.SEQUENCE
////            select
////            next value for Member_SEQ
////            위처럼 시퀸스를 찾는 것을 볼 수 있다.
//            //하지만 시퀸스가 없어서 Integer를 사용해야 된다.
//            //하지만 인티저는 10억을 넘어가면 다시 0으로 돌아오기 때문에
//            //long으로 써야한다.
//            //이제는 성능이 너무 좋아져서 10억 이상 데이터 타입을 바꾸기 힘들기 때문에
//            //그냥 Long을 최대한 활용하는 게 좋다.
//            //또한 별도의 @SequenceGenerator를 통해 시퀀스를 지정하지 않으면
//            //하이버네이트 자체적으로 시퀀스를 만들어서 사용한다.
//            //SequenceGen사용
//            ,generator = "MEMBER_SEQ_GENERATOR"
//            //위에서 생성한 시퀀스 이름을 저네레이터 속성값으로 넣는다.
////            strategy = GenerationType.TABLE,
////            generator = "MEMBER_SEQ_GENERATOR"
//    )
//    private long id;
//
//    //기본키 매핑 어노테이션
//    //만약 id를 직접 할당한다면
//    //String일 경우
////    private String id;
//    //1.@Id : id값을 지정(관계형DB는 오라클의 경우 시퀸스를 많이 사용)
//    //2.@GeneratedValue : 값을 자동할당해주는 것
//
//    //기본키 매핑 방법
////    1.직접할당 :@ID만 사용
////    2.자동생성 @GeneratedValue
////    -IDENTITY:데이터베이스에 위임,mySQL
////    -SEQUENCE: 데이터베이스 시퀸스 오브젝트 사용
////            @SequenceGenerator() 필요
////    -TABLE:키 생성용 테이블 사용, 모든 DB에서 사용
////            @TableGenerator() 필요
////    -AUTO : 방언에 따라 자동 지정, 기본값
//
//    //권장하는 식별자 전략
////    1. 기본키 제약 조건 : null이면 안된다. 유일한 값, 변하면 안된다.
////    2. 미래까지 이 조건을 만족하는 자연키는 찾기 어렵다. 대리키(대체키)를 사용하자.
////        (자연키란 주문번호나 전화번호같은 것들을 자연키라 한다.)
////        (대체키는 비즈니스와 관련 없는 값을 대체키로 사용해야 된다.)
////        (pk를 주민번호로 사용하면 pk만 아니라 그 회원을 가져다 사용한 나머지 테이블도
////        (다 변경해야 되는 불상사가 일어난다(개인정보둥 민감한 정보도 최대한 PK로 사용X))
////        (UUID같은 대체키를 사용하는 게 좋다.)
////    3. 예시로 주민등록번호도 기본키로 적절하지 않다.
////    4. 권장:Long형+대체키+키 생성전략 사용
//
//
//
//
////    @Column(name = "userName")//또한 이렇게 컬럼 어노테이션을 통해 컬럼명도 매핑시킬 수 있다.
//    @Column(name="name",unique = true
//            //nuique는 잘 사용되지 않는다 왜냐면 제약 조건이름이 렌덤값으로 나온다.
//            //그래서 이름을 반영하기 힘들다 그래서 여기서 쓰는게 아니다.
//            //그래서 위에 @Table 속성중 uniqueConstraint를 통해 제약 조건이름도 설정 가능
//            ,length = 10
//            //
//    ,insertable = true,updatable = true,
//            //    ,insertable = true,updatable = true 이 두가지 속성은 컬럼을 수정했을 때 데이터베이스에
//            //인서트를 할 것인 지? 업데이트나 알터문으로 데이터 수정을 할것인지?
//            //예시로 데이터를 등록을 하는데 변경은 되면 안되는 상황일 경우 기본이 true이기 때문에
//            //업데이트쪽만 false로 수정하면 이 컬럼 자체는 변경되지 않는다.
//            nullable = false
//            //기본은 true지만 false로 변경하면 not null 제약 조건이 생성되는 것이다.
//            //DDL도 해주고 notnull인 경우 없으면 컴파일 에러로 체크해준다.
//            ,columnDefinition = "varchar(100) default 'EMPTY'"
////            컬럼 정의를 내가 직접하고 싶을떄
//            //종속 옵션도 이걸 통해 다 넣을 수 있게 된다.
//
//
//    )//유니크 제약 조건도 이렇게 걸 수 있다.
//    //이건 DB에 영향을 주기 때문에 JPA실행 메커니즘에는 영향을 주지 않는다.
//    //DDL생성에만 도움을 준다.
////    private String name;
//    //또한 컬럼의 name 속성을 통해 DB 컬럼명을 별도로 지정해서 DB의 컬럼명과 객체명을 다르게 할 수
//    //있다.
//
//    private String username;
//
////    private int age;
//    //이렇게 테이블을 삭제하면서 age도 새롭게 테이블을 생성한다.
//    //DB에서도 컬럼이 추가된 것을 알 수 있다.DB가서 크레이트나 얼터를치면서
//    //내 PC에서 빨리 개발할 때는 메리트가 있다. 엔티티만 수정하면 되기 때문이다.
//    @Column(
//            //숫자가 엄청 큰 경우 추가 옵션을 줄 수 있는데
//            //precision이나 scale을 줄 수 있다.
//    )
//    private Integer age;
//    //가장 적절한 타입이 알아서 선택된다.
//
//
//
//
//    //요구사항 추가
////    1.회원은 일반 회원과 관리자로 구분해야 한다.
//    @Enumerated(
//            EnumType.STRING//이넘 타입은 String이 필수이다.
////        EnumType.ORDINAL
//    )
//    //Enum타입을 사용할 때는 EnumType.ODINAL이 기본 값이며
//    //위처럼 STring으로 사용 가능
//    //ORDINAL은 순서를 String은 enum이름자체를 저장
//    private RoleType roleType;
////    public enum RoleType {
////        USER,ADMIN;
////    }
////    이처럼 별도의 역할 타입을 만들어 사용할 수 있다.
////@Enumerated를 사용하면 이넘타입을 활용할 수 있다.
////    또한
//    //    public enum RoleType {
////     guest,USER,ADMIN;
////    }
////     이렇게 enum타입을 추가했을 경우 ODINAL일 경우
//    //이렇게 게스트가 0이 되는 것을 볼 수 있다 .하지만 A는 user인데 0인 상태가 변경되지 않는다.
////
////이렇게 이넘 타입을 오디널을 쓰면 정말 위험하다. 이러면 이걸 해결할 수 없는 버그가 되는 것
////    2.회원가입일과 수정일이 있어야 한다.
////회원 가입일
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createDate;
//    //회원정보 마지막 수정일
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date lastModifiedDate;
//
//    private LocalDate testLocalDate;
//    private LocalDateTime testLocalDateTime;
//    //이렇게 확인해보면 date는 년월일
//    //datetime은 년월일시분초까지 데이터가 존재하게 된다.
//    //Temporal에는 총 3가지 타입이 존재한다.
////    1.DATE
////    2.TIME
////    3.TIMESTAMP
////    위처럼 자바의 데이트 타입에는 날짜 시간이 다 있는데 데이터 베이스는 위 3가지를
////    구분해서 사용하기 때문에 3가지 타입으로 나뉘게 되는 것
////    그래서 매핑 정보를 할당해줘야 한다.
//
////    3.회원을 설명할 수 있는 필드가 있어야 한다. 이 필드는 길이가 제한이 없다.
//    @Lob
//    //DB에 varchaer를 넘어서는 데이터를 넣고 싶으면 LOB 타입을 사용하게 된다.
//    //여기서 문자타입은 디폴트로 CLOB과 매핑이 된다.
//    //지정할 수 있는 속성이 없는데 Lob은 매핑하는 필드타입이 문자면C 나머지는 B로 매핑된다.
//    private String description;
//
//
////    public Member(long id, String name) {
////        this.id = id;
////        this.name = name;
////    }//jpa는 생성자를 만들면 jpa는 기본적으로 리플랙션이나 이런 것을 쓰기 떄문에
//    //동적 객체 생성을 해야된다. 그래서 기본 생성자가 하나 있어야 된다.
//    public Member(){
//
//    }
//    //매핑 어노테이션 정리
////    1.@Column : 컬럼 매핑
////            속성 :
////            1. name: 필드와 매핑할 테이블의 컬럼 이름 (기본값:객체 필드 이름)
////            2.insertable,updatable : 등록,변경 여부 (기본값 :TRUE)
////            3.nullable(DDL) :null값의 허용 여부를 설정한다, false로 설정하면 DDL생성 시에
////                             not null 제약 조건이 붙는다.
////            4.unique(DDL) :@Table의 uniqueConstraints와 같지만 한 컬럼에 간단히 유니크
////                            제약조건을 걸 때 사용한다.
////            5.columnDefinition(DDL) :데이터베이스 컬럼 정보를 직접 줄 수 있다.
////            (ex/ varcHAR(100) DEFAULT EMPTY)
////            (기본값: 필드의 자바 타입과 방언 정보를 사용)
////            6.length(DDL):문자길이 제약조건, String 타입에만 사용한다.
////            (기본값 :255)
////            7.precision,scale(DDL): BigDecimal타입에서 사용한다.(BigInteger도 사용가능)
////                                    precision은 소숨점을 포함한 전체 자릿수를,scale은 소수의
////                                    자릿수다. 참고로 double,float타입에는 적용되지 않는다.
////                                    아주 큰 숫자나 정밀한 소수를 다루어야 할 때만 사용한다.
////            (기본값 : precision=19,scale=2)
//
////    2.@Temporal() : 날짜 타입 매핑
////                    날짜 타입(java.util.DATE,java.util.Calendear)을 매핑할 때 사용
////                    참고: LocalDate,LocalDateTime을 사용할 때는 생략 가능
////                    value를 속성값으로 받으며
////                    TemporalType.DATE :날짜,데이터베이스 data타입과 매핑
////                    TemporalType.TIME :시간,데이터베이스 time타입과 매핑
////                    TemporalType.TIMESTAMP :날짜와 시간, 데이터베이스 timestamp타입과 매핑
////
////    3.@Enumerated : enum 타입 매핑
////                    자바 enum타입을 매핑할 때 사용하며 ORDINAL을 사용할 수 없다.
////                    value를 속성값으로 받으며
////                    EnumType.ORDINAL :enum순서를 데이터베이스에 저장 (기본값)
////                    EnumType.STRING :enum이름을 데이터베이스에 저장
////    4.@Lob :BLOB,CLOB매핑
////            @LOB에는 별도 지정 속성이 없다.
////            CLOB:STRING,char[],java,sql.Clob
////            BLOB:byte[],java.sql.BLOB
////    5.@Transient : 특정 필드를 컬럼에 매핑하지 않음(매핑 무시)
////        필드 매핑 x
////        데이터베이스에 저장x,조회x
////        주로 메모리상에서만 임시로 어떤 값을 보관하고 싶을 때 사용
////        @Transient
////        private Interger Temp; >>DB말고 메모리에서만 별도 계산을 하고 싶을 때
////          기본으로 해놓으면 DB에 TEMP가 있어야 되지만 위 설정을 걸어놓으면 Temp는 별도의
////          쿼리로 안나간다.(캐시 데이터 같은 걸 넣어놓을 때
//    //alt insert를 통해 게터 세터 생성 롬복을 사용해도 된다.
//public long getId() {
//    return id;
//}
//
//    public void setId(long id) {
//        this.id = id;
//    }
////public String getId() {
////    return id;
////}
////
////    public void setId(String id) {
////        this.id = id;
////    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public Integer getAge() {
//        return age;
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    }
//
//    public RoleType getRoleType() {
//        return roleType;
//    }
//
//    public void setRoleType(RoleType roleType) {
//        this.roleType = roleType;
//    }
//
//    public Date getCreateDate() {
//        return createDate;
//    }
//
//    public void setCreateDate(Date createDate) {
//        this.createDate = createDate;
//    }
//
//    public Date getLastModifiedDate() {
//        return lastModifiedDate;
//    }
//
//    public void setLastModifiedDate(Date lastModifiedDate) {
//        this.lastModifiedDate = lastModifiedDate;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//}
