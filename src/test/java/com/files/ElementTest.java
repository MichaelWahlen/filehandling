package test.java.com.files;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import main.java.com.files.Cell;
import main.java.com.files.DataStatus;

class ElementTest {
		
	Cell<String> atom;
	
	  @BeforeEach
	  public void setUp() throws Exception {
	    atom = new Cell<String>("FirstValue");
	    atom.changeToValue("SecondValue", true);
	    atom.changeToValue("ThirdValue", false);
	  }	
	
	@Test
	@DisplayName("Test initial setup")
	void testInitSetup() {
		assertEquals(atom.getOriginalValue(),"FirstValue");	
		assertEquals(atom.getCurrentValue(),"ThirdValue");
		assertEquals(atom.getStatus(),DataStatus.CHANGED_NOK);	
	}
	
	@Test
	@DisplayName("Adding a changed and not valid value")
	void addChangedValid() {
		atom.changeToValue("FourthValue", false);
		assertEquals(atom.getStatus(),DataStatus.CHANGED_NOK);
	}
	
	@Test
	@DisplayName("Adding a changed and valid value")
	void addChangedNotValid() {
		atom.changeToValue("FourthValue", true);		
		assertEquals(atom.getStatus(),DataStatus.CHANGED_OK);
	}
	
	@Test
	@DisplayName("Adding a not-changed and not-valid value")
	void addNotChangedNotValid() {
		atom.changeToValue("FirstValue", false);
		assertEquals(atom.getStatus(),DataStatus.UNCHANGED_NOK);
	}
	
	@Test
	@DisplayName("Adding a not-changed and valid value")
	void addNotChangedValid() {
		atom.changeToValue("FirstValue", true);
		assertEquals(atom.getStatus(),DataStatus.UNCHANGED_OK);
	}	
	
	@Test
	@DisplayName("Cycling and adding a value in the middle of list")
	void cycleForwardAndCommit() {
		assertEquals(atom.getCurrentValue(),"ThirdValue");
		assertEquals(atom.getStatus(),DataStatus.CHANGED_NOK);	
		atom.changeToPreviousValue();
		assertEquals(atom.getCurrentValue(),"SecondValue");
		assertEquals(atom.getStatus(),DataStatus.CHANGED_OK);	
		atom.changeToPreviousValue();
		assertEquals(atom.getCurrentValue(),"FirstValue");
		assertEquals(atom.getStatus(),DataStatus.UNCHANGED_OK);
		atom.changeToPreviousValue();
		assertEquals(atom.getCurrentValue(),"FirstValue");
		assertEquals(atom.getStatus(),DataStatus.UNCHANGED_OK);
		atom.changeToNextValue();
		assertEquals(atom.getCurrentValue(),"SecondValue");
		assertEquals(atom.getStatus(),DataStatus.CHANGED_OK);		
		atom.changeToValue("FourthValue", true);
		assertEquals(atom.getCurrentValue(),"FourthValue");
		assertEquals(atom.getStatus(),DataStatus.CHANGED_OK);
		atom.changeToNextValue();
		assertEquals(atom.getCurrentValue(),"FourthValue");
		assertEquals(atom.getStatus(),DataStatus.CHANGED_OK);
		atom.changeToValue("FirstValue", true);
		assertEquals(atom.getCurrentValue(),"FirstValue");
		assertEquals(atom.getStatus(),DataStatus.UNCHANGED_OK);
		atom.changeToPreviousValue();
		assertEquals(atom.getCurrentValue(),"FourthValue");
		assertEquals(atom.getStatus(),DataStatus.CHANGED_OK);
		atom.changeToPreviousValue();
		assertEquals(atom.getCurrentValue(),"SecondValue");
		assertEquals(atom.getStatus(),DataStatus.CHANGED_OK);		
	}
	
	

}
