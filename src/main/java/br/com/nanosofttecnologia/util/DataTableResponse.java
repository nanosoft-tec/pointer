package br.com.nanosofttecnologia.util;

import java.util.ArrayList;
import java.util.List;

public class DataTableResponse {

  private Number draw;
  private Number recordsTotal = 0;
  private Number recordsFiltered = 0;
  private String error;
  private List<?> data = new ArrayList<>();

  public Number getDraw() {
    return draw;
  }

  public void setDraw(Number draw) {
    this.draw = draw;
  }

  public Number getRecordsTotal() {
    return recordsTotal;
  }

  public void setRecordsTotal(Number recordsTotal) {
    this.recordsTotal = recordsTotal;
  }

  public Number getRecordsFiltered() {
    return recordsFiltered;
  }

  public void setRecordsFiltered(Number recordsFiltered) {
    this.recordsFiltered = recordsFiltered;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public List<?> getData() {
    return data;
  }

  public void setData(List<?> data) {
    this.data = data;
  }
}
