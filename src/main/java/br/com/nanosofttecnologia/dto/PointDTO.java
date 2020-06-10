package br.com.nanosofttecnologia.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalTime;

public class PointDTO {

  private String date;
  private String inputTime;
  private String outputTime;
  @JsonIgnore
  private LocalTime input;
  @JsonIgnore
  private LocalTime output;
  private String sumTime;

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getInputTime() {
    return inputTime;
  }

  public void setInputTime(String inputTime) {
    this.inputTime = inputTime;
  }

  public String getOutputTime() {
    return outputTime;
  }

  public void setOutputTime(String outputTime) {
    this.outputTime = outputTime;
  }

  public LocalTime getInput() {
    return input;
  }

  public void setInput(LocalTime input) {
    this.input = input;
  }

  public LocalTime getOutput() {
    return output;
  }

  public void setOutput(LocalTime output) {
    this.output = output;
  }

  public String getSumTime() {
    return sumTime;
  }

  public void setSumTime(String sumTime) {
    this.sumTime = sumTime;
  }
}
