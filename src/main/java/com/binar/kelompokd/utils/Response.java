package com.binar.kelompokd.utils;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Response {

  public Map sukses(Object obj){
    Map map = new HashMap();
    map.put("data", obj);
    map.put("code", HttpStatus.OK.value());
    map.put("status", HttpStatus.OK);
    return map;
  }

  public Map error(Object obj, Object code){
    Map map = new HashMap();
    map.put("code", code);
    map.put("status", obj);
    return map;
  }

  public Boolean isRequired(Object obj){
    return obj == null;
  }

  public Map templateSukses(Object objek){
    Map map = new HashMap();
    map.put("data", objek);
    map.put("code", HttpStatus.OK.value());
    map.put("status", HttpStatus.OK);
    return map;
  }

  public Map templateEror(Object objek){
    Map map = new HashMap();
    map.put("message", objek);
    map.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
    map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
    return map;
  }
  public Map notFound(Object objek){
    Map map = new HashMap();
    map.put("message", objek);
    map.put("status", HttpStatus.NOT_FOUND);
    map.put("code", HttpStatus.NOT_FOUND.value());
    return map;
  }

  public Map badRequest(Object objek){
    Map map = new HashMap();
    map.put("message", objek);
    map.put("status", HttpStatus.BAD_REQUEST);
    map.put("code", HttpStatus.BAD_REQUEST.value());
    return map;
  }

    public Map unauthorized(Object objek){
        Map map = new HashMap();
        map.put("message", objek);
        map.put("status", HttpStatus.UNAUTHORIZED);
        map.put("code", HttpStatus.UNAUTHORIZED.value());
        return map;
    }
}
