/*
 * MG.java
 *
 * This class is used to get and set properties from the MG.properties file
 */

package objects;
import java.io.*;
import java.util.Properties;
import java.util.PropertyPermission;
import java.util.StringTokenizer;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * @author robb
 */
public final class Props
{
    
    /** Creates a new instance of MG */
    public Props()
    {
    }
    
    private static Log log = LogFactory.getLog(Props.class);
    private static Properties props;
    //private static native Properties initProperties(Properties props);

    /*
     * Loads properties. Used in InitServlet
     */
    public static void init(String fileLocation)
    {
        try
        {
            
            FileInputStream in = new FileInputStream(fileLocation);
            props = new Properties();
            Props.props=props;
            
            Props.props.load(in);
            
            in.close();
            
        }
        catch (FileNotFoundException fnfe)
        {
            log.fatal("Cannot find properties in location " + fileLocation);
        }
        catch (IOException io)
        {
            log.fatal("IOException in loading properties from " + fileLocation);
        }
    }
    
    

    /**
     * Gets the property from MGProperties, returning null if not there
     * 
     *
     * @param      key   the name of the MG property.
     * @param      def   a default value.
     * @return     the string value of the MG property,
     *             or null if there is no property with that key.
     *
     * @exception  SecurityException  if a security manager exists and its
     *             <code>checkPropertyAccess</code> method doesn't allow
     *             access to the specified system property.
     * @exception  NullPointerException if <code>key</code> is
     *             <code>null</code>.
     * @exception  IllegalArgumentException if <code>key</code> is empty.
     * @see        #setProperty)
     */
    public static String getProperty(String key) {
	checkKey(key);
	return props.getProperty(key);
    }

    /**
     * Gets the property from MGProperties
     * 
     *
     * @param      key   the name of the MG property.
     * @param      def   a default value.
     * @return     the string value of the MG property,
     *             or the default value if there is no property with that key.
     *
     * @exception  SecurityException  if a security manager exists and its
     *             <code>checkPropertyAccess</code> method doesn't allow
     *             access to the specified system property.
     * @exception  NullPointerException if <code>key</code> is
     *             <code>null</code>.
     * @exception  IllegalArgumentException if <code>key</code> is empty.
     * @see        #setProperty)
     */
    public static String getProperty(String key, String def) {
	checkKey(key);
	return props.getProperty(key, def);
        
    }

    /**
     * Sets the system property indicated by the specified key.
     * <p>
     * First, if a security manager exists, its
     * <code>SecurityManager.checkPermission</code> method
     * is called with a <code>PropertyPermission(key, "write")</code>
     * permission. This may result in a SecurityException being thrown.
     * If no exception is thrown, the specified property is set to the given
     * value.
     * <p>
     *
     * @param      key   the name of the system property.
     * @param      value the value of the system property.
     * @return     the previous value of the system property,
     *             or <code>null</code> if it did not have one.
     *
     * @exception  SecurityException  if a security manager exists and its
     *             <code>checkPermission</code> method doesn't allow
     *             setting of the specified property.
     * @exception  NullPointerException if <code>key</code> or 
     *             <code>value</code> is <code>null</code>.
     * @exception  IllegalArgumentException if <code>key</code> is empty.
     * @see        #getProperty
     * @see        java.lang.System#getProperty(java.lang.String)
     * @see        java.lang.System#getProperty(java.lang.String, java.lang.String)
     * @see        java.util.PropertyPermission
     * @see        SecurityManager#checkPermission
     * @since      1.2
     */
    public static String setProperty(String key, String value) {
	checkKey(key);
	
	return (String) props.setProperty(key, value);
    }

    /**
     * Removes the system property indicated by the specified key. 
     * <p>
     * First, if a security manager exists, its 
     * <code>SecurityManager.checkPermission</code> method
     * is called with a <code>PropertyPermission(key, "write")</code>
     * permission. This may result in a SecurityException being thrown.
     * If no exception is thrown, the specified property is removed.
     * <p>
     *
     * @param      key   the name of the system property to be removed. 
     * @return     the previous string value of the system property,
     *             or <code>null</code> if there was no property with that key.
     *
     * @exception  SecurityException  if a security manager exists and its  
     *             <code>checkPropertyAccess</code> method doesn't allow
     *              access to the specified system property.
     * @exception  NullPointerException if <code>key</code> is
     *             <code>null</code>.
     * @exception  IllegalArgumentException if <code>key</code> is empty.
     * @see        #getProperty
     * @see        #setProperty
     * @see        java.util.Properties
     * @see        java.lang.SecurityException
     * @see        java.lang.SecurityManager#checkPropertiesAccess()
     * @since 1.5
     */
    public static String clearProperty(String key) {
        checkKey(key);
        
        return (String) props.remove(key);
    }

    private static void checkKey(String key) {
        if (key == null) {
            throw new NullPointerException("key can't be null");
        }
        if (key.equals("")) {
            throw new IllegalArgumentException("key can't be empty");
        }
    }

    

    
        

    
}
