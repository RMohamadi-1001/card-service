package ir.co.isc.cardservice.dao;

import ir.co.isc.cardservice.enums.CardTypeEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
//import javax.validation.constraints.Size;

@Entity
@Table(name = "card")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ACCOUNT_ID", referencedColumnName = "ID", nullable=false)
    @ToString.Exclude
    private AccountEntity accountEntity;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PERSON_ID", referencedColumnName = "ID", nullable=false)
    @ToString.Exclude
    private PersonEntity personEntity;*/

    @Column(name = "CARD_NUMBER", nullable=false, unique = true, length = 16)
    @Size(min = 16, max = 16)
    @NonNull
    private String cardNumber;

    @Column(name = "CARD_TYPE", nullable=false)
    @NonNull
    private CardTypeEnum cardType;

    @Column(name = "ISSUER_NAME", nullable=false, length = 20)
    @NonNull
    private String issuerName;

    @Column(name = "ISSUER_CODE", nullable=false, length = 6)
    @Size(min = 6, max = 6)
    @NonNull
    private String issuerCode;

    @Column(name = "EXPIRE_DATE", nullable=false)
    @NonNull
    private String expireDate;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

}
