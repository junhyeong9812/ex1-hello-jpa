package hellojpa;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

    //Team이 1대 다 관계이기 때문에 //onetoMany
    @OneToMany(mappedBy = "team") //mappedBy에서 반대편 사이드의 객체명을 적어주면 된다.
//    private Team team; 맴버에 이렇게 team이라는 객체를 지정해주는 것
    //(mappedBy = "team")이 mappedBy가 왜 필요한거지??
    //이걸 알기 위해서는 객체와 테이블간에 연관관계를 맺는 차이를 이해해야 한다.
    private List<Member> members=new ArrayList<>();
    //add할때 null포인트가 안뜨도록 빈 어레이리스트 객체를 생성
    public void addMember(Member member){
    member.setTeam(this);
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", members=" + members +
                '}';
    }
    //이렇게 생성하면 이렇게 나오는데


    //      편의 메소드가 양쪽에 다 존재하면 문제가 일어날 수도 있다.

    //객체 연관관계는 2개로
//    회원 -> 팀 연관관계 1개 (단방향)
//    팀 -> 회원 연관관계 1개 (단방향)
//    이렇게 총 두개의 연관관계가 존재하지만
//    테이블은
//    회원 <->팀 연관관계 1개 (양방향)
//    이렇게 존재하는 것

    //결국 객체의 양방향 관계는 사실 서로 다른 단방향 관계가 두개이며 객체를 양방향 참조를 하기 위해서
    //단반향 연관관계를 두개 만들어야 된다.

    //그렇게 되면 객체에서는 team객체와 members객체 중 어떤 것을 외래키로 관리할 지 모르게 된다.
    //맴버의 팀을 바꿀 때 맴버의 Team값을 바꾸면 될까? 아니면 team의 members를 바꾸는게 맞을까?
    //이와 관련된 딜레마에 빠지게 된다.
//    하지만 DB입장에서는 Team_ID값만 업데이트 되면 된다. 외래키만 변경되면 된다.
//    그래서 연관관계 매핑을 할때 단반향일 떄는 상관없지만 양방향이 되면서 팀의 members도
//    신경쓰게 된다.
//    member의 team에는 값을 넣고 팀의 members에는 값을 안넣는다면?
    //둘다 값이 존재해야 되는데 이때 명확한 차이가 있는 것
    //그래서 규칙이 생기게 된다.
    //둘 중 하나로 외래키로 관리해야 된다.
    //member의 team으로 관리할 지 리스트의 members로 관리할 지
    //연관관계의 주인을 정해야 된다.

//    그래서 연관관계의 주인이라는 개념이 생겼다.
//    양방향 매핑 규칙
//    1. 객체의 두 관계중 하나를 연관관계의 주인으로 지정
//    2.연관관계의 주인만이 외래 키를 관리 (등록,수정)
//    3. 주인이 아닌쪽은 읽기만 가능
//    4. 주인은 mappedBy속성을 사용하지 않는다.
//    5.주인이 아니면 mappedBy속성으로 주인 지정

    //이때 주인을 누구로 해야 되는가?
//    @ManyToOne @JoinColumn을 통해 어떤 값으로 조인을 할 지 fk가 지정되어 있는 member
    //결국 이 조인커럶이 관리를 하는 것
    //!!외래키가 있는 곳을 주인으로 정해야 된다!!
    //Team은 1대다고 팀에 의해 관리가 된다고 지정하는 것
    //결국 member가 주인인 것
//    그래서 team의 members에 값을 넣어도 아무런 일도 벌어지지 않는다.
    //대신 members로 조회는 가능
//    결국 DB에 값을 넣을 때는 ManyTOOne쪽만 참조해서 업데이트를 하거나 값을 넣는다.
    //결론 >>oneToMany에 값을 넣지마라
    //team을 변경하면 Member에 업데이트 쿼리가 나갈 수도 있다.

    //외래키가 있는 곳을 주인으로 정하면 편하다.
    //결국 RDB입장에서는 외래키가 있는 곳이 n이다.
    //외래키가 없는 곳이 결국 1이다.

    //연관관계 키는 단순하게 N인쪽이 외래키로 설정하면 된다.

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
