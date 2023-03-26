package my.movie.database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainPage extends JFrame implements ActionListener {

    JButton add, clear;
    JTextField name, genreinfo;

    MainPage() {

        setTitle("My Movie Database");

        setLayout(null);

        JLabel text = new JLabel("Welcome to My Movie Database ");
        text.setFont(new Font("Osward", Font.BOLD, 28));
        text.setBounds(200, 40, 550, 40);
        add(text);

        JLabel moviename = new JLabel("Movie/Series: ");
        moviename.setFont(new Font("Raleway", Font.BOLD, 22));
        moviename.setBounds(120, 150, 250, 40);
        add(moviename);

        name = new JTextField();
        name.setBounds(300, 155, 230, 30);
        name.setFont(new Font("Arial", Font.BOLD, 14));
        add(name);

        JLabel genre = new JLabel("Genre: ");
        genre.setFont(new Font("Raleway", Font.BOLD, 22));
        genre.setBounds(120, 220, 250, 30);
        add(genre);

        genreinfo = new JTextField();
        genreinfo.setBounds(300, 225, 230, 30);
        genreinfo.setFont(new Font("Arial", Font.BOLD, 14));
        add(genreinfo);

        add = new JButton("Add Movie");
        add.setBounds(300, 300, 100, 30);
        add.setBackground(new Color(0, 100, 0));
        add.setForeground(Color.white);
        add.addActionListener(this);
        add(add);

        clear = new JButton("Clear");
        clear.setBounds(430, 300, 100, 30);
        clear.setBackground(new Color(139, 0, 0));
        clear.setForeground(Color.white);
        clear.addActionListener(this);
        add(clear);

        getContentPane().setBackground(Color.white);

        setSize(800, 480);
        setVisible(true);
        setLocation(350, 200);

    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == clear) {
            name.setText("");
            genreinfo.setText("");
        } else if (ae.getSource() == add) {
            Conn conn = new Conn();
            String moviename = name.getText();
            String genre = genreinfo.getText();

            if (moviename.isEmpty() || genre.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Fill all the required fields");
                return;
            }

            try {
                String q1 = "INSERT INTO movies (moviename, genre) VALUES ('" + moviename + "','" + genre + "')";
                conn.s.executeUpdate(q1);

                setVisible(false);
                new MovieTable().setVisible(true);
            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }

    public static void main(String args[]) {
        new MainPage();
    }
}
