package com.dikshant.codesphere_backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "snippets") // ✅ This line is the fix
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Snippet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String code;

    private String language;
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
