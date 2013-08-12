/*
 * Page.java
 */

package request;

import rb.utils.tools.StringTool;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import objects.Props;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author robb
 */
public class HttpPage
{
    
    private static Log log = LogFactory.getLog(HttpPage.class);
    
    
    
    /**
     * Gets the contents of a URL page using the GET method
     * @param url
     * @return String - contents of the page
     */
    public static String getPage(String url)
    {
        
        // Create client and set the post URL to HSBC secure ePayments
        HttpClient httpclient = new HttpClient();

        // Check if need to record timings
        String proxy = Props.getProperty("useProxy");
        boolean bProxy = new Boolean(proxy);

        if (bProxy)
        {
            String proxyHost = Props.getProperty("proxyHost");
            String proxyPort = Props.getProperty("proxyPort");

            if (proxyPort!=null && proxyPort!=null)
            {
                try
                {
                    int iProxyPort = new Integer(proxyPort).intValue();
                    httpclient.getHostConfiguration().setProxy(proxyHost, iProxyPort);
                }
                catch (Exception e)
                {
                    log.error("Port was not numeric");
                }
            }
        }

                // ONLY USE THIS IF USERNAME AND PASSWORD REQUIRED
                //httpclient.getState().setProxyCredentials("my-proxy-realm", " myproxyhost",
                //new UsernamePasswordCredentials("my-proxy-username", "my-proxy-password"));

        
        GetMethod get = new GetMethod(url);
        
        log.debug("In get page. Url to get is " + url);
        try
        {
            int result = httpclient.executeMethod(get);

            log.debug("Just received page in HttpPage");
            // Display status code
            //log.debug("Response status code: " + result);
            // Display response
            //log.debug("Response body: " + get.getResponseBodyAsString());

            // INPUT STREAM TAKES 30 seconds per call!!!
            /*
            InputStream is = get.getResponseBodyAsStream();
            String pageBody = StringTool.getStringFromInputStream(is);
            */

            String pageBody = get.getResponseBodyAsString();



            return pageBody;
        }
        catch (IOException ioe)
        {
            log.error("IO Exception: " + ioe.getMessage());
            // Need to show an error page
            return null;
        }
        
        finally
        {
            // Release current connection to the connection pool
            get.releaseConnection();
        }
       
    } 
    
    /**
     * Gets the contents of a URL page using the GET method and sets the userAgent 
     * @param url
     * @return String - contents of the page
     */
    public static String getPage(String url, String userAgent)
    {
        
        // Create client
        HttpClient httpclient = new HttpClient();
        httpclient.getParams().setParameter("http.useragent", userAgent);
         // Check if need to record timings
        String proxy = Props.getProperty("useProxy");
        boolean bProxy = new Boolean(proxy);
        if (bProxy)
        {
            String proxyHost = Props.getProperty("proxyHost");
            String proxyPort = Props.getProperty("proxyPort");

            if (proxyPort!=null && proxyPort!=null)
            {
                try
                {
                    int iProxyPort = new Integer(proxyPort).intValue();
                    httpclient.getHostConfiguration().setProxy(proxyHost, iProxyPort);
                }
                catch (Exception e)
                {
                    log.error("Port was not numeric");
                }
            }
        }

        GetMethod get = new GetMethod(url);
        
        log.debug("In get page. Url to get is " + url);
        try
        {
            int result = httpclient.executeMethod(get);
            // Display status code
            //log.debug("Response status code: " + result);
            // Display response
            //log.debug("Response body: " + get.getResponseBodyAsString());
            String pageBody = get.getResponseBodyAsString();
            return pageBody;
        }
        catch (IOException ioe)
        {
            log.debug("IO Exception: " + ioe.getMessage());
            // Need to show an error page
            return null;
        }
        
        finally
        {
            // Release current connection to the connection pool
            get.releaseConnection();
        }
       
    } 
    
    
    /**
     * Gets the contents of a URL page using the getAsResponseBody method
     * **** NOT TESTED **********
     * @param url
     * @return String - contents of the page
     */
    public static String getPageUsingResponseBody(String url)
    {
        
        // Create client and set the post URL to HSBC secure ePayments
        HttpClient httpclient = new HttpClient();
        GetMethod get = new GetMethod(url);
        
        log.debug("In get page. Url to get is " + url);
        try
        {
            int result = httpclient.executeMethod(get);
            // Display status code
            //log.debug("Response status code: " + result);
            // Display response
            //log.debug("Response body: " + get.getResponseBodyAsString());
            byte is[] = get.getResponseBody();
            String pageBody = new String(is, "US-ASCII");
            
            return pageBody;
        }
        catch (IOException ioe)
        {
            log.debug("IO Exception: " + ioe.getMessage());
            // Need to show an error page
            return null;
        }
        
        finally
        {
            // Release current connection to the connection pool
            get.releaseConnection();
        }
       
    } 
    
    
}
