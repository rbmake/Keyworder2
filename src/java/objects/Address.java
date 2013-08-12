/*
 * Address.java
 *
 * Created on 25 October 2006, 21:37
 *
 * 
 */

package objects;
import java.io.Serializable;

/**
 *
 * @author robb
 */
public class Address implements Serializable  {
    
    private Long id;
    private String address1;
    private String address2;
    private String address3;
    private String city;
    private String postcode;
    private Country country;
    private String firstName;
    private String surname;
    private boolean store;
    private int type;
    private String fullAddress;
    
    
    /** Creates a new instance of Address */
    public Address() {
    }

    public String getAddress1()
    {
        return address1;
    }

    public void setAddress1(String address1)
    {
        this.address1 = address1;
    }

    public String getAddress2()
    {
        return address2;
    }

    public void setAddress2(String address2)
    {
        this.address2 = address2;
    }

    public String getAddress3()
    {
        return address3;
    }

    public void setAddress3(String address3)
    {
        this.address3 = address3;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getPostcode()
    {
        return postcode;
    }

    public void setPostcode(String postcode)
    {
        this.postcode = postcode;
    }

    
    public boolean isStore()
    {
        return store;
    }

    public void setStore(boolean store)
    {
        this.store = store;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public Long getId()
    {
        return id;
    }
   
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public void setId(long id)
    {
        this.id = new Long(id);
    }


    public Country getCountry()
    {
        return country;
    }

    /**
     * Sets the Country object within the Address 
     * 
     * @param country a Country object
     * @return void
     */
    public void setCountry(Country country)
    {
        this.country = country;
    }
    
    /**
     * Sets the Country object within the Address using data loaded from the database
     * at start up
     * 
     * @param countryId the id of the country in the database
     * @return void
     */
    public void setCountry(int countryId)
    {
        country = Data.getCountry(countryId);
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }
    
    public String getFullAddress()
    {
        fullAddress = getAddress1() + "<br>";
        if ((getAddress2()!=null) && (getAddress2().length()>0))
        {
            fullAddress = fullAddress + getAddress2() + "<br>";
        }
        if ((getCity()!=null) && (getCity().length()>0))
        {
            fullAddress = fullAddress + getCity() + ", ";
        }
        if ((getPostcode()!=null) && (getPostcode().length()>0))
        {
            fullAddress = fullAddress + getPostcode() + "<br>";
        }
        if ((getAddress3()!=null) && (getAddress3().length()>0))
        {
            fullAddress = fullAddress + getAddress3() + "<br>";
        }
        if ((getCountry()!=null) && (getCountry().getName().length()>0))
        {
            fullAddress = fullAddress + getCountry().getName();
        }
        return fullAddress;
    }
    
    
}
