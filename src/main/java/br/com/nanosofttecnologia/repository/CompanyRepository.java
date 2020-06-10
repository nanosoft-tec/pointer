package br.com.nanosofttecnologia.repository;

import br.com.nanosofttecnologia.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CompanyRepository extends MongoRepository<Company, String> {

  List<Company> findByUserIdAndActive(String userId, boolean active);

  List<Company> findByUserId(String userId);
}
