package ca.mcgill.ecse321.GroceryStoreBackend.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.util.*;


import javax.persistence.*;

// line 11 "model.ump"
// line 161 "model.ump"

@Entity
@Table(name = "Accounts")
public abstract class Person
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

//  private static Map<String, Person> personsByEmail = new HashMap<String, Person>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Person Attributes
  protected String email;
  protected String password;
  protected String name;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Person(String aEmail, String aPassword, String aName)
  {
    password = aPassword;
    name = aName;
    email = aEmail;
  }
  
  public Person() {
      
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void setEmail(String aEmail)
  {
      this.email = aEmail;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  @Id
  public String getEmail()
  {
    return email;
  }
  
//  /* Code from template attribute_GetUnique */
//  public static Person getWithEmail(String aEmail)
//  {
//    return personsByEmail.get(aEmail);
//  }
//  
//  /* Code from template attribute_HasUnique */
//  public static boolean hasWithEmail(String aEmail)
//  {
//    return getWithEmail(aEmail) != null;
//  }

  public String getPassword()
  {
    return password;
  }

  public String getName()
  {
    return name;
  }

  public void delete()
  {
    //personsByEmail.remove(getEmail());
    this.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "email" + ":" + getEmail()+ "," +
            "password" + ":" + getPassword()+ "," +
            "name" + ":" + getName()+ "]";
  }
}