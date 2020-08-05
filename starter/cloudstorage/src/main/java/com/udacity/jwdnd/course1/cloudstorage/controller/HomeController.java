package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public String goHome(Model model) {
        getUpdatedMessages(model);
        return "home";
    }

    @PostMapping("/file-upload")
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile fileUpload, Model model, RedirectAttributes redirectAttributes) throws IOException, SQLException {
        User user = getUser();
        File existingFile = fileService.getFile(fileUpload.getOriginalFilename(), user.getUserId());
        if(existingFile == null && (fileUpload.getSize() > 0 && !fileUpload.getOriginalFilename().isEmpty())) {
            fileService.saveNewFile(fileUpload, user.getUserId());
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded " + fileUpload.getOriginalFilename() + "!");
        } else{
            model.addAttribute("uploadError", "File " + existingFile.getFilename() + " already exists.");
        }

        getUpdatedMessages(model);
        return "home";
    }

    @GetMapping("/file-download")
    public ResponseEntity HandleFileDownload(@RequestParam("fileName")String fileName){
        User user = getUser();
        File existingFile = fileService.getFile(fileName, user.getUserId());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + existingFile.getFilename() + "\"")
                .body(existingFile.getFileData());
    }

    @GetMapping("/file-delete")
    public String HandleFileDelete(@RequestParam("fileName") String fileName){
        User user = getUser();
        File existingFile = fileService.getFile(fileName, user.getUserId());
        fileService.deleteFile(existingFile);
        return "redirect:/home";
    }

    private User getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;

        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return userService.getUser(username);
    }

    private void getUpdatedMessages(Model model) {
        User user = getUser();
        List<File> userFiles = fileService.getFiles(user.getUserId());

        if(userFiles != null) {
            model.addAttribute("userFiles", userFiles);
        }
    }
}
