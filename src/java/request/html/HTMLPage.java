package request.html;
import request.HttpPage;

 
/**
 *
 * @author Rob
 */

import rb.utils.tools.StringTool;
import rb.utils.tools.CharTool;

import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

    
    

public class HTMLPage extends HttpPage {
    
    private static Log log = LogFactory.getLog(HTMLPage.class);
    public static String MAIN_CONTENT_START = "<div id=\"mainContent\"";
    public static String DIV_END = "</div>";
    
    /**
     * From an inputted String (page), this gets all the content within a span with an id=tagId. 
     * It is currently limited in that the format of the tag must be <tag id="abc">
     * @param page The String in which the tag content will be
     * @param spanId The id of the span i.e. the in the id=""
     * @return String - the content of the span
     */
    public static String getHTMLTagContent(String page, String tagId, String tag)
    {
        
        String initialTagPart = "<" + tag + " id=\"" + tagId;
        //log.info("initial tag part = " + initialTagPart);
        
        
        // Now get the index within page of the end of the <span declaration
        String tagPart = StringTool.substringInclusive(page, initialTagPart, ">");
        
        //log.info("tag part is " + tagPart);
        
        // Now get from the initial string to the beginning of the first </span>
        if (tagPart==null)
        {
            return null;
        }
                
        String mainContent = StringTool.substringExclusive(page,tagPart , "</" + tag + ">", 1);
        //log.info("Main content is " + mainContent);
        
        
        if (mainContent!=null)
        {
            // Find how many other tag elements there are
            String[] num = mainContent.split("<" + tag);
        
            // If no more spans are found then fine. Otherwise need to get substring with n end tags
            if (num.length==1)
            {
                // No other tag elements of the same name - we're good
                
            }
            else
            {
                mainContent = StringTool.substringExclusive(page, tagPart, "</" + tag + ">", num.length);
            }
        }
        
        return mainContent;
    }
    
    /**
     * Gets the image within an anchor text within a page
     * @param page The String or page to search for this anchor and image
     * @param anchorHref The href="" content of the anchor
     * @return String - the image details
     */
    public static String getImageWithinAnchor(String page, String anchorHref)
    {
        log.debug("Text passed into getImageWithinAnchor is " + page);
        // First get anchor substring
        String anchorStart = "<a href=\"" + anchorHref;
        String anchorEnd = "</a>";
        String anchor = StringTool.substringExclusive(page, anchorStart, anchorEnd,1);
        log.debug("Anchor is " + anchor);
        String imageStart = "src=\"";
        String imageEnd = "\"";
        
        if (anchor!=null)
        {
            String image = StringTool.substringExclusive(anchor, imageStart, imageEnd,1);
        
            return image;
        }
        else
        {
            return null;
        }
    }
    
    /**
     * Gets the image within an anchor text within a page
     * @param page The String or page to search for this anchor and image
     * @param anchorHref The href="" content of the anchor
     * @return String - the image details
     */
    public static String getCleanAnchorHref(String anchorSection)
    {
        // First get anchor substring
        String hRefStart = "<a href=\"";
        String hRefEnd = "\"";
        String hRef = StringTool.substringExclusive(anchorSection, hRefStart, hRefEnd,1);
        
        
        if (hRef!=null)
        {
            hRef = getCleanedLink(hRef);
            return hRef;
        }
        else
        {
            return null;
        }
    }
    
    /**
     * Gets the image within an div within a page
     * @param page The String or page to search for this anchor and image
     * @param anchorHref The id of the div
     * @return String - the image details
     */
    public static String getImageWithinDiv(String page, String divId)
    {
        // First get div content
        String content = getHTMLTagContent(page, divId, "div");
        
        
        String imageStart = "src=\"";
        String imageEnd = "\"";
        
        if (content!=null)
        {
            String image = StringTool.substringExclusive(content, imageStart, imageEnd, 1);
            return image;
        }
        else
        {
            return null;
        }
        
    }
    
    
    public static String getCleanedImageWithinDiv(String page, String divId)
    {
        String image = getImageWithinDiv(page, divId);
        
        if (image!=null)
        {
            return getCleanedLink(image);
        }
        else
        {
            return null;
        }
    }
        
             
    
    
    /**
     * As for getImageWithinAnchor but will remove any .. so that the image is the reference from the root
     * @param page The String or page to search for this anchor and image
     * @param anchorHref The href="" content of the anchor
     * @return String - the image details without any ..
     */
    public static String getCleanedImageWithinAnchor(String page, String anchorHref)
    {
        String image = getImageWithinAnchor(page, anchorHref);
        log.debug("Image within anchor is " + image);
        log.debug("Anchor href is " + anchorHref);
        if (image!=null)
        {
            return getCleanedLink(image);
        }
        else
        {
            return null;
        }
    }
    
