package com.xasan.amigosfullstack.student;

import com.xasan.amigosfullstack.EmailValidator;
import com.xasan.amigosfullstack.exception.ApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    private final StudentDataAccessService studentDataAccessService;
    private final EmailValidator emailValidator;
    @Autowired
    public StudentService(StudentDataAccessService studentDataAccessService, EmailValidator emailValidator) {
        this.studentDataAccessService = studentDataAccessService;
        this.emailValidator = emailValidator;
    }

    List<Student> getAllStudents(){
        return studentDataAccessService.selectAllStudents();
    }
    List<StudentCourse> getAllCoursesForStudent(UUID studentId){
        return studentDataAccessService.selectAllStudentCourses(studentId);
    }
    int addNewStudent(Student student){

        UUID newStudentId = Optional.ofNullable((UUID) null).orElse(UUID.randomUUID());

        if (!emailValidator.test(student.getEmail())){
            throw new ApiRequestException(student.getEmail() + " is not valid");
        }

        if (studentDataAccessService.isEmailTaken(student.getEmail())){
            throw new ApiRequestException(student.getEmail() + " is taken");
        }

        return studentDataAccessService.insertStudent(newStudentId, student);
    }
}
