package studentapi;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        boolean duplicate = studentRepository.findAll().stream()
                .anyMatch(s -> s.getRegNumber().equalsIgnoreCase(student.getRegNumber()));

        if (duplicate) {
            throw new RuntimeException("A student with this registration number already exists.");
        }

        return studentRepository.save(student);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        Student existing = studentRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        existing.setFullName(updatedStudent.getFullName());
        existing.setRegNumber(updatedStudent.getRegNumber());
        existing.setForm(updatedStudent.getForm());
        existing.setGender(updatedStudent.getGender());
        existing.setDob(updatedStudent.getDob());
        existing.setGuardianContact(updatedStudent.getGuardianContact());
        return studentRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
    }
}