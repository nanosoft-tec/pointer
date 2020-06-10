package br.com.nanosofttecnologia.controller.rest;

import br.com.nanosofttecnologia.model.Company;
import br.com.nanosofttecnologia.repository.CompanyRepository;
import br.com.nanosofttecnologia.util.DataTableRequest;
import br.com.nanosofttecnologia.util.DataTableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class CompanyRestController {

  @Autowired private CompanyRepository companyRepository;

  @GetMapping(value = "/secure/company/listActive", produces = APPLICATION_JSON_VALUE)
  public List<Company> listActive() {
    OAuth2User user =
        (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return companyRepository.findByUserIdAndActive(user.getName(), Boolean.TRUE);
  }

  @PostMapping(value = "/secure/company/list", produces = APPLICATION_JSON_VALUE)
  public DataTableResponse list(@RequestBody DataTableRequest request) {
    OAuth2User user =
        (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    List<Company> companies = companyRepository.findByUserId(user.getName());
    return request.getResponse(companies.size(), companies.size(), companies);
  }

  @PostMapping(
      value = "/secure/company",
      consumes = APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE)
  public Company save(@RequestBody Company company) {
    OAuth2User user =
        (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    company.setActive(true);
    company.setUserId(user.getName());
    return companyRepository.save(company);
  }

  @PutMapping("/secure/company/{id}")
  public Company update(@PathVariable("id") String companyId) {
    OAuth2User user =
        (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Company company =
        companyRepository.findById(companyId).orElseThrow(() -> new NoSuchElementException());
    if (company.getUserId() == user.getName()) {
      company.setActive(!company.isActive());
    }
    return companyRepository.save(company);
  }
}
