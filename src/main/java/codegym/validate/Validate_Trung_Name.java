package codegym.validate;

import codegym.model.Student;
import codegym.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class Validate_Trung_Name implements Validator {
    @Autowired
    IStudentService studentService;
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Student student = (Student) target;
        List<Student> students = studentService.findAll();
        for (Student s:students) {
            if (student.getName().equals(s.getName())){
                errors.rejectValue("name", "", "Trung name");
                return;
            }
        }
    }
}
