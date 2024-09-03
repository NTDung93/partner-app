package elca.ntig.partnerapp.be.model.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {
    @Version
    @Column(nullable = false)
    private int version;
}
