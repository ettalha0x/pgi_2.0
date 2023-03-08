package demo.pgi.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import demo.pgi.entity.Student;
import demo.pgi.services.MainService;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
    @Autowired
    private MainService studentService;
    @GetMapping("/register")
    public String register(){return "register";}
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        if (session.getAttribute("student") == null){
            return "redirect:/login";
        }
        return "dashboard";
    }
    @GetMapping("/login")
    public String login(HttpSession session) {
        if (session.getAttribute("student") == null){
            return "login";
        }
        return "dashboard";
    }
    @PostMapping("/save")
    public String saveStudent(@ModelAttribute Student student, Model model){
        Student s = this.studentService.saveStudent(student);
        if(s == null){
            model.addAttribute("error", "regestration failed");
            return "register";
        }
        model.addAttribute("success", "regestration succssed");
        return "register";
    }
    @PostMapping("/login")
    public String loginStudent(@RequestParam String username, @RequestParam String password, Model model, HttpSession session){
        Student s = this.studentService.loginStudent(username, password);
        if(s == null){
            model.addAttribute("error", "username or password incorrect");
            return "login";
        }
        session.setAttribute("student", s);
        return "redirect:/dashboard";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }

}
