package product.demo.shop.common.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class TestEntity extends AuditEntity {

    @Id @GeneratedValue
    private long id;

    private String testField;
}
