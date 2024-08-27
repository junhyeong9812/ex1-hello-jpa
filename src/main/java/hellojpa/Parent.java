package hellojpa;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Parent {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy = "parent",cascade = CascadeType.ALL,orphanRemoval = true)
    //cascade = CascadeType.ALL를 통해 내부의 child객체도 자동으로 영속성 관리가 되도록
    //persist를 해준다.
//    @OneToMany(mappedBy = "parent",orphanRemoval = true)
    //이렇게 하고 Parent를 삭제하면 그 내부의 child객체들도 orphan입장에서는 컬렉션 자체가
    //다 삭제되기 때문에 자식 객체들도 전부 삭제가 된다.
    private List<Child> childList=new ArrayList<>();

    //편의 메소드
    public void addChild(Child child){
        childList.add(child);
        child.setParent(this);
        //
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

    public List<Child> getChildList() {
        return childList;
    }

    public void setChildList(List<Child> childList) {
        this.childList = childList;
    }
}
