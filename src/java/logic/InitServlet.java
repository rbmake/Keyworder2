/*
 * InitServlet.java
 *
 * Created on 30 October 2006, 21:54
 */

package logic;

import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.net.InetAddress;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import objects.Props;
import objects.Data;


import constants.Constants;

/**
 *
 * @author Rob Brown
 */
public class InitServlet extends HttpServlet {
    
    private static Log log = LogFactory.getLog(InitServlet.class);

    public void init()
    {

        // Load data from the Properties file
        // This must happen first since some are needed in MGData init
        loadProperties();
        
        log.info("Loaded Properties");

        // Load data into Data static object e.g months, years
        Data.init(getServletContext());
        
        //setRoots();
       
        // Set the templates and images directory
        //setDirectories();
        
        
    }
    
    private void setRoots()
    {
        
        String root = null;
        String secureRoot = null;
        String hostName = null;
        
        try
        {
            hostName = InetAddress.getLocalHost().getHostName();
        }
        catch (Exception e)
        {
            log.fatal(e.getMessage());
        }
        
        // If does not equal -1 then the live uri is found
        // set root and secure root to live ones
        if (hostName.indexOf(Props.getProperty (Constants.KEY_LIVE_URI))!=Constants.NOT_FOUND)
        {
            root = Props.getProperty (Constants.KEY_LIVE_ROOT);
            secureRoot = Props.getProperty (Constants.KEY_LIVE_SECURE_ROOT);
            Data.set(Constants.BOX, Constants.LIVE);
            log.info("Live root paths set up successfully.");
        }
        // If does not equal -1 then the developer uri is found
        // set root and secure root to developer ones
        else if (hostName.indexOf(Props.getProperty (Constants.KEY_DEVELOPER_URI))!=Constants.NOT_FOUND)
        {
            root = Props.getProperty (Constants.KEY_DEVELOPER_ROOT);
            secureRoot = Props.getProperty (Constants.KEY_DEVELOPER_SECURE_ROOT);
            Data.set(Constants.BOX, Constants.DEVELOPMENT);
            log.info("Developer root paths set up successfully.");
        }
        else
        {
            log.fatal("Not on live or dev! Hostname is " + hostName + ". Reconfiguration required.");
        }
        
        
        log.info("Root path is " + root);
        log.info("Secure root is " + secureRoot);
        
        Props.setProperty(Constants.KEY_ROOT, root);
        Props.setProperty(Constants.KEY_SECURE_ROOT, secureRoot);
    }
    
    private void loadProperties()
    {
        // create and load default properties
        Properties defaultProps = new Properties();
        String partialPath = getInitParameter("Properties");
        String fullPath = getServletContext().getRealPath(partialPath);
        Props.init(fullPath);
        
   }
    
    
    
   /**
    * This sets the Templates directory in the form
     * C:/TheModernGardener.... or /opt/etc/...
    */
   /*private void setDirectories()
   {
        String templatesPartialPath = Data.getProperty(Constants.KEY_TEMPLATES_DIRECTORY);
        String imagesPartialPath = Data.getProperty(Constants.KEY_IMAGES_DIRECTORY);
        
        String templatesFullPath = getServletContext().getRealPath(templatesPartialPath);
        String imagesFullPath = getServletContext().getRealPath(imagesPartialPath);
        MG.setProperty(Constants.KEY_TEMPLATES_DIRECTORY,templatesFullPath);
        MG.setProperty(Constants.KEY_FULL_IMAGES_DIRECTORY, imagesFullPath);
        
        log.debug("Templates directory partial path = " + templatesPartialPath);
        log.debug("Templates directory full path = " + templatesFullPath);
        log.debug("Images directory partial path = " + imagesPartialPath);
        log.debug("Images directory full path = " + imagesFullPath);
        
        
        
   }*/
}

