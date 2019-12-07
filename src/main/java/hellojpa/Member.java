package hellojpa;

import javax.persistence.*;
import java.lang.annotation.ElementType;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
//@Table(uniqueConstraints = "") // 한컬럼에 유니크 제약죠건을 사용할 수 있
public class Member {

    @Id
    private long id;

    @Column(name = "name", updatable = false, nullable = false) // 수정을 할 수 있는 필드 인지 아닌지, notnull == nullable = false
    private String name;

    @Enumerated(EnumType.STRING) //EnumType.Ordinal 은 실무에서 사용x
    private RoleType roleType;

    @Temporal(TemporalType.DATE)
    private Date regYmdt;

    @Temporal(TemporalType.TIME)
    private Date reqYmdt;

//    @Temporal(TemporalType.TIMESTAMP) // 현대 언어(JAVA8)  이후로는 LocalDate를 사용함)
    private LocalDateTime modYmdt;

    @Lob
    private String description;

    @Transient // db에서 사용하지 않는 필드
    private String temp;

    public Member(){

    }

    public Member(long id, String name){
        this.id = id;
        this.name = name;
    }

    public Long getId() {
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
