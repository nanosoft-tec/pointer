package br.com.nanosofttecnologia.repository;

import br.com.nanosofttecnologia.model.Company;
import br.com.nanosofttecnologia.model.Mark;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MarkRepository extends MongoRepository<Mark, String> {
    List<Mark> findByDate(LocalDate now);

    List<Mark> findByDateBetweenAndCompany(LocalDateTime startPeriod, LocalDateTime endPeriod, Company company, Sort sort);
}
