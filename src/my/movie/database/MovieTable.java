package my.movie.database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class MovieTable extends JFrame {

    private JButton backButton;
    private JButton searchButton;
    private JButton resetButton;
    private JTextField searchField;
    private JTable table;
    private Object[][] data;
    private Object[][] originalData;
    private Object[] columnNames;

    public MovieTable() {
        setTitle("Movie Table");

        try {
            Conn c1 = new Conn();
            ResultSet rs = c1.s.executeQuery("SELECT moviename, genre FROM movies");

            data = new Object[500][2];
            originalData = new Object[500][2];
            int i = 0;
            while (rs.next()) {
                String name = rs.getString("moviename");
                String genre = rs.getString("genre");
                data[i][0] = name;
                data[i][1] = genre;
                originalData[i][0] = name;
                originalData[i][1] = genre;
                i++;
            }

            columnNames = new Object[]{"Movies/Series", "Genre"};

            table = new JTable(data, columnNames);

            JScrollPane scrollPane = new JScrollPane(table);

            backButton = new JButton("Back");
            backButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose(); // close the current frame
                    new MainPage(); // open the main page
                }
            });

            searchButton = new JButton("Search");
            searchButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String searchTerm = searchField.getText().trim().toLowerCase();

                    if (searchTerm.isEmpty()) {
                        table.setModel(new DefaultTableModel(originalData, columnNames));
                        data = originalData;
                    } else {
                        Object[][] searchData = new Object[500][2];
                        int j = 0;
                        for (int i = 0; i < originalData.length; i++) {
                            String name = originalData[i][0] != null ? originalData[i][0].toString().toLowerCase() : "";
                            if (name.contains(searchTerm)) {
                                searchData[j][0] = originalData[i][0];
                                searchData[j][1] = originalData[i][1];
                                j++;
                            }
                        }
                        searchData = resizeArray(searchData, j, 2);
                        table.setModel(new DefaultTableModel(searchData, columnNames));
                        data = searchData;
                    }
                }
            });

            resetButton = new JButton("Reset");
            resetButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    table.setModel(new DefaultTableModel(originalData, columnNames));
                    data = originalData;
                    searchField.setText("");
                }
            });

            searchField = new JTextField(20);
            searchField.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    searchButton.doClick();
                }
            });

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
            buttonPanel.add(resetButton);
            buttonPanel.add(Box.createHorizontalGlue());
            buttonPanel.add(new JLabel("Search:"));
            buttonPanel.add(searchField);
            buttonPanel.add(Box.createHorizontalStrut(10));
            buttonPanel.add(backButton);

            add(scrollPane, BorderLayout.CENTER);
            add(buttonPanel, BorderLayout.NORTH);

            setSize(800, 480);
            setVisible(true);
            setLocation(350, 200);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        new MovieTable();
    }

    private Object[][] resizeArray(Object[][] arr, int rows, int cols) {
        Object[][] temp = new Object[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(arr[i], 0, temp[i], 0, cols);
        }
        return temp;
    }
}
