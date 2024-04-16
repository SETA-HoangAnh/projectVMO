package com.example.project.service;

import com.example.project.dto.ProjectAndUserDto;
import com.example.project.dto.ProjectDto;
import com.example.project.entity.Project;
import com.example.project.entity.ProjectUser;
import com.example.project.entity.Users;
import com.example.project.repository.ProjectRepository;
import com.example.project.repository.ProjectUserRepository;
import com.example.project.repository.UserRepository;
import com.example.project.security.service.UserDetailsImpl;
import com.example.project.socket.Notify;
import com.example.project.socket.NotifyRepository;
import com.example.project.socket.WSService;
import com.example.project.util.Constant;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class ProjectUserServiceImpl {

    private static ProjectUserRepository projectUserRepository;

    private static UserRepository userRepository;

    private static ProjectRepository projectRepository;

    private static NotifyRepository notifyRepository;

    private static WSService wsService;

    private static JavaMailSender javaMailSender;

    public ProjectUserServiceImpl(ProjectUserRepository projectUserRepository, UserRepository userRepository, ProjectRepository projectRepository, NotifyRepository notifyRepository, WSService wsService, JavaMailSender javaMailSender) {
        this.projectUserRepository = projectUserRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.notifyRepository = notifyRepository;
        this.wsService = wsService;
        this.javaMailSender = javaMailSender;
    }

    public ResponseEntity<?> listProjectUser(Long projectId){

        List<ProjectAndUserDto> projectAndUserDtoList = projectUserRepository.listProjectAndUser(projectId);
        return ResponseEntity.ok(projectAndUserDtoList);
    }

    public ResponseEntity<?> listProjectByUser(){

        List<ProjectDto> listProjectByUser = projectUserRepository.listProjectByUser(getUserLoginId());
        return ResponseEntity.ok(listProjectByUser);
    }


    public ResponseEntity<?> addToProject(List<ProjectUser> projectUserList){

        projectUserRepository.saveAll(projectUserList);
        return ResponseEntity.ok("Added to project");
    }


    public ResponseEntity<?> removeFromProject(Long projectId, Long userId){

        ProjectUser projectUserFind = projectUserRepository.findProjectByUser(projectId, userId);
        projectUserRepository.deleteById(projectUserFind.getProjectUserId());
        printMessageRemove(projectId, userId);
        return ResponseEntity.ok("Removed from project");
    }

    public static Long getUserLoginId() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getId();
    }

    public static void printMessagePresit(Long projectId, Long userId){

        Users userPresit = userRepository.findById(userId).get();
        Project projectPresit = projectRepository.findById(projectId).get();

        Notify notify = new Notify();
        notify.setNotifyContent(String.format(Constant.addToProject, projectPresit.getProjectName()));
        notify.setUsers(userRepository.findById(userPresit.getUserId()).get());
        List<String> lstNotify = Arrays.asList(userPresit.getUserId().toString());
        notify.setUserNotifyLst(lstNotify);
        notifyRepository.save(notify);
        wsService.notifyUser(notify);

        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            try {

                MimeMessage message = javaMailSender.createMimeMessage();

                boolean multipart = true;

                MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
                String notifyMailContent = notify.getNotifyContent();
                notifyMailContent = notifyMailContent.replaceAll("<span>","");
                notifyMailContent = notifyMailContent.replaceAll("</span>","");

                String htmlMsg = "";
                try {
                    Resource resource = new ClassPathResource("static/templates/emailtemplate.html");
                    htmlMsg = StreamUtils.copyToString(resource.getInputStream(), Charset.defaultCharset());
                    htmlMsg = htmlMsg.replace("{{notifyMailContent}}", notifyMailContent);
                    String link = "http://vmo.com/#/";
                    htmlMsg = htmlMsg.replace("{{link}}", link);

                } catch (IOException e) {
                    // Xử lý lỗi khi không thể đọc file HTML
                    System.out.println("Error HTML file!");
                }

                helper.setSubject(notifyMailContent);
                helper.setTo(userRepository.findById(userId).get().getEmail());
                helper.setText(htmlMsg, true);
                javaMailSender.send(message);

            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }

    public static void printMessageRemove(Long projectId, Long userId){

        Users userRemove = userRepository.findById(userId).get();
        Project projectPresit = projectRepository.findById(projectId).get();

        Notify notify = new Notify();
        notify.setNotifyContent(String.format(Constant.removeFromProject, projectPresit.getProjectName()));
        notify.setUsers(userRepository.findById(userRemove.getUserId()).get());
        List<String> lstNotify = Arrays.asList(userRemove.getUserId().toString());
        notify.setUserNotifyLst(lstNotify);
        notifyRepository.save(notify);
        wsService.notifyUser(notify);

        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            try {

                MimeMessage message = javaMailSender.createMimeMessage();

                boolean multipart = true;

                MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
                String notifyMailContent = notify.getNotifyContent();
                notifyMailContent = notifyMailContent.replaceAll("<span>","");
                notifyMailContent = notifyMailContent.replaceAll("</span>","");

                String htmlMsg = "";
                try {
                    Resource resource = new ClassPathResource("static/templates/emailtemplate.html");
                    htmlMsg = StreamUtils.copyToString(resource.getInputStream(), Charset.defaultCharset());
                    htmlMsg = htmlMsg.replace("{{notifyMailContent}}", notifyMailContent);
                    String link = "http://vmo.com/#/";
                    htmlMsg = htmlMsg.replace("{{link}}", link);
                } catch (IOException e) {
                    System.out.println("Error HTML file!");
                }

                helper.setSubject(notifyMailContent);
                helper.setTo(userRepository.findById(userId).get().getEmail());
                helper.setText(htmlMsg, true);
                javaMailSender.send(message);

            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }
}
