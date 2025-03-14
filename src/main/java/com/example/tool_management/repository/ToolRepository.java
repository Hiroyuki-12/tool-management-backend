package com.example.tool_management.repository;

import com.example.tool_management.entity.Tool;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Long> {
  Optional<Tool> findById(Long id);
}