    /**
     * Returns link without any .. in
     * @param image
     * @return the URL extension only e.g. images/abc.jpg. Note it does not return the preceding /
     */
    public static String getCleanedLink(String link)
    {
        boolean b = false;
        String dotString = "../";
        
        // Does it contain the dot String              
        b = StringTool.contains(link, dotString);
        
        // If it does not then return the link untouched
        if (b==false)
        {
            return link;
        }
        
        // If it does then see if it contains another
        while (b==true)
        {
            String newDotString = dotString + "../";
            b = StringTool.contains(link, newDotString);
        
            if (b==false)
            {
                break;
            }
            else
            {
                dotString = newDotString;
            }
        }
        log.debug("Dot string is " + dotString);
        String returnString = StringTool.endSubstringExclusive(link, dotString);
        return returnString;
    }

    /**
     * Gets the page name from a url. e.g. solalex_1.php from http://abc.com/test/solalex_1.php
     * @param url
     * @return String - the page name. Returns null if cannot find a page name
     */
    public static String getPageNameFromURL(String url)
    {
        return StringTool.lastSubstringExclusive(url, "/");
    }
    
    public static String getURLRoot(String url)
    {
        return StringTool.allBeforeLastInstanceOf(url, "/") + "/";
    }
    
    /**
     * If you pass in pagename_1 or pagename1 it will return similar page names up until
     * maxNum. It must start with 1
     * @param pageName
     * @param maxNum - the number that you want it to go to
     * @return ArrayList of pagenames e.g. pagename_1, pagename_2. 
     * Note it does not include the original pagename.
     */
    public static ArrayList getSimilarNumberedPageNames(String pageName, int maxNum)
    {
        ArrayList pageNames = new ArrayList();
        
        int index = pageName.indexOf(1);
        if (index!=-1)
        {
                return null;
        }
                
        for (int i=2; i<=maxNum; i++)
        {
            String page = new String();
            page = pageName.replace('1', CharTool.intToChar(i));
            pageNames.add(page);
        }
        return pageNames;
    }
    
    /*
    public static String getDivContent(String page, String divId)
    {
        
        String initialDivPart = "<div id=\"" + divId;
        
        // Now get the index within page of the end of the <span declaration
        String endDivPart = StringTool.substring(page, initialDivPart, ">");
        String spanPart = initialDivPart + endDPart + ">";
        
        log.info("Span start tag is " + spanPart);
        
        // Now get from the initial string to the beginning of the first </span>
        String mainContent = StringTool.substring(page,spanPart , "</span>");
        log.info("Main content is " + mainContent);
        
        
        if (mainContent!=null)
        {
            // Find how many other spans there are
            String[] num = mainContent.split("<span");
        
            // If no more spans are found then fine. Otherwise need to get substring with n end tags
            if (num.length==1)
            {
                // No other divs - we're good
                
            }
            else
            {
                mainContent = StringTool.substringExclusive(page, divPart, "</div>", num.length);
            }
        }
        
        return mainContent;
    }
    */
    /**
     * This gets the alt text within an image tag. Assumes that you are passing in a small section
     * with one image tag
     * @param section
     * @return
     */
    public static String getAltTextWithinImageTag(String section)
    {
        section = StringTool.substringInclusive(section, "<img", ">");
        
        if (section!=null)
        {
            return StringTool.substringExclusive(section, "alt=\"", "\"", 1);
        }
        else
        {
            return null;
        }
        
    }
    
    
}
