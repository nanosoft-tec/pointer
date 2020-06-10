package br.com.nanosofttecnologia.dto;

import java.time.LocalDateTime;

public class MarkDTO {

  private LocalDateTime date;
  private String typeId;
  private String companyId;

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public String getTypeId() {
    return typeId;
  }

  public void setTypeId(String typeId) {
    this.typeId = typeId;
  }

  public String getCompanyId() {
    return companyId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }
}
