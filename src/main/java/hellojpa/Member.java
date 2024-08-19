package hellojpa;

import jakarta.persistence.*;

import java.util.Date;

@Entity(name="Member") //엔티티가 존재해야 jpa가 로딩할 때 jpa를 사용하는 엔티티라고 인식 후 관리하게 된다.
//name속성은 디폴트로 위와 같이 현재 클래스명과 같다.
//@Table(name = "user")//위와 같이 DB테이블명을 명시해서 매핑해줄 수 있다.
public class Member {


    @Id//Id 어노테이션을 통해 Pk가 어떤 값인 지 알려준다.
    private long id;
//    @Column(name = "userName")//또한 이렇게 컬럼 어노테이션을 통해 컬럼명도 매핑시킬 수 있다.
    @Column(name="name",unique = true,length = 10)//유니크 제약 조건도 이렇게 걸 수 있다.
    //이건 DB에 영향을 주기 때문에 JPA실행 메커니즘에는 영향을 주지 않는다.
    //DDL생성에만 도움을 준다.
//    private String name;
    //또한 컬럼의 name 속성을 통해 DB 컬럼명을 별도로 지정해서 DB의 컬럼명과 객체명을 다르게 할 수
    //있다.
    private String username;

    private int age;
    //이렇게 테이블을 삭제하면서 age도 새롭게 테이블을 생성한다.
    //DB에서도 컬럼이 추가된 것을 알 수 있다.DB가서 크레이트나 얼터를치면서
    //내 PC에서 빨리 개발할 때는 메리트가 있다. 엔티티만 수정하면 되기 때문이다.

    //요구사항 추가
//    1.회원은 일반 회원과 관리자로 구분해야 한다.
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
//    public enum RoleType {
//        USER,ADMIN;
//    }
//    이처럼 별도의 역할 타입을 만들어야 한다.

//    2.회원가입일과 수정일이 있어야 한다.
//회원 가입일
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    //회원정보 마지막 수정일
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

//    3.회원을 설명할 수 있는 필드가 있어야 한다. 이 필드는 길이가 제한이 없다.
    @Lob
    private String description;


//    public Member(long id, String name) {
//        this.id = id;
//        this.name = name;
//    }//jpa는 생성자를 만들면 jpa는 기본적으로 리플랙션이나 이런 것을 쓰기 떄문에
    //동적 객체 생성을 해야된다. 그래서 기본 생성자가 하나 있어야 된다.
    public Member(){

    }

    //alt insert를 통해 게터 세터 생성 롬복을 사용해도 된다.

}
