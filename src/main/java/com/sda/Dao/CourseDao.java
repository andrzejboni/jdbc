package com.sda.Dao;

import com.sda.jdbc.JdbConnectionFactory;
import com.sda.model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {
    private final static String CREATE_QUERRY = "CREATE TABLE `courses`.`courses` (\n" +
            "  `idcourse` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  `cena` FLOAT NOT NULL,\n" +
            "  `iloscGodzin` INT NOT NULL,\n" +
            "  PRIMARY KEY (`idcourse`));";


    private final static String DELETE_QUERRRY = "DELETE FROM `courses`.`courses` WHERE id=?;";


    private final static String INSERT_QUERY = "INSERT INTO `courses`.`courses`\n" +
            "(`idcourse`,\n" +
            "`name`,\n" +
            "`cena`,\n" +
            "`iloscGodzin`)\n" +
            "VALUES\n" +
            "(NULL,\n" +
            "?,\n" +
            "?,\n" +
            "?);";


    private final static String SELECT_QUERRY = "SELECT * FROM courses";

    public List<Course> select() {
        List<Course> courses = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERRY)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()){ // dopóki sa rekordy
                    Course course = new Course();

                    course.setId(resultSet.getLong(1));
                    course.setNazwa(resultSet.getString(2));
                    course.setCena(resultSet.getDouble(3));
                    course.setIloscGodzin(resultSet.getInt(4));


                    courses.add(course);
                }


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }


    private JdbConnectionFactory connectionFactory;

    public CourseDao(JdbConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        try (Connection connection = connectionFactory.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERRY)) {
                boolean result = preparedStatement.execute();
                System.out.println("Create: " + result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void delete(Course course) {
        try (Connection connection = connectionFactory.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERRRY)) {
                preparedStatement.setLong(1, course.getId());

                boolean result = preparedStatement.execute();
                System.out.println("Delete: " + result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Course course) {
        try (Connection connection = connectionFactory.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setString(1, course.getNazwa());
                preparedStatement.setDouble(2, course.getCena());
                preparedStatement.setInt(3, course.getIloscGodzin());

                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next(); // przjedz do nastepnego klucza

                System.out.println("Success insert" + resultSet.getInt(1));

                course.setId(resultSet.getLong(1));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
