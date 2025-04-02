package com.example.tool_management.controller;

import com.example.tool_management.entity.Tool;
import com.example.tool_management.repository.ToolRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Tool に関する REST API を提供するコントローラー。
 * - @RestController によって JSON形式のレスポンスを返す
 * - @RequestMapping("/api/tools") で、このクラスのAPIパスを /api/tools に固定
 * - @CrossOrigin により、指定のフロントエンドからのCORSアクセスを許可
 */

@RestController
@RequestMapping("/api/tools")
@CrossOrigin(origins = "http://54.172.221.49:3000")
public class ToolController {
  private final ToolRepository toolRepository;

  @Autowired
  public ToolController(ToolRepository toolRepository) {
    this.toolRepository = toolRepository;
  }

  /**
   * 全ての道具データ（Toolエンティティ）を取得して返します。
   * GET /api/tools に対応。
   *
   * @return HTTPステータス200（OK）と全ToolデータのリストをJSON形式で返却
   */

  @GetMapping
  public ResponseEntity<?> getTools() {
    return ResponseEntity.ok(toolRepository.findAll());
  }

  /**
   * 新しい Tool データを登録するエンドポイント。
   * POST /api/tools に対応。
   *
   * @param tool リクエストボディから受け取った Tool データ（JSON形式）
   * @return 保存された Tool データとともに HTTPステータス200（OK）を返却
   */

  @PostMapping
  public ResponseEntity<Tool> createTool(@RequestBody Tool tool) {
    Tool savedTool = toolRepository.save(tool);
    return ResponseEntity.ok(savedTool);
  }

  /**
   * 指定されたIDの Tool 情報を部分的に更新します。
   * PATCH /api/tools/{id} に対応。
   *
   * - リクエストボディに含まれている項目のみを既存データに反映します（null でない値、変更があった値のみを更新）。
   * - 指定されたIDのToolが存在しない場合は、HTTP 404 Not Found を返します。
   *
   * @param id 更新対象のToolのID（URLパスから取得）
   * @param tool リクエストボディに含まれる更新用のTool情報（部分的な内容でも可）
   * @return 更新後のTool情報とHTTPステータス200（OK）
   */

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

  /**
   * 指定されたIDの Tool データを削除します。
   * DELETE /api/tools/{id} に対応。
   *
   * - 該当するToolが存在しない場合は、HTTP 404 Not Found を返します。
   * - 削除が成功した場合は、HTTP 204 No Content を返します（返却データなし）。
   *
   * @param id 削除対象のToolのID
   * @return 204 No Content または 404 Not Found
   */

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTool(@PathVariable Long id) {
    if (!toolRepository.existsById(id)) {
      return ResponseEntity.notFound().build();
    }
    toolRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}


