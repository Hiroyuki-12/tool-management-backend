package com.example.tool_management.controller;

import com.example.tool_management.entity.Tool;
import com.example.tool_management.repository.ToolRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tools")
@CrossOrigin(origins = "http://18.232.107.8:3000")
public class ToolController {
  private final ToolRepository toolRepository;

  @Autowired
  public ToolController(ToolRepository toolRepository) {
    this.toolRepository = toolRepository;
  }

  @GetMapping
  public ResponseEntity<?> getTools() {
    return ResponseEntity.ok(toolRepository.findAll());
  }

  @PostMapping
  public ResponseEntity<Tool> createTool(@RequestBody Tool tool) {
    Tool savedTool = toolRepository.save(tool);
    return ResponseEntity.ok(savedTool);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Tool> updateTool(@PathVariable Long id, @RequestBody Tool tool) {
    Optional<Tool> optionalTool = toolRepository.findById(id);
    if (optionalTool.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Tool existingTool = optionalTool.get();

    if (tool.getName() != null) {
      existingTool.setName(tool.getName());
    }
    if (tool.getWorkName() != null) {
      existingTool.setWorkName(tool.getWorkName());
    }
    if (tool.getPhotoUrl() != null) {
      existingTool.setPhotoUrl(tool.getPhotoUrl());
    }
    if (tool.getQuantity() != 0) {
      existingTool.setQuantity(tool.getQuantity());
    }
    if (tool.getStatus() != null) {
      existingTool.setStatus(tool.getStatus());
    }
    if (tool.getIsSelected() != existingTool.getIsSelected()) {
      existingTool.setIsSelected(tool.getIsSelected());
    }

    Tool updatedTool = toolRepository.save(existingTool);
    return ResponseEntity.ok(updatedTool);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTool(@PathVariable Long id) {
    if (!toolRepository.existsById(id)) {
      return ResponseEntity.notFound().build();
    }
    toolRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}


