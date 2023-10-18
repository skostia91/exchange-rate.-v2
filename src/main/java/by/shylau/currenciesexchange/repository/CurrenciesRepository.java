package by.shylau.currenciesexchange.repository;

import by.shylau.currenciesexchange.model.Currencie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrenciesRepository extends JpaRepository<Currencie, Integer> {
    Currencie findByCode(String name);
}
