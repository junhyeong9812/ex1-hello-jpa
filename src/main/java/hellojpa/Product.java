package hellojpa;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id @GeneratedValue
    private Long id;
    private String name;

    //양방향 다대다
//    @ManyToMany(mappedBy = "products")
//    private List<Member> members=new ArrayList<>();

    //다대다 관계를 풀어쓰기
    @OneToMany(mappedBy = "product")
    private List<MemberProduct> memberProducts=new ArrayList<>();

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
