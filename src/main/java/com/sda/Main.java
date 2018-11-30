package com.sda;

import com.mysql.cj.jdbc.JdbcConnection;
import com.sda.Dao.CourseDao;
import com.sda.Dao.StudentDao;
import com.sda.jdbc.JdbConnectionFactory;
import com.sda.model.Course;
import com.sda.model.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        try {
//            // towrzoymu sobue połączenie ładujemy z konfiguracji properties
            JdbConnectionFactory jdbcConnectionFactory = new JdbConnectionFactory();
//
//            Connection connection = jdbcConnectionFactory.getConnection();
//
//            System.out.println("Closed: " + connection.isClosed());
//
//            CourseDao courseDao = new CourseDao(jdbcConnectionFactory);
//            Course course = new Course();
//
//            course.setNazwa("Jaszczur");
//            course.setIloscGodzin(360);
//            course.setCena(12000);
//
//            courseDao.insert(course);
////            List<Course> courseList = courseDao.select();
//
//            List<Course> courseList = courseDao.select();
//
//            courseList.forEach(System.out::println);

//            System.out.println(courseList);
//            courseDao.delete(course);
// -----------------------------            TERAZ STJUDENT ____---------

            StudentDao studentDao = new StudentDao(jdbcConnectionFactory);
            Student student = new Student();

            student.setImie("Ignac");
            student.setNazwisko("Bolech");

            studentDao.insert(student);

//            studentDao.delete(student);

            List<Student> studentList = studentDao.select();
            studentList.forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
