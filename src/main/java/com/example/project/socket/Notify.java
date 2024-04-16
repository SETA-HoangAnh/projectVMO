package com.example.project.socket;

import com.example.project.entity.Users;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Entity
@Data
@Table(name = "notify")
public class Notify {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notify_id")
    private Long notifyId;

    @Column(name = "notify_content")
    private String notifyContent;

    protected LocalDateTime createdAt;

    protected LocalDateTime updatedAt;

    @Transient
    private List<String> userNotifyLst;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @PrePersist
    public void setDefaultValue() {

        ZoneId zone = ZoneId.of("Asia/Ho_Chi_Minh");
        LocalDateTime currentDateTime = LocalDateTime.now(zone);
        createdAt = currentDateTime;
        updatedAt = currentDateTime;
    }
}
