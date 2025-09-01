package com.example.cybershield.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String channel;
    private String content;
    private String link;
    private String imageUrl;
    private Double latitude;
    private Double longitude;
    private LocalDateTime timestamp;
}
