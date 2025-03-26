# 道具管理アプリ - tool-management

## はじめに

このリポジトリは、Javaを学習中の私が作成した道具管理アプリのプロジェクトです。  
発達障がいのあるスタッフの業務支援を目的とし、実際の現場での課題をアプリで解決することを目指して開発しました。  
本リポジトリの利用に関するトラブル等について、開発者は一切の責任を負いかねますのでご了承ください。

---

## コンセプト

以前の職場で、「道具の忘れ物」や「紙のチェックリストの記入ミス」が頻繁に発生していました。  
そうした課題をアプリで解決したいと考え、スマートフォンでも使いやすい道具管理アプリを開発しました。  
誰でも簡単に使えるシンプルなUIで、現場でのミス防止と業務の効率化を支援します。

---

## 概要

このアプリは、発達障がいのあるスタッフが仕事で使用する道具を忘れずに準備できるように支援するためのWebアプリケーションです。  
道具の一覧表示、個数の調整、チェックマーク機能などを備えており、視覚的に準備状況を確認できます。  
スマートフォンからのアクセスも可能で、実際に動作確認済みです。  
※セキュリティの都合上、AWSインスタンスは提出後に停止予定です。

---

## 操作デモ動画

以下の動画では、アプリのログインから道具追加・チェック操作までの流れをご覧いただけます。  
実際の操作感や画面構成の確認にご活用ください。

[▶ デモ動画を再生する](https://github.com/user-attachments/assets/97739471-b620-4fbf-9aac-68bfc0b41a19)

---

## 使用技術（バッジ）

![Java](https://img.shields.io/badge/Language-Java%2021-007396?logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Framework-Spring%20Boot%203.4.3-6DB33F?logo=springboot)
![MySQL](https://img.shields.io/badge/Database-MySQL-4479A1?logo=mysql&logoColor=white)
![JPA](https://img.shields.io/badge/ORM-Spring%20Data%20JPA-000000?logo=hibernate&logoColor=white)
![Security](https://img.shields.io/badge/Security-Spring%20Security-6DB33F?logo=springsecurity)
![Gradle](https://img.shields.io/badge/Build-Gradle-02303A?logo=gradle&logoColor=white)
![GitHub](https://img.shields.io/badge/Repo-GitHub-181717?logo=github)

---

## 使用技術 / 開発環境

- 言語：Java 21
- フレームワーク：Spring Boot 3.4.3
- ビルドツール：Gradle
- データベース：MySQL
- ORマッパー：Spring Data JPA
- 認証・認可：Spring Security
- 開発環境：IntelliJ IDEA (Community Edition)
- デプロイ環境：AWS EC2（提出時のみ公開）

---


## データベースの構成

```sql
CREATE DATABASE tool_management;

USE tool_management;

CREATE TABLE tools (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  work_name VARCHAR(255),
  photo_url VARCHAR(255),
  tool_name VARCHAR(255),
  quantity INT,
  is_selected BOOLEAN,
  status VARCHAR(255)
);

```

## 利用方法（バックエンドのみ）
1. MySQLを起動し、上記「データベースの構成」の手順に従って、データベースとテーブルを作成しておきます。

2. GitHubからこのリポジトリをクローン（コピー）します。  
   ※ GitHubの「Code」ボタンから「HTTPS」を選択してコピーしてください。

3. 任意のフォルダにクローンした後、IntelliJ IDEAでプロジェクトを開きます。

4. プロジェクトを開いたら、Gradleの `bootRun` タスクを使ってアプリケーションを起動してください。  
   IntelliJ内のGradleタブ → `Tasks` → `application` → `bootRun` をダブルクリックすると起動します。

---

## 機能一覧

- ログイン機能（ユーザー名とパスワードによる認証）
- 道具の一覧表示
- 道具の追加（作業名・道具名・個数の入力）
- 道具の削除
- 個数の増減（+ / - ボタン）
- チェックマーク機能（準備状況の可視化）

---

## 工夫した点 / 苦労した点 / 学んだこと

実際の現場で起きていた「道具の忘れ物」や「紙でのチェックミス」といった課題をもとに、
どのような機能がスタッフの負担軽減につながるかを考えながら設計を行いました。
チェックマーク機能や個数の増減といった視覚的に状態を把握できる仕組みは、そうした現場の声をもとに工夫したポイントです。

JavaやMySQLについてはこれまでも学習経験がありましたが、今回はデータベース設計を起点に、
情報の構造化、認証機能の導入、AWSによる本番環境の構築までを一貫して行うことで、より実務に近い形でのアプリ開発を意識しました。

特に、データベース設計を最初に丁寧に行った上で、Spring Bootを用いてAPI連携・認証処理・CRUD機能などを組み立てていったことで、
バックエンド全体の構成を意識しながら開発できた点は大きな学びでした。

また、AWSでのデプロイにあたっては、CORS設定やセキュリティ構成で何度もつまずきましたが、
エラーメッセージや公式ドキュメントと向き合い、自力で問題を解決しながら構築できたことは大きな経験となりました。

アノテーションの活用、レイヤーごとの役割理解、データベースとの連携、ログイン認証やデプロイまでの一連の流れを通じて、
Webアプリケーション開発の全体像とその中でのバックエンドの責任範囲を、実感を持って理解することができました。

---

## 今後の改善案

- 作業名ごとに道具を絞り込んで表示できるソート機能の追加
- 午前・午後・退勤前など、チェックタイミングを分けたチェックマーク機能
- 担当者名や日付の入力欄を追加し、記録性を向上させる
- 写真付き or なしを選べるようにし、利用者の特性に合わせた柔軟な表示に対応
- 実際の現場で使いやすいように、UIをさらに簡潔・直感的に改善

---

## 制約事項・補足

- セキュリティ上の理由から、AWS環境は提出後に一時的に停止予定です。
- データ保存にはMySQLを使用していますが、今後セキュリティや永続性を高める改良も検討しています。
- フロントエンド部分はReactを使用し、ChatGPTのサポートを受けながら構築しました。  
  今回はJavaを学習してきた経緯もあり、主にSpring Bootを用いてアプリを作成し、Webアプリ全体の仕組みやAPI連携の流れを理解することを目的として取り組みました。  
  Reactについては、今後オンラインスクールの別講座で正式に学習する予定です。

---

## おわりに

このアプリはJavaとSpring Bootの学習の一環として、実際の業務課題をベースに開発したポートフォリオ作品です。  
コードや設計、使いやすさについてのフィードバックは大歓迎です。  

