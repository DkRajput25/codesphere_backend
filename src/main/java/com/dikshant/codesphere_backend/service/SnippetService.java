package com.dikshant.codesphere_backend.service;

import com.dikshant.codesphere_backend.model.Snippet;
import com.dikshant.codesphere_backend.model.User;
import com.dikshant.codesphere_backend.repository.SnippetRepository;
import com.dikshant.codesphere_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class SnippetService {

    @Autowired
    private SnippetRepository snippetRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Fetch all snippets belonging to the logged-in user
    public List<Snippet> getUserSnippets(String username) {
        // Step 1: Find user by username
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            System.out.println("⚠️ No user found with username: " + username);
            return Collections.emptyList();
        }

        // Step 2: Fetch snippets using user ID (✅ correct approach)
        List<Snippet> snippets = snippetRepository.findByUserId(user.getId());
        System.out.println("✅ Found " + snippets.size() + " snippets for user: " + username);
        return snippets;
    }


    // ------------------------------------------------------------
    // ✅ NEW: Delete snippet by ID (with ownership check)
    // ------------------------------------------------------------
    public boolean deleteSnippet(Long snippetId, String username) {

        // Step 1: Get user
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) return false;

        // Step 2: Check if snippet belongs to this user
        Snippet snippet = snippetRepository.findByIdAndUserId(snippetId, user.getId());
        if (snippet == null) {
            return false; // ❌ Not found OR not owned by this user
        }

        // Step 3: Delete it
        snippetRepository.delete(snippet);
        return true; // ✅ Successfully deleted
    }
}
