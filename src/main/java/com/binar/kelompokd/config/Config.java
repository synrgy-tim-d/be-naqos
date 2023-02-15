package com.binar.kelompokd.config;

import lombok.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Config {
  public String isRequired =" is Required";
  public String convertDateToString(Date date) {
    DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String strDate = dateFormat.format(date);
    return strDate;
  }
}
