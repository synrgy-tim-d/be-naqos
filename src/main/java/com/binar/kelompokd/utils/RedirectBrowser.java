package com.binar.kelompokd.utils;

import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;

@Component
public class RedirectBrowser implements AuthorizationCodeInstalledApp.Browser{
  private final static Logger logger = LoggerFactory.getLogger(RedirectBrowser.class);

  @Override
  public void browse(String s) throws IOException {
    System.setProperty("java.awt.headless", "false");
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    System.out.println("Headless mode: " + GraphicsEnvironment.isHeadless());
    try {
      if (Desktop.isDesktopSupported()) {
        Desktop desktop = Desktop.getDesktop();
        if (desktop.isSupported(Desktop.Action.BROWSE)) {
          System.out.println("Attempting to open that address in the default browser now...");
          desktop.browse(URI.create(s));
        }
      }
    } catch (IOException var2) {
      logger.warn( "Unable to open browser", var2);
    } catch (InternalError var3) {
      logger.warn( "Unable to open browser", var3);
    }catch(Exception e){
      logger.error("redirect error", e);
    }
  }
}
