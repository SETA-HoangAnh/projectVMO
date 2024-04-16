package com.example.project.socket;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class NotifyDTO {

    private Long notifyId;

    private String notifyContent;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long userId;

    public NotifyDTO(Long notifyId, String notifyContent, LocalDateTime createdAt, LocalDateTime updatedAt, Long userId) {
        this.notifyId = notifyId;
        this.notifyContent = notifyContent;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
    }
}
