package codegym.controller;

import codegym.model.ClassRoom;
import codegym.model.Student;
import codegym.service.IClassZoomService;
import codegym.service.IStudentService;
import codegym.service.StudentService;
import codegym.validate.Validate_Trung_Name;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class StudentController {
    @Autowired
    IStudentService studentService;

    @Autowired
    IClassZoomService classZoomService;

    @Autowired
    Validate_Trung_Name validate_trung_name;

    @GetMapping("/students")
    public ModelAndView showAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "name") String option) {
        ModelAndView modelAndView = new ModelAndView("show");
        modelAndView.addObject("students", studentService.findAll(PageRequest.of(page, 3, Sort.by(option))));
        modelAndView.addObject("option", option);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreate() {
        ModelAndView modelAndView = new ModelAndView("create");
        return modelAndView;
    }

    @ModelAttribute("student")
    public Student student(){
        return new Student();
    }

    @ModelAttribute("classZooms")
    public List<ClassRoom> classZooms(){
        return classZoomService.findAll();
    }


    @PostMapping("/create")
    public String create(@Valid @ModelAttribute(value = "student") Student student, BindingResult bindingResult, @RequestParam MultipartFile upImg) {
        validate_trung_name.validate(student, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            return "create";
        }
        String nameFile = upImg.getOriginalFilename();
        try {
            FileCopyUtils.copy(upImg.getBytes(), new File("/Users/johntoan98gmail.com/Desktop/Module3/Demo_Spring_Repository/src/main/webapp/WEB-INF/img/" + nameFile));
            student.setImg("/img/" + nameFile);
            studentService.save(student);

        } catch (IOException e) {
            student.setImg("/img/abc.jpeg");
            studentService.save(student);
            e.printStackTrace();
        }
        return "redirect:/students";
    }
}
