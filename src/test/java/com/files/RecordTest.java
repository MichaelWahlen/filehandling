package test.java.com.files;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import main.java.com.files.DataStatus;
import main.java.com.files.Record;

class RecordTest {
	
	Record<String> row;
	
	@BeforeEach
	public void setUp() throws Exception {
		row = new Record<String>();
		row.add("Alia");
		row.add("Iacta");
		row.add("Est");		
	}	
	
	@Test
	@DisplayName("Test initial setup")
	void initSetup() {
		assertEquals(row.getAt(0),"Alia");	
		assertEquals(row.getAt(1),"Iacta");
		assertEquals(row.getAt(2),"Est");
		assertEquals(row.getSize(),3);
		assertNull(row.getAt(-1));
		assertNull(row.getAt(99));
		assertEquals(row.getStatus(),DataStatus.UNCHANGED_OK);
	}
	
	@Test
	@DisplayName("Test remove")
	void remove() {
		assertEquals(row.getAt(1),"Iacta");
		row.remove(1);
		assertEquals(row.getSize(),2);
		row.remove(4);
		assertEquals(row.getSize(),2);
		row.remove(-1);
		assertEquals(row.getSize(),2);
		assertEquals(row.getAt(1),"Est");
		assertNull(row.getAt(2));
		assertEquals(row.getStatus(),DataStatus.UNCHANGED_OK);
	}
	
	@Test
	@DisplayName("Test add")
	void add() {
		row.add("Deus");
		row.add("Volent");
		assertEquals(row.getSize(),5);
		assertEquals(row.getAt(1),"Iacta");
		assertEquals(row.getAt(4),"Volent");
		assertEquals(row.getStatus(),DataStatus.UNCHANGED_OK);
	}
	
	@Test
	@DisplayName("Get all")
	void getAll() {
		List<String> retrievedList = row.getAll();
		assertEquals(retrievedList.get(0),"Alia");
		assertEquals(retrievedList.get(1),"Iacta");
		assertEquals(retrievedList.get(2),"Est");		
	}
	
	@Test
	@DisplayName("Valid update")
	void validUpdate() {
		row.changeValueAt(0, "Alia", true);
		assertEquals(row.getStatus(),DataStatus.UNCHANGED_OK);
		assertEquals(row.getSize(),3);
		row.changeValueAt(1, "NewValue", true);
		assertEquals(row.getStatus(),DataStatus.CHANGED_OK);
		row.changeValueAt(10, "NewValue", true);
		assertEquals(row.getSize(),3);
	}
	
	@Test
	@DisplayName("Invalid update: \"╯°□°）╯\"")
	void inValidUpdate() {
		row.changeValueAt(0, "Alia", false);
		assertEquals(row.getStatus(),DataStatus.UNCHANGED_NOK);
		row.changeValueAt(1, "NewValue", false);
		assertEquals(row.getStatus(),DataStatus.CHANGED_NOK);		
	}
	
	@Test
	@DisplayName("Setting prescribed length, to shorter")
	void settingLengthToShorter() {
		row.setPrescribedSize(1);
		row.getAll().get(0);
		assertThrows(IndexOutOfBoundsException.class,				
				()->{row.getAll().get(1);});
	}
	
	@Test
	@DisplayName("Setting prescribed length, to longer")
	void settingLengthToLonger() {
		row.setPrescribedSize(5);
		assertNull(row.getAt(4));
	}
	
	@Test
	@DisplayName("Remove and valid")
	void removeAndValidate() {
		row.changeValueAt(0, "Alia", false);
		assertEquals(row.getStatus(),DataStatus.UNCHANGED_NOK);
		row.remove(0);
		assertEquals(row.getStatus(),DataStatus.UNCHANGED_OK);
	}	
	
	@Test
	@DisplayName("Move forward and backward")
	void moveForwardAndBackward() {
		row.changeValueAt(0, "Abc", true);
		row.changeValueAt(0, "Def", false);
		row.changeValueAt(0, "Ghi", false);
		row.changeValueAt(1, "Abc", true);
		row.changeValueAt(1, "Def", false);		
		assertEquals(row.getAt(0),"Ghi");
		assertEquals(row.getAt(1),"Def");
		assertEquals(row.getAt(2),"Est");
		assertEquals(row.getStatus(),DataStatus.CHANGED_NOK);
		row.previousValue();
		row.previousValue();
		row.previousValue();
		assertEquals(row.getAt(0),"Alia");
		assertEquals(row.getAt(1),"Iacta");
		assertEquals(row.getAt(2),"Est");
		assertEquals(row.getStatus(),DataStatus.UNCHANGED_OK);
		row.nextValue();
		assertEquals(row.getAt(0),"Abc");
		assertEquals(row.getAt(1),"Abc");
		assertEquals(row.getAt(2),"Est");
		assertEquals(row.getStatus(),DataStatus.CHANGED_OK);
		row.changeValueAt(0, "Alia", true);
		row.changeValueAt(1, "Iacta", true);
		row.nextValue();
		row.nextValue();
		row.previousValue();
		row.previousValue();
		row.previousValue();
		row.previousValue();
		row.nextValue();
		row.nextValue();
		row.nextValue();
		row.nextValue();
		row.nextValue();
		assertEquals(row.getAt(0),"Alia");
		assertEquals(row.getAt(1),"Iacta");
		assertEquals(row.getAt(2),"Est");
		assertEquals(row.getStatus(),DataStatus.UNCHANGED_OK);
	}	
}
