package br.com.nanosofttecnologia.controller.rest;

import br.com.nanosofttecnologia.dto.MarkDTO;
import br.com.nanosofttecnologia.dto.PointDTO;
import br.com.nanosofttecnologia.model.Company;
import br.com.nanosofttecnologia.model.Mark;
import br.com.nanosofttecnologia.model.TypeEnum;
import br.com.nanosofttecnologia.model.User;
import br.com.nanosofttecnologia.repository.CompanyRepository;
import br.com.nanosofttecnologia.repository.MarkRepository;
import br.com.nanosofttecnologia.util.DataTableRequest;
import br.com.nanosofttecnologia.util.DataTableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class MarkRestController {

  @Autowired private MarkRepository markRepository;
  @Autowired private CompanyRepository companyRepository;

  @PostMapping(
      value = "/secure/mark/listCurrentDay",
      consumes = APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE)
  public DataTableResponse listCurrentDay(@RequestBody DataTableRequest request) {
    List<Mark> marks = markRepository.findByDate(LocalDate.now());
    return request.getResponse(marks.size(), marks.size(), marks);
  }

  @PostMapping(
      value = "/secure/mark/list",
      consumes = APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE)
  public DataTableResponse list(@RequestBody DataTableRequest request) {
    List<PointDTO> points = new ArrayList<>();

    String companyId = request.getObject().get("companyId").toString();
    String period = request.getObject().get("period").toString();

    if (!StringUtils.isEmpty(companyId) && !StringUtils.isEmpty(period)) {
      int year = Integer.parseInt(period.split("-")[0]);
      int month = Integer.parseInt(period.split("-")[1]);
      LocalDateTime startPeriod = LocalDateTime.of(year, month, 1, 0, 0, 0, 0);
      LocalDateTime endPeriod = startPeriod.plusMonths(1).minusNanos(1);
      Company company =
          companyRepository.findById(companyId).orElseThrow(() -> new NoSuchElementException());

      List<Mark> marks =
          markRepository.findByDateBetweenAndCompany(
              startPeriod,
              endPeriod,
              company,
              Sort.by(Sort.Order.by("date"), Sort.Order.by("time")));
      Map<LocalDate, List<Mark>> marksByDate = new HashMap<>();

      marks.stream()
          .forEach(
              m -> {
                if (!marksByDate.containsKey(m.getDate())) {
                  marksByDate.put(m.getDate(), new ArrayList<>());
                }
                marksByDate.get(m.getDate()).add(m);
              });

      processMarkPoint(points, marksByDate);
    }
    return request.getResponse(points.size(), points.size(), points);
  }

  private void processMarkPoint(List<PointDTO> points, Map<LocalDate, List<Mark>> marksByDate) {

    long totalTime = 0;
    boolean missingMark = false;

    DateTimeFormatter formaterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter fromaterTime = DateTimeFormatter.ofPattern("HH:mm");
    for (Map.Entry<LocalDate, List<Mark>> entry : marksByDate.entrySet()) {
      entry.getValue().sort(Comparator.comparing(Mark::getTime));
      for (int i = 0; i < entry.getValue().size(); i++) {

        PointDTO point = new PointDTO();
        point.setDate(entry.getKey().format(formaterDate));
        if (TypeEnum.ENTRADA == entry.getValue().get(i).getType()) {
          point.setInputTime(entry.getValue().get(i).getTime().format(fromaterTime));
          point.setInput(entry.getValue().get(i).getTime());
          if (i + 1 < entry.getValue().size()) {
            if (TypeEnum.SAIDA == entry.getValue().get(i + 1).getType()) {
              point.setOutputTime(entry.getValue().get(i + 1).getTime().format(fromaterTime));
              point.setOutput(entry.getValue().get(i + 1).getTime());
              i++;
            }
          }
        } else {
          point.setOutputTime(entry.getValue().get(i).getTime().format(fromaterTime));
          point.setOutput(entry.getValue().get(i).getTime());
        }

        if (point.getInput() != null && point.getOutput() != null) {
          Duration sumTime = Duration.between(point.getInput(), point.getOutput());
          point.setSumTime(getDurationString(sumTime.getSeconds()));

          totalTime = totalTime + sumTime.getSeconds();
        } else {
          missingMark = true;
        }
        points.add(point);
      }
    }
    points.sort(Comparator.comparing(PointDTO::getDate));
    PointDTO point = new PointDTO();
    point.setDate("");
    point.setInputTime("");
    point.setOutputTime("");
    point.setSumTime((missingMark ?  "*" : "") + getDurationString(totalTime));
    points.add(point);
  }

  @PostMapping(
      value = "/secure/mark",
      consumes = APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE)
  public Mark save(@RequestBody MarkDTO dto) {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Mark mark = new Mark();
    mark.setUserId(user.getId());
    mark.setDate(dto.getDate().toLocalDate());
    mark.setTime(dto.getDate().toLocalTime());
    mark.setType(TypeEnum.valueOf(dto.getTypeId()));
    mark.setCompany(companyRepository.findById(dto.getCompanyId()).get());
    return markRepository.save(mark);
  }

  private String getDurationString(long seconds) {

    long hours = seconds / 3600;
    long minutes = (seconds % 3600) / 60;

    return twoDigitString(hours) + ":" + twoDigitString(minutes);
  }

  private String twoDigitString(long number) {

    if (number == 0) {
      return "00";
    }

    if (number / 10 == 0) {
      return "0" + number;
    }

    return String.valueOf(number);
  }
}
