# Todo App

간단한 할 일 목록을 관리하는 Android 어플리케이션입니다.

## 기능

- 할 일 추가, 수정, 삭제
- 할 일 목록 보기
- 북마크 된 할 일 목록 보기
- 할 일에 대한 상세 내용 확인


## 사용 기술

- Kotlin
- Android MVVM 아키텍처
- ViewModel
- LiveData
- ViewBinding
- Timber (로그 기록)

## 빌드 및 실행

프로젝트는 Android Studio에서 빌드 및 실행할 수 있습니다. 최신 버전의 Android Studio를 설치하고 프로젝트를 열어주세요.

```bash
git clone https://github.com/your-username/TodoApp.git
```

그리고 Android Studio에서 해당 프로젝트를 엽니다.

## 프로젝트 구조

프로젝트는 다음과 같은 구조로 이루어져 있습니다.

```
__todo_app
│
├── ui
│   ├── bookmark
│   │   ├── BookmarkListEvent.kt
│   │   ├── BookmarkListAdapter.kt
│   │   ├── BookmarkListFragment.kt
│   │   └── BookmarkViewModel.kt
│   │
│   └── todo
│       ├── content
│       │   ├── TodoContentConstant.kt
│       │   ├── TodoContentEvent.kt
│       │   ├── TodoContentActionType.kt
│       │   ├── TodoContentUiState.kt
│       │   ├── TodoContentViewModel.kt
│       │   ├── TodoContentActivity.kt
│       │   └── TodoContentEntryType.kt
│       │
│       ├── list
│       │   ├── TodoListViewModel.kt
│       │   ├── TodoListFragment.kt
│       │   ├── TodoListAdapter.kt
│       │   ├── TodoListViewType.kt
│       │   └── TodoListEvent.kt
│       │
│       └── main
│           ├── TodoMainActivity.kt
│           ├── TodoMainViewModel.kt
│           ├── TodoMainViewPagerAdapter.kt
│           └── TodoMainTab.kt
│
├── util
│   └── SingleLiveEvent.kt
│
└── data
    ├── TodoListItem.kt
    └── TodoEntity.kt

```

- `app`: 앱 모듈
    - `data`: 데이터 관련 클래스 (Entity 등)
    - `ui`: UI 관련 클래스 (Activity, ViewModel, Adapter 등)
    - `util`: 유틸리티 클래스
    - `MainActivity.kt`: 앱의 진입점 및 메인 액티비티
