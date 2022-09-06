package ir.co.isc.cardservice.repository;


import ir.co.isc.cardservice.dao.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

}
