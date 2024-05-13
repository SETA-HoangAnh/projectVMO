package com.example.project.service.Impl;

import com.example.project.dto.ProjectAndUserDto;
import com.example.project.dto.ProjectDto;
import com.example.project.entity.Project;
import com.example.project.entity.ProjectUser;
import com.example.project.repository.ProjectRepository;
import com.example.project.repository.ProjectUserRepository;
import com.example.project.repository.UserRepository;
import com.example.project.security.service.UserDetailsImpl;
import com.example.project.service.ProjectUserService;
import com.example.project.util.Constant;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class ProjectUserServiceImpl implements ProjectUserService {

    private static ProjectUserRepository projectUserRepository;

    private static UserRepository userRepository;

    private static ProjectRepository projectRepository;

    private static JavaMailSender javaMailSender;


    public ProjectUserServiceImpl(ProjectUserRepository projectUserRepository, UserRepository userRepository, ProjectRepository projectRepository, JavaMailSender javaMailSender) {
        this.projectUserRepository = projectUserRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public Page<ProjectAndUserDto> listProjectUser(Long projectId, Pageable pageable){

        Page<ProjectAndUserDto> projectAndUserDtoList = projectUserRepository.listProjectAndUser(projectId, pageable);
        return projectAndUserDtoList;
    }


    @Override
    public Page<ProjectDto> listProjectByUser(Pageable pageable){

        Page<ProjectDto> listProjectByUser = projectUserRepository.listProjectByUser(getUserLoginId(), pageable);
        return listProjectByUser;
    }


    @Override
    public List<ProjectUser> addToProject(List<ProjectUser> projectUserList){

        return projectUserRepository.saveAll(projectUserList);
    }


    @Override
    public void removeFromProject(Long projectId, Long userId){

        ProjectUser projectUserFind = projectUserRepository.findProjectByUser(projectId, userId);
        projectUserRepository.deleteById(projectUserFind.getProjectUserId());
        removeMessagePresit(projectId, userId);
    }


    public static Long getUserLoginId() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getId();
    }

    public static void printMessagePresit(Long projectId, Long userId){

        Project projectPresit = projectRepository.findById(projectId).get();

        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            try {

                MimeMessage message = javaMailSender.createMimeMessage();

                boolean multipart = true;

                MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
                String notifyMailContent = String.format(Constant.addToProject, projectPresit.getProjectName());
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

    public static void removeMessagePresit(Long projectId, Long userId){

        Project projectPresit = projectRepository.findById(projectId).get();

        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            try {

                MimeMessage message = javaMailSender.createMimeMessage();

                boolean multipart = true;

                MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
                String notifyMailContent = String.format(Constant.removeFromProject, projectPresit.getProjectName());
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
