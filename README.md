# 📱 mp_practice

숭실대학교 **모바일 프로그래밍** 수업 실습 코드 저장소입니다.
수업 시간에 진행한 예제를 날짜별로 정리합니다.

## 개발 환경

| 항목 | 내용 |
| --- | --- |
| IDE | Android Studio |
| 언어 | Kotlin |
| 화면 구성 | XML 레이아웃 (Empty Views Activity) |

## 폴더 구조

```
mp_practice/
├── README.md
└── practiceYYMMDD/          ← 실습 날짜 (예: practice261007)
    ├── settings.gradle.kts
    ├── mpXXYY/              ← 예제 XX-YY (예: mp0401 = 예제 4-1)
    └── ...
```

- 날짜 폴더(`practiceYYMMDD`) 하나가 **Android Studio 프로젝트 하나**예요.
- 그 안의 `mpXXYY` 폴더는 **예제 하나 = 앱 모듈 하나**예요. (XX: 장 번호, YY: 예제 번호)

## 실행 방법

1. Android Studio에서 **File → Open →** 원하는 날짜 폴더(예: `practice261007`)를 엽니다.
2. Gradle Sync가 끝날 때까지 기다립니다.
3. 상단 실행 구성 드롭다운에서 모듈(예: `mp0401`)을 선택하고 ▶ 실행합니다.
