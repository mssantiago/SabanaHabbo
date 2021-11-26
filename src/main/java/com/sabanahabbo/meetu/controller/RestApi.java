package com.sabanahabbo.meetu.controller;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.persistence.EntityNotFoundException;

import com.sabanahabbo.meetu.model.Event;
import com.sabanahabbo.meetu.model.Student;
import com.sabanahabbo.meetu.model.UserLogin;
import com.sabanahabbo.meetu.repository.EventRepository;
import com.sabanahabbo.meetu.repository.GroupRepository;
import com.sabanahabbo.meetu.repository.InterestRepository;
import com.sabanahabbo.meetu.repository.StudentRepository;
import com.sabanahabbo.meetu.repository.UniversityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestApi {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    UniversityRepository universityRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    InterestRepository interestRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String[] nameListF = { "Daniela" };
    private String[] nameListM = { "Tom", "Andrew", "Jankos", "Adam" };
    private String[] lastNameList = { "Vargas"};
    private String[] careerList = { "Ingeniería informática", "Medicina", "Ingeniería industrial", "Ingeniería mecánica" };

    // agregar estudiante cuando ingresa password, email, y selecciona su avatar
    @PostMapping("/service/students")
    public Student addStudent(@RequestBody Student student) {
        student.setName(student.getAvatar().getSexo().equals("Female") ? nameListF[new Random().nextInt(nameListF.length)]
                : nameListM[new Random().nextInt(nameListM.length)]);
        student.setLastName(lastNameList[new Random().nextInt(lastNameList.length)]);
        student.setCareer(careerList[new Random().nextInt(careerList.length)]);

        return studentRepository.save(student);
    }

    // permite actualizar la lista de intereses y la lista de grupos para guardar al
    // estudiante
    @PutMapping("/service/students")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updateStudent = studentRepository.findByEmail(student.getEmail())
                .orElseThrow(() -> new EntityNotFoundException());
        // update es el estudiante encontrado a opartir de lo mandado
        if (!student.getInterests().isEmpty())
            updateStudent.setInterests(student.getInterests());
        if (!student.getGroups().isEmpty())
            updateStudent.setGroups(student.getGroups());

        studentRepository.save(updateStudent);
        return new ResponseEntity<>(updateStudent, HttpStatus.OK);
    }

    // verifica si existe el email, si manda error de no encontrado es por que debe
    // seguir a crearse, o por el contrario, compara la password de la bd
    @PostMapping("/service/students/login")
    public ResponseEntity<?> findStudentToLoad(@RequestBody UserLogin userLogin) {
        Optional<Student> findStudent = studentRepository.findByEmail(userLogin.getEmail());
        if (!findStudent.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            if (findStudent.get().getEmail().equals(userLogin.getEmail())
                    && passwordEncoder.matches(userLogin.getPassword(), findStudent.get().getPassword())) {
                return new ResponseEntity<>(findStudent, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
    }

    // trae todos los eventos por la lista de intereses
    @GetMapping("/service/students")
    public List<?> getAllStudents() {
        return studentRepository.findAll();
    }

    // para crear un evento hacedlo desde aquí
    @PostMapping("/service/university/events")
    public Event createEvent(@RequestBody Event event) {
        System.out.println(event.toString());
        return eventRepository.save(event);
    }

    /////////////////////////////////// probado hasta aca
    /////////////////////////////////// ///////////////////////////////////////

    // traer todos los intereses adjuntados a la universidad de la sabana para crear

    @GetMapping("/service/university/")
    public List<?> getAllUniversities() {
        return universityRepository.findAll();
    }

    // trae todos los eventos relacionados a los grupos o relacionados a intereses,
    // si tiene tittle es que es un evento de un grupo, sino, es un evento de un
    // interest
    @GetMapping("/service/university/events")
    public List<?> getAllEvents() {
        return eventRepository.getAllEvents();
    }

    // trae todos los estudiantes dependiendo del id interes que se le mande
    @GetMapping("/service/students/interest")
    public List<?> getAllStudentsByInterest(@RequestParam Long Param) {
        return interestRepository.getAllStudentsByInterest(Param);
    }

    // trae todos los grupos con sus participantes
    @GetMapping("/service/groups")
    public List<?> getAllGroups() {
        return groupRepository.findAll();
    }

    // trae todos los intereses por el id del estudiante
    @GetMapping("/service/interest/student")
    public List<?> getAllInterestByStudent(@RequestParam Long Param) {
        return interestRepository.getAllInterestByStudent(Param);
    }

    // trae todos los eventos por la lista de intereses
    @GetMapping("/service/events/interest")
    public List<?> getAllEventsByInterest(@RequestParam List<String> Params) {
        return eventRepository.getAllEventsByInterest(Params);
    }

    // trae todos los eventos por la lista de grupos
    @GetMapping("/service/events/groups")
    public List<?> getAllEventsByGroups(@RequestParam List<Long> Params) {
        return eventRepository.getAllEventsByGroups(Params);
    }

    // trae todos los eventos por la lista de grupos
    @GetMapping("/service/events")
    public List<?> getAlllEvents() {
        return eventRepository.findAll();

    }

    @GetMapping("/service/interets")
    public List<?> getAlllInterest() {
        return interestRepository.findAll();

    }

}