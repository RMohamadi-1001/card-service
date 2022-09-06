package ir.co.isc.cardservice.dao;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
//import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "person")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "accountEntitySet")
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="personEntity")
    @ToString.Exclude
    private Set<AccountEntity> accountEntitySet;

   /* @OneToMany(cascade = CascadeType.ALL, mappedBy = "personEntity")
    @ToString.Exclude
    private Set<CardEntity> cardEntitySet;*/

    @Column(name = "FIRST_NAME", nullable=false, length = 25)
    @NonNull
    private String firstName;

    @Column(name = "LAST_NAME", nullable=false, length = 40)
    @NonNull
    private String lastName;

    @Column(name = "NATIONAL_CODE", nullable=false, unique = true, length = 10)
    @Size(min = 10, max = 10)
    @NonNull
    private String nationalCode;

    @Column(name = "HOME_ADDRESS", nullable=false, length = 255)
    @NonNull
    private String homeAddress;

    @Column(name = "PHONE_NUMBER", nullable=false, length = 11)
    @NonNull
    private String phoneNumber;

}
