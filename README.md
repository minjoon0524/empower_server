## 1. 프로젝트 소개
- 프로젝트 이름 : EMPOWER
- 프로젝트 내용 : 인사 관리, 근태 관리, 휴가관리를 통합한 사내 인사관리 웹프로그램

### 개발 기간
> 2024.09.02 - 2024.11.27 <br>

### 인원 구성
> 유민준(1인)

## 2. 주요기능
- 인사관리 : 사원등록 및 인사정보 관리
- 근태관리 : 사원 출퇴근 시간 등록, 사원 근태관리(관리자)
- 휴가관리 : 휴가 신청 및 승인 반려, 승인 반려시 사용자 및 관리자에게 이메일 알림
- 보안 : SpringSecurity JWT 활용, IP접근제한

## 3. 개발환경
| 구분 | 내용 |
| --- | --- |
| 개발툴(프론트) | Visual Studio Code |
| 개발툴(백엔드) | IntelliJ Ultimate IDE |
| 사용언어(프론트) | JacaScript,React |
| 사용언어(백엔드) | Java |
| 프레임워크(백엔드) | SpringBoot |
| 데이터베이스 | MySql |
| 백엔드 기술 세부스택 | SpringSecurity, JWT |
| 프론트엔드 기술 세부스텍 | Redux,react-cookie,Axios |
| 배포 | AWS EC2 |

## 4. 시스템 아키텍처(구조)
![스크린샷 2024-11-27 155018](https://github.com/user-attachments/assets/29657246-2296-4ef5-93ee-1c19c46772d3)

## 5. 화면 흐름도
![스크린샷 2024-11-30 214408](https://github.com/user-attachments/assets/23921c45-dc2c-4c7f-9614-5d2c0aabfa0f)

## 6.화면 구성 및 주요 기능

### 1) 로그인
- 관리자는 사원을 추가하고, 사원은 부여받은 사번과 비밀번호로 로그인이 가능합니다. <br>

|로그인|
|:---:|
![로그인](https://github.com/user-attachments/assets/a6d70e87-1e50-4183-885a-0e97798ab785)

### 2) 인사관리
- 관리자와 사용자는 인사내역조회가 가능합니다. <br>
- 관리자는 사원추가,수정,삭제,권한부여가 가능합니다.(일반 사원은 불가능) <br>

|인적사항조회|
|:---:|
![인적사항조회](https://github.com/user-attachments/assets/2e70f214-f754-42b5-beed-1d882a04f73e)


|사원추가|
|:---:|
![사원추가ㅣ](https://github.com/user-attachments/assets/a836a7bb-7b72-4290-b644-67229b14f3fa)

|사원수정|
|:---:|
![사원수정](https://github.com/user-attachments/assets/d1864d4e-c023-4c4c-9b1e-2480b57d0429)

|사원상세내역|
|:---:|
![사원상세내역](https://github.com/user-attachments/assets/8019b32d-703d-4718-a06f-da4e51c4571f)

|사원수정|
|:---:|
![사원수정](https://github.com/user-attachments/assets/d1864d4e-c023-4c4c-9b1e-2480b57d0429))

### 3) 근태관리
- 사원은 출퇴근 등록을 할 수 있습니다. <br>
- 관리자는 사원 출퇴근 내역을 확인 할 수 있으며, 날짜를 선택 후 필터별 검색이 가능합니다.<br>

|출퇴근 등록|
|:---:|
![근태](https://github.com/user-attachments/assets/a1129c59-2a1d-4547-8f79-adad44c1f13c)

|출퇴근 조회|
|:---:|
![근태 관리자](https://github.com/user-attachments/assets/9bf7cad0-d049-4853-8a9e-8cc679b50e2a)


### 4) 휴가관리
- 사원은 휴가신청이 가능합니다. <br>
- 사원은 내 휴가신청내역조회가 가능합니다. <br>
- 관리자는 사원 휴가내역 조회가 가능하며 휴가신청시 승인 또는 반려할 수있습니다.<br>
- 휴가 신청시 관리자에게, 승인,반려시 사용자에게 이메일 알림이 갑니다.<br>
- 관리자에게 승인되지 않은 휴가는 수정및삭제가 가능합니다.<br>

|휴가 신청|
|:---:|
![휴가신청](https://github.com/user-attachments/assets/804cd3bf-f055-4a44-8876-5c5902ace959)

|휴가 승인 및 반려|
|:---:|
![관리자 휴가](https://github.com/user-attachments/assets/8cef0742-1e2f-4c68-850b-f35f65c0d340)

|사원 휴가신청내역|
|:---:|
![내 휴가 신청내역](https://github.com/user-attachments/assets/1208f519-386c-44f7-9b38-84a2aa92cb19)

|휴가 수정 및 삭제|
|:---:|
![휴가 신청 수정 및 삭제](https://github.com/user-attachments/assets/ffbd9eb4-11e8-47c4-bc43-718d87221e5f)




