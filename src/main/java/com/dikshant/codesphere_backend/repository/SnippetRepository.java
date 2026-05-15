package com.dikshant.codesphere_backend.repository;

import com.dikshant.codesphere_backend.model.Snippet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SnippetRepository extends JpaRepository<Snippet, Long> {

    // ✅ Fetch snippets by user_id (fixes object mismatch)
    List<Snippet> findByUserId(Long userId);
    // ✅ (NEW) For ownership checking – optional but important for security
    Snippet findByIdAndUserId(Long id, Long userId);
}
