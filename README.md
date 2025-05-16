# 🎟️ 동시성 환경에서 티켓 예약 시스템 테스트

## 🎯 Branch Convention & Git Convention
### 🎯 Git Convention
- 🎉 **Start:** Start New Project [:tada]
- ✨ **Feat:** 새로운 기능을 추가 [:sparkles]
- 🐛 **Fix:** 버그 수정 [:bug]
- 🎨 **Design:** CSS 등 사용자 UI 디자인 변경 [:art]
- ♻️ **Refactor:** 코드 리팩토링 [:recycle]
- 🔧 **Settings:** Changing configuration files [:wrench]
- 🗃️ **Comment:** 필요한 주석 추가 및 변경 [:card_file_box]
- ➕ **Dependency/Plugin:** Add a dependency/plugin [:heavy_plus_sign]
- 📝 **Docs:** 문서 수정 [:memo]
- 🔀 **Merge:** Merge branches [:twisted_rightwards_arrows:]
- 🚀 **Deploy:** Deploying stuff [:rocket]
- 🚚 **Rename:** 파일 혹은 폴더명을 수정하거나 옮기는 작업만인 경우 [:truck]
- 🔥 **Remove:** 파일을 삭제하는 작업만 수행한 경우 [:fire]
- ⏪️ **Revert:** 전 버전으로 롤백 [:rewind]
### 🪴 Branch Convention (GitHub Flow)
- `main`: 배포 가능한 브랜치, 항상 배포 가능한 상태를 유지
- `feature/{description}`: 새로운 기능을 개발하는 브랜치
    - 예: `feature/add-login-page`
### Flow
1. `main` 브랜치에서 새로운 브랜치를 생성.
2. 작업을 완료하고 커밋 메시지에 맞게 커밋.
3. Pull Request를 생성 / 팀원들의 리뷰.
4. 리뷰가 완료되면 `main` 브랜치로 병합.
5. 병합 후, 필요시 배포.
   **예시**:
```bash
# 새로운 기능 개발
git checkout -b feature/add-login-page
# 작업 완료 후, main 브랜치로 병합
git checkout main
git pull origin main
git merge feature/add-login-page
git push origin main
```

    
[TEST 1. 어떠한 설정도 추가하지 않고 실행](https://www.notion.so/TEST-1-1ebec761786c8023a9faff1ec04e8c1f)

[TEST 2. DB 락 + 벌크 Insert를 이용한 동시성 제어 및 Insert 성능 향상](https://www.notion.so/TEST-2-DB-Insert-Insert-1e6ec761786c80128c7cf624e6d98673?pvs=21)

[TEST 3. open-in-view 설정을 통한 DB 커넥션 조절](https://www.notion.so/TEST-3-open-in-view-DB-1ebec761786c80de82d1e396d3b0f8a6?pvs=21)

[TEST 4. `비관적 락 + 중복방지 + 인덱스 + 네이티브 쿼리`](https://www.notion.so/TEST-4-1ecec761786c8086aa9fe934604bc009?pvs=21)

[TEST 5. `Projection을 사용하여 성능 개선하기`](https://www.notion.so/TEST-5-Projection-1f2ec761786c80e2a1e4fc6836cfd051?pvs=21)

[TEST 6. `In-Memory 캐시 + Sharding`을 사용한 성능 최적화](https://www.notion.so/TEST-6-In-Memory-Sharding-1f2ec761786c80c59adcd9f65a793b66?pvs=21)
