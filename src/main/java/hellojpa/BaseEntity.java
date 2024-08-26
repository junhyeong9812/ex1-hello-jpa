package hellojpa;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {
    //Mapped SuperClass
    @Column(name = "INSERT_MEMBER")
    private String createBy;
    private LocalDateTime createDate;
    @Column(name = "UPDATE_MEMBER")
    private String lastmodifiedBy;
    private LocalDateTime lastModifiedDate;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getLastmodifiedBy() {
        return lastmodifiedBy;
    }

    public void setLastmodifiedBy(String lastmodifiedBy) {
        this.lastmodifiedBy = lastmodifiedBy;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
