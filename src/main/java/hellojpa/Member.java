package hellojpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity //엔티티가 존재해야 jpa가 로딩할 때 jpa를 사용하는 엔티티라고 인식 후 관리하게 된다.
//@Table(name = "user")//위와 같이 DB테이블명을 명시해서 매핑해줄 수 있다.
public class Member {
    @Id//Id 어노테이션을 통해 Pk가 어떤 값인 지 알려준다.
    private long id;
//    @Column(name = "userName")//또한 이렇게 컬럼 어노테이션을 통해 컬럼명도 매핑시킬 수 있다.
    private String name;
    //alt insert를 통해 게터 세터 생성 롬복을 사용해도 된다.
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
