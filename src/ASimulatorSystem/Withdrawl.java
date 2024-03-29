
package ASimulatorSystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Withdrawl extends JFrame implements ActionListener {

    JTextField t1;
    JTextField t2;

    JButton b1;
    JButton b2;
    JButton b3;

    JLabel l1;
    JLabel l2;
    JLabel l3;

    String pin;

    public Withdrawl(String pin) {
        this.pin = pin;
        ImageIcon i1 = new ImageIcon(
                ClassLoader.getSystemResource("ASimulatorSystem/icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(
                1000, 1180, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, 960, 1080);
        add(l3);

        l1 = new JLabel("MAXIMUM WITHDRAWAL IS RS.10,000");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));

        l2 = new JLabel("PLEASE ENTER YOUR AMOUNT");
        l2.setForeground(Color.WHITE);
        l2.setFont(new Font("System", Font.BOLD, 16));

        t1 = new JTextField();
        t1.setFont(new Font("Raleway", Font.BOLD, 25));

        b1 = new JButton("WITHDRAW");
        b2 = new JButton("BACK");

        setLayout(null);

        l1.setBounds(190, 350, 400, 20);
        l3.add(l1);

        l2.setBounds(190, 400, 400, 20);
        l3.add(l2);

        t1.setBounds(190, 450, 330, 30);
        l3.add(t1);

        b1.setBounds(390, 588, 150, 35);
        l3.add(b1);

        b2.setBounds(390, 633, 150, 35);
        l3.add(b2);

        b1.addActionListener(this);
        b2.addActionListener(this);

        setSize(960, 1080);
        setLocation(500, 0);
        setUndecorated(true);
        setVisible(true);
    }

    public Withdrawl() {
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String amount = t1.getText();
            Date date = new Date();
            if (ae.getSource().equals(b1)) {
                if (t1.getText().equals("")) {
                    JOptionPane.showMessageDialog(
                            null, "Please enter the Amount to you want to Withdraw");
                } else {
                    withdrawMoney(amount, pin, date);
                }
            } else if (ae.getSource().equals(b2)) {
                setVisible(false);
                new Transactions(pin).setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error: " + e);
        }

    }

    public void withdrawMoney(String amount, String pin, Date date) throws SQLException {

        Conn c1 = new Conn();

        ResultSet rs = c1.s.executeQuery("select * from bank where pin = '" + pin + "'");
        int balance = 1000;
        while (rs.next()) {
            if (rs.getString("type").equals("Deposit")) {
                balance += Integer.parseInt(rs.getString("amount"));
            } else {
                balance -= Integer.parseInt(rs.getString("amount"));
            }
        }
        if (balance < Integer.parseInt(amount)) {
            JOptionPane.showMessageDialog(
                    null, "Insuffient Balance");
            System.out.println("Insufficient balance");
            System.out.println("Your current balance is: " + balance);
        }
        
        c1.s.executeUpdate(
                "insert into bank values('" + pin + "', '" +
                        date + "', 'Withdrawl', '" +
                        amount + "')");
        JOptionPane.showMessageDialog(
                null, "Rs. " + amount + " Debited Successfully");
        System.out.println("Debited Successfully");
        System.out.println("Your current balance is: " + balance);
        setVisible(false);
        new Transactions(pin).setVisible(true);
    }

    public static void main(String[] args) {
        new Withdrawl("").setVisible(true);
    }
}