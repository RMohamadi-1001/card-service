package ir.co.isc.cardservice.repository;


import ir.co.isc.cardservice.dao.CardEntity;
import ir.co.isc.cardservice.enums.CardTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {

    @Query("select c from CardEntity c where (c.cardNumber =:cardNumber or c.issuerCode =:issuerCode or c.cardType =:cardType) and c.accountEntity.personEntity.nationalCode = :nationalCode")
    List<CardEntity> searchForPersonCardType(@Param("cardNumber") String cardNumber,
                                 @Param("issuerCode") String issuerCode,
                                 @Param("cardType") CardTypeEnum cardType,
                                 @Param("nationalCode") String nationalCode);
}
