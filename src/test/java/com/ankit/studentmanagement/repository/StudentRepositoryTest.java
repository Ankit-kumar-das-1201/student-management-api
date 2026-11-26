package com.ankit.studentmanagement.repository;

import com.ankit.studentmanagement.entity.Student;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
class StudentRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer
            = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16-alpine"));

    @Test
    void canEstablishConnection(){
        assertThat(postgreSQLContainer.isCreated()).isTrue();
        assertThat(postgreSQLContainer.isRunning()).isTrue();
    }

//    @Test
//    void canEstablishConnection() throws Exception {
//        assertThat(postgreSQLContainer.isRunning()).isTrue();
//
//        try (var connection = postgreSQLContainer.createConnection("")) {
//            assertThat(connection.isValid(2)).isTrue();
//        }
//    }

    @Autowired
    DataSource dataSource;

    @Test
    void springShouldConnectToPostgresContainer() throws  SQLException{
        try(Connection connection = dataSource.getConnection()){
            assertThat(connection.isValid(2)).isTrue();
            assertThat(connection.getMetaData().getURL())
                    .isEqualTo(postgreSQLContainer.getJdbcUrl());
        }
    }
    @Autowired
    StudentRepository studentRepository;




    @Test
    void shouldReturnStudentWhenNameMatchesIgnoringCase() {
        //given
        String name = "Person0";
        Student student = new Student();
        student.setAge(15);
        student.setEmail("Person0@gmail.com");
        student.setName("Person0");
        studentRepository.save(student);
        // when
        List<Student> studentByName = studentRepository.findByNameContainingIgnoreCase(name);
        // then
        assertThat(studentByName).isNotEmpty();
        assertThat(studentByName)
                .extracting(Student::getName)
                .contains("Person0");

    }

    @BeforeEach
    void setUp() {
        Student s1 = new Student();
        s1.setName("Person1");
        s1.setEmail("Peron1@gmail.com");
        s1.setAge(45);
        studentRepository.save(s1);

        Student s2 = new Student();
        s2.setName("Person2");
        s2.setEmail("Peron2@gmail.com");
        s2.setAge(5);
        studentRepository.save(s2);
    }

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    void shouldReturnStudentWhoseAgeIsMoreThanGivenAge () {


        List<Student> studentList = studentRepository.findStudentsOlderThan(18);
        Assertions.assertThat(studentList)
                .allSatisfy(student ->
                       assertThat(student.getAge()).isGreaterThan(18)
                       );
        studentList.forEach(System.out::println);
    }


    @Test
    void findStudentByEmail() {
        Student s1 = new Student();
        s1.setAge(45);
        s1.setEmail("person3@gmail.com");
        s1.setName("Person3");
        studentRepository.save(s1);
        Optional<Student> studentOptional = studentRepository.findStudentByEmail("person3@gmail.com");

        assertThat(studentOptional).isPresent();
        assertThat(studentOptional.get().getEmail()).isEqualTo("person3@gmail.com");
        assertThat(studentOptional.get().getName()).isEqualTo("Person3");
        assertThat(studentOptional.get().getAge()).isEqualTo(45);


    }

    @Test
    void findStudentByTheirEmail(){
        Optional<Student> student = studentRepository.findStudentByEmail("riya1234@gmail.com");

        assertThat(student).isNotPresent();
    }
}