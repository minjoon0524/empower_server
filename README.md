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
| Environment | Frontend                                                                                                  | Backend                                                                                             | Database                                                                                   | Deployment                                                                                     |
|-------------|-----------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------|
| ![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white) ![Visual Studio Code](https://img.shields.io/badge/Visual%20Studio%20Code-0078d7.svg?style=for-the-badge&logo=visual-studio-code&logoColor=white) ![IntelliJ](https://img.shields.io/badge/IntelliJ%20IDEA-black.svg?style=for-the-badge&logo=intellijidea&logoColor=white) | ![JavaScript](https://img.shields.io/badge/javascript-%23F7DF1E.svg?style=for-the-badge&logo=javascript&logoColor=black) ![React](https://img.shields.io/badge/react-%2361DAFB.svg?style=for-the-badge&logo=react&logoColor=black) ![Redux](https://img.shields.io/badge/redux-%23593d88.svg?style=for-the-badge&logo=redux&logoColor=white) | ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white) ![Spring Boot](https://img.shields.io/badge/spring%20boot-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) ![Spring Security](https://img.shields.io/badge/spring%20security-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) ![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens) | ![MySQL](https://img.shields.io/badge/mysql-%234479A1.svg?style=for-the-badge&logo=mysql&logoColor=white) | ![AWS EC2](https://img.shields.io/badge/AWS%20EC2-%23232F3E.svg?style=for-the-badge&logo=amazon-aws&logoColor=white) |





## 4. 시스템 아키텍처(구조)
![스크린샷 2024-11-27 155018](https://github.com/user-attachments/assets/29657246-2296-4ef5-93ee-1c19c46772d3)

## 5. 화면 흐름도
![스크린샷 2024-11-30 214408](https://github.com/user-attachments/assets/23921c45-dc2c-4c7f-9614-5d2c0aabfa0f)

## 6.화면 구성 및 주요 기능

### 1) 로그인
- 관리자는 사원을 추가하고, 사원은 부여받은 사번과 비밀번호로 로그인이 가능합니다. <br>

|로그인|
|:---:|
![로그인](https://github.com/user-attachments/assets/e8c5607f-93e7-46e5-a159-d6e628efedd7)

### 2) 인사관리
- 관리자와 사용자는 인사내역조회가 가능합니다. <br>
- 관리자는 사원추가,수정,삭제,권한부여가 가능합니다.(일반 사원은 불가능) <br>

|인적사항조회|
|:---:|
![인적사항조회](https://github.com/user-attachments/assets/0c4e4409-4401-4bf9-ab86-0d6bccf57ec0)


|사원추가|
|:---:|
![사원추가](https://github.com/user-attachments/assets/f97a1bd1-58d2-4363-915d-230442ae76aa)

|사원수정|
|:---:|
![회원수정](https://github.com/user-attachments/assets/780d7aec-487b-4299-8bc1-e05c89a97f9c)

|사원상세내역|
|:---:|
![사원상세내역](https://github.com/user-attachments/assets/8019b32d-703d-4718-a06f-da4e51c4571f)

|권한부여|
|:---:|
![권한부여](https://github.com/user-attachments/assets/7901e837-b987-4530-b1a1-20635e38df51)

### 3) 근태관리
- 사원은 출퇴근 등록을 할 수 있습니다. <br>
- 관리자는 사원 출퇴근 내역을 확인 할 수 있으며, 날짜를 선택 후 필터별 검색이 가능합니다.<br>

|출퇴근 등록|
|:---:|
![근태관리](https://github.com/user-attachments/assets/194185a5-2c67-4e55-9b7a-ae77168b3ba5)


|출퇴근 조회|
|:---:|
![근태조회](https://github.com/user-attachments/assets/1208a33f-4aab-4831-aa54-18dc89a5ecf0)


### 4) 휴가관리
- 사원은 휴가신청이 가능합니다. <br>
- 사원은 내 휴가신청내역조회가 가능합니다. <br>
- 관리자는 사원 휴가내역 조회가 가능하며 휴가신청시 승인 또는 반려할 수있습니다.<br>
- 휴가 신청시 관리자에게, 승인,반려시 사용자에게 이메일 알림이 갑니다.<br>
- 관리자에게 승인되지 않은 휴가는 수정및삭제가 가능합니다.<br>

|휴가 신청|
|:---:|
![휴가신청](https://github.com/user-attachments/assets/a25e88fe-8e9f-400a-a1dd-18b4bbb4d9ad)

|휴가 승인 및 반려|
|:---:|
![휴가 신청 승인 및 거절](https://github.com/user-attachments/assets/97009cef-a202-4929-8b26-9cac614cf6df)

|사원 휴가신청내역|
|:---:|
![내 휴가 신청내역](https://github.com/user-attachments/assets/1208f519-386c-44f7-9b38-84a2aa92cb19)

|휴가 수정 및 삭제|
|:---:|
![휴가 삭제 및 수정](https://github.com/user-attachments/assets/596ecc9a-abb0-4ae6-9a48-efcfc941c86b)




