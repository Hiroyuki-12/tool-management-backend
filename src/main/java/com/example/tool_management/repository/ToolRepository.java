package com.example.tool_management.repository;

import com.example.tool_management.entity.Tool;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Tool エンティティに対するデータベース操作を行うリポジトリインターフェース。
 * Spring Data JPA により、自動的に実装される。
 *
 * - JpaRepository<Tool, Long> を継承することで、基本的なCRUD操作（保存・取得・更新・削除）が利用可能。
 * - findById(Long id): IDを指定してToolを1件取得する。
 */

@Repository
public interface ToolRepository extends JpaRepository<Tool, Long> {

  /**
   * IDを指定してToolを1件取得する。
   * 該当するデータが存在しない場合は Optional.empty() を返す。
   *
   * @param id Toolの主キー
   * @return Optional<Tool>（見つかれば中にToolが入る、なければ空）
   */

  Optional<Tool> findById(Long id);
}
