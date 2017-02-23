package io.muic.ooc.webapp;

import io.muic.ooc.webapp.service.SecurityService;
import java.io.File;
import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.commons.codec.digest.DigestUtils;

public class Webapp {

    public static void main(String[] args) {

        // tomCat Connection
        String docBase = "src/main/webapp/";
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(80);
        SecurityService securityService = new SecurityService();

        ServletRouter servletRouter = new ServletRouter();
        servletRouter.setSecurityService(securityService);

        System.out.println(DigestUtils.sha256Hex("root"+"salt"));

        Context ctx;
        try {
            ctx = tomcat.addWebapp("/", new File(docBase).getAbsolutePath());
            servletRouter.init(ctx);
            tomcat.start();
            tomcat.getServer().await();
        } catch (ServletException | LifecycleException ex) {
            ex.printStackTrace();
        }

    }
}
