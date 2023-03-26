package my.movie.database;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MovieTable extends JFrame {

    JTextArea textArea;

    MovieTable() {
        setTitle("Movie Table");

        // Create a JTextArea to display the movie names and genres
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Add the scroll pane to the frame
        add(scrollPane, BorderLayout.CENTER);

        // Add a back button to the frame
        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.white);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Hide the current frame
                setVisible(false);

                // Create a new instance of the main page and show it
                new MainPage().setVisible(true);
            }
        });
        add(backButton, BorderLayout.SOUTH);

        setSize(600, 400);
        setVisible(true);
        setLocation(350, 200);

        try {
            Conn c1 = new Conn();
            ResultSet rs = c1.s.executeQuery("SELECT moviename, genre FROM movies");

            while (rs.next()) {
                String name = rs.getString("moviename");
                String genre = rs.getString("genre");
                textArea.append(String.format("%-60s %s\n", name, genre));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        new MovieTable();
    }
}
