package demo.pgi.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import demo.pgi.entity.Students;
import demo.pgi.services.MainService;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
    @Autowired
    private MainService studentService;
    @GetMapping({"/register", "/signup"})
    public String register(){return "register";}
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        if (session.getAttribute("student") == null){
            return "redirect:/login";
        }
        return "dashboard";
    }
    @GetMapping({"/login", "/signin"})
    public String login(HttpSession session) {
        if (session.getAttribute("student") == null){
            return "login";
        }
        return "dashboard";
    }
    @PostMapping("/save")
    public String saveStudent(@ModelAttribute Students student, Model model){
        Students s = this.studentService.saveStudent(student);
        if(s == null){
            model.addAttribute("error", "regestration failed");
            return "register";
        }
        model.addAttribute("success", "regestration succssed");
        return "register";
    }
    @PostMapping({"/login", "/signin"})
    public String loginStudent(@RequestParam String username, @RequestParam String password, Model model, HttpSession session){
        Students s = this.studentService.loginStudent(username, password);
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

    @GetMapping({"/", "/index"})
    public String index(){
        return "index";
    }
}
