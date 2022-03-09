package ca.mcgill.ecse321.GroceryStoreBackend.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;


import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.OwnerRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Owner;


@ExtendWith(MockitoExtension.class)
public class TestOwnerService {



  @Mock
  private OwnerRepository ownerRepo;
  @InjectMocks
  private OwnerService ownerService;

  private static final String OWNER_USERNAME = "TestOwner";
  private static final String OWNER_PASSWORD = "TestPassword1";
  private static final String OWNER_EMAIL = "admin@grocerystore.com";


  @BeforeEach
  public void setMockOutput() {

    lenient().when(ownerRepo.findByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
      if (invocation.getArgument(0).equals(OWNER_USERNAME)) {
        Owner owner = new Owner();
        owner.setName(OWNER_USERNAME);
        owner.setPassword(OWNER_PASSWORD);
        return owner;
      } else {
        return null;
      }
    });

    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
      return invocation.getArgument(0);
    };
    lenient().when(ownerRepo.save(any(Owner.class))).thenAnswer(returnParameterAsAnswer);
  }

  @Test
  public void testCreateOwner() {
    assertEquals(0, ownerService.getAllOwners().size());
    String username = "nameTest";
    String password = "passwordTest1";
    String email = "admin@grocerystore.com";
    Owner owner = null;
    try {
      owner = ownerService.createOwner(email, password, username);
    } catch (IllegalArgumentException e) {
      fail();
    }
    assertNotNull(owner);
    assertEquals(username, owner.getName());
    assertEquals(password, owner.getPassword());
    assertEquals(email, owner.getEmail());
  }


  @Test
  public void testFindOwner() {
    assertEquals(0, ownerService.getAllOwners().size());

    Owner owner = null;
    try {
      owner = ownerService.getOwner(OWNER_EMAIL);
    } catch (IllegalArgumentException e) {
      fail();
    }
    assertNotNull(owner);
    assertEquals(owner.getName(), OWNER_USERNAME);
    assertEquals(owner.getPassword(), OWNER_PASSWORD);
    assertEquals(owner.getEmail(),OWNER_EMAIL);

  }

  @Test
  public void testCreateOwnerErrorBlankUsername() {
    assertEquals(0, ownerService.getAllOwners().size());
    String username = "";
    String password = "Password123";
    String email = "admin@grocerystore.com";
    Owner owner = null;
    String error = "";
    try {
      owner = ownerService.createOwner(email, password, username);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(owner);
    assertEquals("Username cannot be blank", error);
  }

  @Test
  public void testCreateOwnerErrorBlankPassword() {
    assertEquals(0, ownerService.getAllOwners().size());
    String username = "newUsername1";
    String password = "";
    String email = "admin@grocerystore.com";
    Owner owner = null;
    String error = "";
    try {
      owner = ownerService.createOwner(email, password, username);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(owner);
    assertEquals("Password cannot be blank", error);
  }

  @Test
  public void testCreateOwnerWithInvalidPasswordLessThan8Chars() {
    String username = "nameTest";
    String invalidPassword = "hey";
    String email = "admin@grocerystore.com";
    Owner owner = null;
    String error = null;

    try {
      owner = ownerService.createOwner(email, invalidPassword, username);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(owner);
    assertEquals("Password must have at least 4 characters", error);
  }

  @Test
  public void testCreateOwnerWithInvalidPasswordMoreThan20Chars() {
    String username = "nameTest";
    String invalidPassword = "ThisisaLengthmorethan20characters";
    String email = "admin@grocerystore.com";
    Owner owner = null;
    String error = null;
    try {
      owner = ownerService.createOwner(email, invalidPassword, username);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(owner);
    assertEquals("Password must not have more than 20 characters", error);
  }

  @Test
  public void testCreateOwnerWithInvalidPasswordNoUpperCase() {
    String username = "nameTest";
    String invalidPassword = "noupper1";
    String email = "admin@grocerystore.com";
    Owner owner = null;
    String error = null;
    try {
      owner = ownerService.createOwner(email, invalidPassword, username);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(owner);
    assertEquals("Password must contain at least one uppercase character", error);
  }

  @Test
  public void testCreateOwnerWithInvalidPasswordNoLowerCase() {
    String username = "nameTest";
    String invalidPassword = "NOLOWER1";
    String email = "admin@grocerystore.com";
    Owner owner = null;
    String error = null;
    try {
      owner = ownerService.createOwner(email, invalidPassword, username);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(owner);
    assertEquals("Password must contain at least one lowercase character", error);
  }

  @Test
  public void testCreateOwnerWithInvalidPasswordNoNumericalValue() {
    String username = "nameTest";
    String invalidPassword = "Nonumbers";
    String email = "admin@grocerystore.com";
    Owner owner = null;
    String error = null;

    try {
      owner = ownerService.createOwner(email, invalidPassword, username);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(owner);
    assertEquals("Password must contain at least one numeric character", error);
  }
  
  @Test
  public void testUpdateOwnerPassword() {
  Owner owner = null;

  try {
      owner = ownerService.updateOwner(OWNER_EMAIL,"newPassword123");
  }catch(IllegalArgumentException e) {
      fail();
  }
  assertNotNull(owner);
  assertEquals(OWNER_USERNAME,owner.getName());
  assertEquals("newPassword123",owner.getPassword());
  assertEquals(OWNER_EMAIL,owner.getEmail());
}
  
  @Test
  public void testUpdateSamePassword() {
  Owner owner = null;

  try {
      owner = ownerService.updateOwner(OWNER_EMAIL,OWNER_PASSWORD);
  }catch(IllegalArgumentException e) {
      fail();
  }
  assertNotNull(owner);
  assertEquals(OWNER_USERNAME,owner.getName());
  assertEquals("newPassword123",owner.getPassword());
  assertEquals(OWNER_EMAIL,owner.getEmail());
  }
  
  @Test
  public void testUpdateOwnerWithInvalidPasswordLessThan8Chars() {
      Owner owner =null;
      String invalidPassword ="hey";
      String error = "";

      try {
      owner = ownerService.updateOwner(OWNER_EMAIL,invalidPassword);
      } catch (IllegalArgumentException e) {
          error = e.getMessage();
      }
      assertNull(owner);
      assertEquals("Password must have at least 4 characters",error);
  }
  
  @Test
  public void testUpdateOwnerWithInvalidPasswordMoreThan20Chars() {
      Owner owner =null;
      String invalidPassword ="invalidbecauseitisMorethan20";
      String error = "";

      try {
      owner = ownerService.updateOwner(OWNER_EMAIL,invalidPassword);
      } catch (IllegalArgumentException e) {
          error = e.getMessage();
      }
      assertNull(owner);
      assertEquals("Password must not have more than 20 characters",error);
  }
  
  @Test
  public void testUpdateOwnerWithInvalidPasswordNoUpperCase() {
      Owner owner =null;
      String invalidPassword ="noouppercasepass2";
      String error = "";

      try {
      owner = ownerService.updateOwner(OWNER_EMAIL,invalidPassword);
      } catch (IllegalArgumentException e) {
          error = e.getMessage();
      }
      assertNull(owner);
      assertEquals("Password must contain at least one uppercase character",error);
  }
  
  @Test
  public void testUpdateOwnerWithInvalidPasswordNoLowerCase() {
      Owner owner =null;
      String invalidPassword ="ALLCAPSNOLOWER1";
      String error = "";

      try {
      owner = ownerService.updateOwner(OWNER_EMAIL,invalidPassword);
      } catch (IllegalArgumentException e) {
          error = e.getMessage();
      }
      assertNull(owner);
      assertEquals("Password must contain at least one lowercase character",error);
  }
  
  @Test
  public void testUpdateOwnerWithInvalidPasswordNoNumericalValue() {
      Owner owner =null;
      String invalidPassword ="nonumbersA";
      String error = "";

      try {
      owner = ownerService.updateOwner(OWNER_EMAIL,invalidPassword);
      } catch (IllegalArgumentException e) {
          error = e.getMessage();
      }
      assertNull(owner);
      assertEquals("Password must contain at least one numeric character",error);
  }   
  
  @Test
  public void testGetExistingOwner() {
      assertEquals(OWNER_USERNAME, ownerService.getOwner(OWNER_EMAIL).getName());
  }

  @Test
  public void testGetNonExistingOwner() {
      assertNull(ownerService.getOwner("nonExistingOwner@shop.com"));
  }
}
