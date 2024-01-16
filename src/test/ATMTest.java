package test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ASimulatorSystem.Withdrawl;

public class ATMTest{
    Withdrawl withdrawl = new Withdrawl();

    ByteArrayOutputStream outstream = new ByteArrayOutputStream();
    ByteArrayOutputStream errstream = new ByteArrayOutputStream();
    PrintStream out = System.out;
    PrintStream err = System.err;

    @Before
    public void setUp(){
        System.setOut(new PrintStream(outstream));
    }

    @Test
    public void TC01() throws SQLException{
        withdrawl.withdrawMoney("100", "3951" , new Date());
        String expected_output = "Debited Successfully\r\n" + "Your current balance is: 1000\r\n";
        String actual_output = outstream.toString();
        assertEquals(expected_output, actual_output);
    }
    
    @Test
    public void TC02() throws SQLException{
        withdrawl.withdrawMoney("2222", "3951" , new Date());
        String expected_output = "Insufficient balance\r\n" + "Your current balance is: 900\r\n" + "Debited Successfully\r\n" 
        + "Your current balance is: 900\r\n";
        String actual_output = outstream.toString();
        assertEquals(expected_output, actual_output);
    }
    
    
    @After
    public void tearDown(){
        System.setOut(out);
        System.setErr(err);
    }
}
