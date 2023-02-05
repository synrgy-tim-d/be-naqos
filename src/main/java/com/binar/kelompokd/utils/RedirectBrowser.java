package com.binar.kelompokd.utils;

import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

@Component
public class RedirectBrowser implements AuthorizationCodeInstalledApp.Browser{
  private final static Logger logger = LoggerFactory.getLogger(RedirectBrowser.class);

  @Override
  public void browse(String s) throws IOException {
    System.setProperty("java.awt.headless", "false");
    Desktop desktop = Desktop.getDesktop();
    try{
      desktop.browse(new URI(s));
    }catch(Exception e){
      logger.error("redirect error", e);
    }
  }
}
