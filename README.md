## 🎯 프로젝트 개요

이 프로젝트는 **헥사고날 아키텍처**(또는 포트와 어댑터 아키텍처) 패턴을 사용하여 시스템의 **유연성**, **테스트 용이성**, **분리된 책임**을 유지하며 리팩토링되었습니다. 기존의 레이어드 아키텍처를 헥사고날 아키텍처로 리팩토링하여 각 구성 요소의 경계를 명확하게 구분하고, 외부 시스템과의 통합을 쉽게 관리할 수 있도록 했습니다.
헥사고날 아키텍처로 리팩토링한 코드는 main, 전통적인 레이어드 아키텍처 코드는 [layerd 브랜치](https://github.com/hyukjunkim1116/spring-hexagonal/tree/layerd)에 있습니다.

## 🏗️ 아키텍처 개요

헥사고날 아키텍처는 **핵심 도메인 로직**을 중심으로 다양한 어댑터(입력, 출력)를 통해 외부 시스템과 상호작용하는 구조를 가집니다. 이 아키텍처는 다음과 같은 주요 구성 요소로 나눌 수 있습니다:

- **핵심 도메인**(Domain): 비즈니스 로직과 도메인 엔티티를 포함합니다.
- **입력 포트(Input Port)**: 클라이언트나 사용자 요청을 처리하는 인터페이스입니다.
- **출력 포트(Output Port)**: 외부 시스템과의 인터페이스를 정의하는 포트입니다.
- **입력 어댑터(Input Adapter)**: 웹 요청과 같은 외부 입력을 받아서 입력 포트를 호출하는 역할을 합니다.
- **출력 어댑터(Output Adapter)**: 데이터베이스나 외부 API 호출을 통해 출력 포트를 호출하는 역할을 합니다.

## 🔧 프로젝트 구성

### 1. **도메인 레이어 (Domain Layer)**

핵심 비즈니스 로직과 도메인 모델이 포함되어 있습니다. 이 레이어는 외부와의 의존성을 최소화하며, 비즈니스 규칙에 집중합니다.  
- **도메인 엔티티**(Entity): 비즈니스 객체들을 정의합니다.
- **유스케이스**(Use Cases): 시스템의 주요 기능을 정의하는 서비스 계층입니다.

### 2. **포트와 어댑터**

헥사고날 아키텍처에서는 입력 포트와 출력 포트를 통해 외부 시스템과의 상호작용을 추상화합니다. 포트는 인터페이스로 정의되며, 어댑터가 이 인터페이스를 구현하여 실제 요청을 처리합니다.

- **입력 포트 (Input Port)**: 시스템의 주요 기능을 정의합니다. 클라이언트 요청을 받아서 도메인 로직을 호출합니다.
- **출력 포트 (Output Port)**: 데이터 저장소나 외부 API 호출을 정의합니다. 도메인 로직에서 데이터를 저장하거나 조회할 때 사용됩니다.

## 📂 프로젝트 디렉토리 구조

- 헥사고날 아키텍처

```
├── common
│   ├── controller
│   │   ├── ExceptionControllerAdvice.java
│   │   └── web
│   │       ├── argumentresolver
│   │       │   ├── Login.java
│   │       │   └── LoginUserArgumentResolver.java
│   │       └── interceptor
│   │           └── LoginCheckInterceptor.java
│   ├── domain
│   │   ├── ErrorResponse.java
│   │   └── exception
│   │       ├── AlreadyExistsEmailException.java
│   │       ├── AlreadyExistsUsernameException.java
│   │       ├── PasswordNotMatchException.java
│   │       ├── UserException.java
│   │       └── UserNotFound.java
│   └── service
│       └── SessionManager.java
├── config
│   └── WebMvcConfig.java
└── user
    ├── controller
    │   ├── UserController.java
    │   └── request
    │       ├── LoginRequest.java
    │       ├── SignupRequest.java
    │       └── UserInfo.java
    ├── domain
    │   └── User.java
    ├── infrastructure
    │   ├── PasswordEncoderImpl.java
    │   ├── UserEntity.java
    │   ├── UserJpaRepository.java
    │   └── UserRepositoryImpl.java
    └── service
        ├── UserService.java
        └── port
            ├── PasswordEncoder.java
            └── UserRepository.java
```

- 레이어드 아키텍처

```
├── config
│   ├── LoginCheckInterceptor.java
│   ├── WebConfig.java
│   └── argumentresolver
│       ├── Login.java
│       └── LoginUserArgumentResolver.java
├── user
│   ├── controller
│   │   ├── UserController.java
│   │   └── UserExceptionController.java
│   ├── domain
│   │   └── User.java
│   ├── dto
│   │   ├── ErrorResponse.java
│   │   ├── LoginRequest.java
│   │   ├── SignupRequest.java
│   │   └── UserInfo.java
│   ├── exception
│   │   ├── AlreadyExistsEmailException.java
│   │   ├── PasswordNotMatchException.java
│   │   ├── UserException.java
│   │   └── UserNotFound.java
│   ├── repository
│   │   └── UserRepository.java
│   └── service
│       └── UserService.java
└── util
    ├── PasswordEncoder.java
    └── SessionConst.java
```

## 🔄 주요 변경 사항

기존의 레이어드 아키텍처는 계층 간의 의존성에 의해 각 레이어가 서로 밀접하게 연결되어 있었습니다. 이로 인해 테스트와 유지보수가 어려웠습니다. 헥사고날 아키텍처로 리팩토링하면서 개선된 부분은

0. Fat Service의 종말과 OOP스러운 코드의 등장 :
![1](https://github.com/user-attachments/assets/f3dc1d02-0901-4594-b747-7ea0454db457)

2. 도메인 로직의 독립성: 도메인 로직은 외부 시스템과 분리되어 독립적이며, 테스트 용이성이 높아졌습니다. 수월한 단위 테스트 작성이 가능해졌습니다.
3. 어댑터의 역할 분리: 웹, 데이터베이스, 외부 API와의 연결을 어댑터에서 처리하므로, 도메인 로직은 이러한 외부 시스템에 대한 의존성이 없습니다.
4. 확장성: 새로운 입력 어댑터나 출력 어댑터를 추가하는 것이 쉬워졌습니다. 예를 들어, 새로운 API 또는 데이터 저장소를 추가할 때 기존 도메인 로직에 대한 변경 없이 어댑터만 추가하면 됩니다.

