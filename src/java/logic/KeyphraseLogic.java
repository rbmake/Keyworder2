/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic;

import dao.KeyphraseDAO;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import objects.Domain;
import objects.Keyphrase;



/**
 *
 * @author robbrown
 */
public class KeyphraseLogic
{

    private static Log log = LogFactory.getLog(KeyphraseLogic.class);
    /**
     * Gets a list of keywords for a domain
     * @param domain
     * @return
     */
    public static ArrayList getKeyphrases(Domain domain)
    {

        ArrayList al = KeyphraseDAO.getKeyphrasesForDomain(domain.getId());
        if (al!=null)
        {
            log.info("Found " + al.size() + " keywords.");
        }

        return al;
    }

    public static int deleteKeyphrase(Integer keyphraseId)
    {
        log.debug("In KeyphraseLogic and about to delete keyphrase " + keyphraseId);
        return KeyphraseDAO.deleteKeyphrase(keyphraseId);
    }


    /**
     * Gets a list of active keywords for a domain
     * @param domain
     * @return
     */
    public static ArrayList getActiveKeyphrases(Domain domain)
    {

        ArrayList al = KeyphraseDAO.getActiveKeyphrasesForDomain(domain.getId());
        if (al!=null)
        {
            log.info("Found " + al.size() + " keywords.");
        }

        return al;
    }

    public static void setActiveKeyphrases(Domain domain, ArrayList activeKeyphrases)
    {
        // First off set all keyphrases to inactive
        KeyphraseDAO.deactivateAllKeyphrases(domain.getId());

        // Then set those that have been passed in to active
        KeyphraseDAO.setActiveKeyphrases(domain.getId(), activeKeyphrases);

    }

    public static Keyphrase getKeyphraseFromId(long keyphraseId)
    {
        Keyphrase k = KeyphraseDAO.getKeyphraseById(keyphraseId);
        Domain d = DomainLogic.getDomainFromId(k.getDomain().getId());
        k.setDomain(d);
        return k;
    }

    public static void addKeyphraseToDomain(Domain d, String keyphrase)
    {
        KeyphraseDAO.insertKeyphrase(keyphrase, d.getId());
    }

    public static Keyphrase getKeyphraseForDomain(String keyphrase, Domain d)
    {
        // Returns null if cannot find it
        return KeyphraseDAO.getKeyphraseForDomain(d.getId(), keyphrase);
    }

    public static boolean doesKeyphraseAlreadyExistForDomain(String keyphrase, Domain d)
    {
        Keyphrase kp = getKeyphraseForDomain(keyphrase,d);

        if (kp==null)
        {
            // Nothing returned so keyphrase does not already exist for this domain
            return false;
        }
        else
        {
            log.debug("Keyphrase with id " + kp.getId() + " and name " + kp.getName() + " already exists.");
            return true;
        }
        

    }



}
