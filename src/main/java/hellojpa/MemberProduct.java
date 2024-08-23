package hellojpa;

import jakarta.persistence.*;

import java.time.LocalDateTime;

//다대다 관계를 풀기 위한 중간 엔티티 생성
@Entity
public class MemberProduct {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
    //이처럼 조인을 위해 묶는 게 좋다.
    //PK는 그냥 의미 없는 값을 쓰는 게 좋다.
    //나중에 배우는 JPA에서 2개의 PK를 묶어서 만들면 컴포지드 아이디를 하나 만들어줘야 하는데
    //그게 귀찮지만 DB설계 관점으로 하는게 새로운 아이디를 만들고 다른 것을 FK로 연결
//    아니면 두개를 묶어서 PK/FK로 사용할 지 두 방식이 존재

    //연결 엔티티 외 데이터
    private int count;
    private int price;
    private LocalDateTime orderDateTime;
    //이렇게 단순하게 구성할 수 도 있지만
    //실제 비즈니스는 복잡하게 구성되어 있는데
    //이때 이걸 더 쉽게 하기 위해 연결 엔티티를 생성해서 사용하는 게 좋다.
}
