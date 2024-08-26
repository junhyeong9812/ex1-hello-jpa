package hellojpa;

import jakarta.persistence.*;

@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//이렇게 단일 테이블로 하게 되면 ITem테이블에 자식 객체도 전부 들어가서 생성되고
//DTYPE을 통해 데이터 타입을 분류한다.
@DiscriminatorColumn //싱글 테이블일 경우에는 이 어노테이션이 없어도 생긴다.
//@DiscriminatorColumn이 어노테이션을 추가하면
//item테이블에 DTYPE을 추가하고 그 안에 구현된 엔티티의 이름이 들어가게 된다.
//보통은 엔티티명이 DTYPE으로 들어가게 된다. 이게 있는 게 좋다.
//DB만 쿼리를 날렸을 때 앨범인지 무비때문인지 모르기 때문에 DB작업이 필요할 때
//DTYPE을 넣어놓는 게 좋다.
//        (name = "DTS_TYPE")
//이처럼 컬럼명도 별도로 지정할 수 있다.
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//구현 클래스마다 테이블 전략
public abstract class Item {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int price;
    //

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
//만약 별도의 전략을 선택하지 않으면 싱글 테이블 전략으로
//create table Item (
//        price integer not null,
//        id bigint not null,
//        DTYPE varchar(31) not null,
//actor varchar(255),
//artist varchar(255),
//author varchar(255),
//director varchar(255),
//isbn varchar(255),
//name varchar(255),
//primary key (id)
//    )// 한테이블에 자식 컬럼이 전부 들어가서 하나의 단일 테이블처럼 생성되어 버린다.

//@Inheritance(strategy = InheritanceType.JOINED)
//위처럼 부모(슈퍼) 엔티티에 조인 전략으로 매핑을 한다면
//Hibernate:
//create table Album (
//        id bigint not null,
//        artist varchar(255),
//primary key (id)
//    )
//Hibernate:
//create table Book (
//        id bigint not null,
//        author varchar(255),
//isbn varchar(255),
//primary key (id)
//    )
//Hibernate:
//create table Item (
//        price integer not null,
//        id bigint not null,
//        name varchar(255),
//primary key (id)
//    )
//create table Movie (
//        id bigint not null,
//        actor varchar(255),
//director varchar(255),
//primary key (id)
//    )
//이렇게 조인 테이블 설계와 같은 테이블이 된다.