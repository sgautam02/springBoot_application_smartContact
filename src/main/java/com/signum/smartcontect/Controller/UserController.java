package com.signum.smartcontect.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.signum.smartcontect.Helper.ShowMessage;
import com.signum.smartcontect.JpaRepo.ContactRepo;
import com.signum.smartcontect.JpaRepo.UserRepo;
import com.signum.smartcontect.Model.Contact;
import com.signum.smartcontect.Model.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

    // private String userName;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private ContactRepo contactRepository;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @RequestMapping("/logout")
    public String logOut(HttpSession session) {
        session.invalidate();
        return "home";
    }

    @PostMapping("/do_login")
    public String do_login(@RequestParam("email") String email, String password, Model model, HttpSession session) {
        // Log the received email
        System.out.println("Received email: " + email);

        // Try to retrieve the user by email
        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            return "login";
        }


        // Log the user (for debugging)
        // System.out.println("User retrieved: " + user);

        if (user != null && user.getPassword().equals(password)) {
            model.addAttribute("title", "index");
            session.setAttribute("message", new ShowMessage("alert-success", "Successfully Logged In"));
            session.setAttribute("user", user);
            model.addAttribute("user", user);
            return "redirect:/index"; // Redirect to a protected page
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/index")
    public String profilePage(Model model, HttpSession session) {
        // Access the user data from the session
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "login";
        }

        int contactSize = user.getContacts().size();
        model.addAttribute("user", user);
        model.addAttribute("contactSize", contactSize);

        // System.out.println(user);
        return "Users/dashboard"; // Display the profile page with user data
    }

    @GetMapping("/new-contact")
    public String addContact(Model model, HttpSession session) {
        // Access the user data from the session
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "login";
        }

        model.addAttribute("user", user);
        model.addAttribute("title", "add contact");
        model.addAttribute("contact", new Contact());
        // System.out.println(user.getContacts());
        return "Users/add-contact-form"; // Display the profile page with user data
    }

    @PostMapping("/contact-added")
    public String contactAdded(
            Model model,
            HttpSession session,
            @ModelAttribute Contact contact,
            @RequestParam("dpImage") MultipartFile file) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "login";
        }

        try {

            contact.setUser(user);
            user.getContacts().add(contact);

            if (!file.isEmpty()) {
                // Process the file upload here
                String fileName = file.getOriginalFilename();
                contact.setDpUri(fileName);
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image Uploaded: " + path.toString());
            }

            // Perform validation if needed

            contactRepository.save(contact);
            userRepository.save(user);
        } catch (Exception e) {
            System.out.println(e);
        }

        return "redirect:/show-contacts";
    }

    // Show contacts
    @GetMapping("/show-contacts")
    public String showContacts(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "login";
        }

        List<Contact> contact = user.getContacts();
        model.addAttribute("contact", contact);
        return "Users/show_contacts";

    }

    @RequestMapping("/update-contact/{id}")
    public String updateContact(@PathVariable("id") Integer id, Model model, HttpSession session
     ) {

        Contact contact = contactRepository.findById(id).get();
        model.addAttribute("contact", contact);
        return "Users/contact-update-form";
    }

    @PostMapping("/updated-contact/{id}")
    public String updatedContact(
        @PathVariable("id") Integer id,
        @ModelAttribute("contact") Contact contact,
        HttpSession session,
        @RequestParam("dpImage") MultipartFile file) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "login";
        }

        try {
            Contact contactToUpdate = user.getContacts().stream()
                    .filter(contacts -> contacts.getId() == id)
                    .findFirst()
                    .orElse(null);

            if (contactToUpdate != null) {
                contactToUpdate.setName(contact.getName());
                contactToUpdate.setSurName(contact.getSurName());
                contactToUpdate.setEmail(contact.getEmail());
                contactToUpdate.setAbout(contact.getAbout());
                // contactToUpdate.setDpUri(contact.getDpUri());
                contactToUpdate.setPhone(contact.getPhone());
                contactToUpdate.setWorkTitle(contact.getWorkTitle());
                contactToUpdate.setAddress(contact.getAddress());

                if (!file.isEmpty()) {
                    // Process the file upload here
                    String fileName = file.getOriginalFilename();
                    contact.setDpUri(fileName);
                    File saveFile = new ClassPathResource("static/img").getFile();
                    Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + fileName);
                    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Image Uploaded: " + path.toString());
                }

                userRepository.save(user);

            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return "redirect:/show-contacts";
    }

    // view individual contact detail
    @GetMapping("/view-detail/{id}")
    public String viewDetail(@PathVariable("id") Integer id, Model model, HttpSession session) {

        Contact contact = contactRepository.findById(id).get();
        model.addAttribute("contact", contact);
        return "Users/view-contact-detail";
    }

    @GetMapping("/delete-contact/{id}")
    public String deleteContact(@PathVariable("id") Integer id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "login";
        }

        try {
            // Remove the contact from the user's contact list
            Contact contactToDelete = user.getContacts().stream()
                    .filter(contact -> contact.getId() == id)
                    .findFirst()
                    .orElse(null);

            if (contactToDelete != null) {
                String fileName = contactToDelete.getDpUri();

                // Delete the associated file from the server's file system
                File deleteFile = new ClassPathResource("static/img/" + fileName).getFile();
                if (deleteFile.delete()) {
                    System.out.println("File deleted successfully: " + fileName);
                } else {
                    System.out.println("Error deleting file: " + fileName);
                }

                contactRepository.deleteById(id);
                // Remove the contact from the user's contact list
                user.getContacts().remove(contactToDelete);
                // Save the user to update the contact list in the database
                userRepository.save(user);

                System.out.println("Contact deleted");
            }
        } catch (Exception e) {
            System.out.println("Error deleting contact: " + e.getMessage());
        }

        return "redirect:/show-contacts";
    }
    // @GetMapping("/user_profile")
    // public String userProfile(Model model ,HttpSession session){
    // User user = (User) session.getAttribute("user");
    // model.addAttribute("user", user);
    // return "Users/dashboard";
    // }

    @RequestMapping("/update-user")
    public String updateUserForm(Model model, HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "login";
        }

        //model.addAttribute("about", user.getAbout());
        model.addAttribute("user", user);
        return "Users/user-update-form";
    }

    @PostMapping("/updated-user-profile")
    public String updateUserProfile(HttpSession session, @ModelAttribute User profile,
            @RequestParam("profileImage") MultipartFile file) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "login";
        }

        try {

            if (!file.isEmpty()) {
                // Process the file upload here
                String fileName = file.getOriginalFilename();
                user.setImageUri(fileName);
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image Uploaded: " + path.toString());
            }
            user.setName(profile.getName());
            user.setRole(profile.getRole());
            user.setAbout(profile.getAbout());
            userRepository.save(user);

            session.setAttribute("message", new ShowMessage("alert-success", "Successfully registered"));

        } catch (Exception e) {
            System.out.println(e);
            session.setAttribute("message", new ShowMessage("alert-danger", "Something went wrong: " + e.getMessage()));
            // return "redirect:/update-user";
        }

        return "redirect:/index";
    }

}
