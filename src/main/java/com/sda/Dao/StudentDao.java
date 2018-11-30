package com.sda.Dao;


import com.sda.jdbc.JdbConnectionFactory;
import com.sda.model.Course;
import com.sda.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// hbm2ddl -- ddl = data definition language

public class StudentDao {
    private final static String CREATE_QUERRY = "CREATE TABLE `student` (\n" +
            "  `id_student` int(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `imie` varchar(255) COLLATE utf8_polish_ci NOT NULL,\n" +
            "  `nazwisko` varchar(255) COLLATE utf8_polish_ci NOT NULL,\n" +
            "  PRIMARY KEY (`id_student`)\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;\n";


    private final static String DELETE_QUERRRY = "DELETE FROM `courses`.`student` WHERE id=?";


    private final static String INSERT_QUERY = "INSERT INTO `courses`.`student` (`id_student`, `imie`, `nazwisko`) VALUES (NULL,?,?);";


    private final static String SELECT_QUERRY = "SELECT * FROM student";

    public List<Student> select() {
        List<Student> students = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERRY)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) { // dopóki sa rekordy
                    Student student = new Student();

                    student.setId(resultSet.getLong(1));
                    student.setImie(resultSet.getString(2));
                    student.setNazwisko(resultSet.getString(3));


                    students.add(student);
                }


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }


    private JdbConnectionFactory connectionFactory;

    public StudentDao(JdbConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        createTableIfNotExists();
    }



    private void createTableIfNotExists() {
        try (Connection connection = connectionFactory.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERRY)) {
                boolean result = preparedStatement.execute();
                System.out.println("Create: " + result);
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void delete(Student student) {
        try (Connection connection = connectionFactory.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERRRY)) {
                preparedStatement.setLong(1, student.getId());

                boolean result = preparedStatement.execute();
                System.out.println("Delete: " + result);
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Student student) {
        try (Connection connection = connectionFactory.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
                System.out.println();
                preparedStatement.setString(1, student.getImie());
                preparedStatement.setString(2, student.getNazwisko());


                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next(); // przjedz do nastepnego klucza

                System.out.println("Success insert" + resultSet.getInt(1));
                student.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

