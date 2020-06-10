package br.com.nanosofttecnologia.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

public class DataTableRequest {

  private List<DataTableColumns> columns;
  private List<DataTableOrder> order;
  private DataTableSearch search;
  private Integer draw;
  private Integer start;
  private Integer length;
  private Map<String, Object> object;

  public List<DataTableColumns> getColumns() {
    return columns;
  }

  public void setColumns(List<DataTableColumns> columns) {
    this.columns = columns;
  }

  public List<DataTableOrder> getOrder() {
    return order;
  }

  public void setOrder(List<DataTableOrder> order) {
    this.order = order;
  }

  public DataTableSearch getSearch() {
    return search;
  }

  public void setSearch(DataTableSearch search) {
    this.search = search;
  }

  public Integer getDraw() {
    return draw;
  }

  public void setDraw(Integer draw) {
    this.draw = draw;
  }

  public Integer getStart() {
    return start;
  }

  public void setStart(Integer start) {
    this.start = start;
  }

  public Integer getLength() {
    return length;
  }

  public Map<String, Object> getObject() {
    return object;
  }

  public void setObject(Map<String, Object> object) {
    this.object = object;
  }

  public Pageable getPageable() {
    int page = start.intValue() == 0 ? 0 : start.intValue() / length.intValue();
    if (this.order.isEmpty()) {
      return PageRequest.of(page, length.intValue());
    } else {
      return PageRequest.of(
          page,
          length.intValue(),
          Sort.by(
              new Sort.Order[] {
                new Sort.Order(
                    Sort.Direction.fromString(this.order.get(0).getDir()),
                    this.columns.get(this.order.get(0).getColumn()).getData())
              }));
    }
  }

  public void setLength(Integer length) {
    this.length = length;
  }

  public DataTableResponse getError(String msg) {
    DataTableResponse response = new DataTableResponse();
    response.setDraw(this.draw);
    response.setError(msg);
    return response;
  }

  public DataTableResponse getResponse() {
    DataTableResponse response = new DataTableResponse();
    response.setDraw(this.draw);
    return response;
  }

  public DataTableResponse getResponse(long recordsTotal, long recordsFiltered, List<?> list) {
    DataTableResponse response = new DataTableResponse();
    response.setDraw(this.draw);
    response.setData(list);
    response.setRecordsFiltered(recordsFiltered);
    response.setRecordsTotal(recordsTotal);
    return response;
  }
}
