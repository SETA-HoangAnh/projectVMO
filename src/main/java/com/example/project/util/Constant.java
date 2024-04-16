package com.example.project.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Constant {

    /**
     * Message
     */
    public static final String addToProject = "You have been added to the <span>%s</span> project";
    public static final String removeFromProject = "You have been removed from the <span>%s</span> project";
}
