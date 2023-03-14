package demo.pgi.controller;

import demo.pgi.entity.Admins;
import demo.pgi.entity.Students;
import demo.pgi.services.MainService;
import demo.pgi.repository.StudentRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @Autowired
    private MainService studentService;
    @Autowired
    private StudentRepository studentRepository;
    @GetMapping({"/register", "/signup"})
    public String register(){return "register";}
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        if (session.getAttribute("student") == null){
            return "redirect:/login";
        }
        return "dashboard";
    }
    @GetMapping("/admin_dashboard")
    public String admin_dashboard(HttpSession session) {
        if (session.getAttribute("admin") == null){
            return "redirect:/login";
        }
        return "admin_dashboard";
    }
    @GetMapping({"/login", "/signin"})
    public String login(HttpSession session) {
        if (session.getAttribute("student") == null || session.getAttribute("admin") == null){
            return "login";
        }
        else if (session.getAttribute("admin") != null)
        {
            return "admin_dashboard";
        }
        else
            return "dashboard";
    }
    @PostMapping("/save")
    public String saveStudent(@ModelAttribute Students student, Model model){
        // Check if username already exists in the database
        if (this.studentRepository.findByUsername(student.getUsername()) != null) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }

        // Check if email already exists in the database
        if (this.studentRepository.findByEmail(student.getEmail()) != null) {
            model.addAttribute("error", "Email already exists");
            return "register";
        }

        if (!student.getPassword().equals(student.getCpassword())) {
            model.addAttribute("error", "Password and confirm password do not match");
            return "register";
        }
        // Save the student object to the database
        Students s = this.studentService.saveStudent(student);

        // Return success message to the user
        model.addAttribute("success", "Registration successful");
        return "register";
    }
    @PostMapping({"/login", "/signin"})
    public String loginStudent(@RequestParam String username, @RequestParam String password, @RequestParam(required = false) boolean isAdmin, Model model, HttpSession session){
        if (isAdmin) {
            Admins admin = this.studentService.loginAdmin(username, password);
            if (admin == null) {
                model.addAttribute("error", "Admin username or password incorrect");
                return "login";
            }
            session.setAttribute("admin", admin);
            return "redirect:/admin_dashboard";
        }
        Students student = this.studentService.loginStudent(username, password);
        if(student == null){
            model.addAttribute("error", "username or password incorrect");
            return "login";
        }
        session.setAttribute("student", student);
        return "redirect:/dashboard";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping({"/", "/index"})
    public String index(){
        return "index";
    }
}