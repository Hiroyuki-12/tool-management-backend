package com.example.tool_management.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "tools")
public class Tool {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "work_name")
  @JsonProperty("work_name")          // ← JSON対応
  private String workName;

  @Column(name = "photo_url")
  @JsonProperty("photo_url")          // ← JSON対応
  private String photoUrl;

  @Column(name = "tool_name")
  @JsonProperty("tool_name")          // ← JSONのキーが tool_name に対応！
  private String name;

  private int quantity = 0;

  @Column(name = "isSelected")
  @JsonProperty("isSelected")         // ← JSONのキーもそのまま → 念のため明示
  private boolean isSelected;

  @JsonProperty("status")             // ← これも合わせておくと安心
  private String status;

  // デフォルトコンストラクタ（JPA用）
  public Tool() {}

  // 値をセットするコンストラクタ
  public Tool(String workName, String photoUrl,String name, int quantity, boolean isSelected, String status) {
    this.workName = workName;
    this.photoUrl= photoUrl;
    this.name = name;
    this.quantity = quantity;
    this.isSelected = isSelected;
    this.status= status;
  }

  // Getter（値を取得するメソッド）
  public Long getId() { return id; }
  public String getWorkName() { return workName; }
  public String getPhotoUrl() {return photoUrl; }
  public String getName() { return name; }
  public int getQuantity() { return quantity; }
  public boolean getIsSelected() { return isSelected; }
  public String getStatus() {return status; }

  // Setter（値を変更するメソッド）
  public void setWorkName(String workName) {this.workName = workName; }
  public void setPhotoUrl(String photoUrl) {this.photoUrl = photoUrl; }
  public void setName(String name) { this.name = name; }
  public void setQuantity(int quantity) { this.quantity = quantity; }
  public void setIsSelected(boolean isSelected) {this.isSelected = isSelected; }
  public void setStatus(String status) {this.status = status; }
}