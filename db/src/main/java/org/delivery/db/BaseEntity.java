package org.delivery.db;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@MappedSuperclass
@Getter @Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
