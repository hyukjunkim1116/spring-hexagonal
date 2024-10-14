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
- **유스케이스**(Use Cases, Service): 시스템의 주요 기능을 정의하는 서비스 계층입니다.

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

1. 폴더 구조 변경 :
    - Repository -> Infrastructure : 한정된 의미의 Repository를 광범위한 Infrastructure로 바꾸었습니다. DB 한정으로 사용되는 단어를 Output Port 개념으로 바꾸어 외부 시스템과의 연동을 유연하게 받아들이겠다는 의미로 확장됩니다.
    - 각 계층 하위에 port 패키지 생성 : port 패키지는 인터페이스를 모아두는 곳으로 각 계층은 하위 구현 클래스를 바로 참조하지 않고 인터페이스를 참조합니다. 따라서 하위 계층이 어떻게 구현되어 있는지 알지 못하게 되므로 결합도가 약화됩니다.
    - common/controller/web 패키지 생성 : 프로젝트에 사용되는 스프링 인터셉터, ArgumentResolver는 컨트롤러에서 실행되기 전에 웹 계층에서 작동하는 기능입니다. user 패키지 외에 다른 패키지에서도 사용된다는 가정하에 common 하위에 넣어두었고 Input Port에 해당하는 웹 계층이므로 controller/web에 넣었습니다.


2. Fat Service의 종말과 OOP스러운 코드의 등장 : 기존의 서비스는 절차지향스럽게 작성하였으나 개선된 서비스 코드는 User 도메인에 비즈니스 책임을 분리하고 PasswordEncoder와 SessionManager에 각각의 기능을 분리하여 각 객체간의 협력을 이끌어내는 파사드로써의 책임만을 주었습니다.

![2](https://github.com/user-attachments/assets/49b0df1f-df30-4316-ba42-7267cd715619)

3. 도메인 로직의 독립성: 도메인 로직을 순수하게 작성하여 프로젝트의 핵심인 비즈니스 로직에만 집중할 수 있도록 작성했습니다. 이로 인해 Testability가 높아졌으며 개발자 관심사의 분리가 이루어졌습니다.
기존의 @Entity가 붙은 User를 활용했다면 이제는 DB 엔티티인 UserEntity와 비즈니스 도메인인 User로 분리하여 프로젝트의 핵심 로직이 외부 기술의 영향을 받지 않게 되었습니다.

![user](https://github.com/user-attachments/assets/b8ac9082-e859-4532-b838-f667264ee26c)

5. 어댑터의 역할 분리: 웹, 데이터베이스, 외부 API와의 연결을 추상화하여 포트에는 인터페이스, 어댑터에는 구현 클래스를 배치했습니다. 유연한 기술 교체가 가능해졌습니다. 예를 들어, 기존의 JpaRepository만을 상속받은 UserRepository를 사용하였지만, 이를 UserRepository(포트), UserJpaRepository(외부 기술), UserRepositoryImpl(어댑터)로 구현하여 JPA를 다른 기술로 바꾸는 것이 언제든지 가능합니다.

6. 수월한 테스트 작성 : 기존의 레이어드 아키텍처는 절차지향적인 사고 유도로 인해 개발자가 핵심 비즈니스 로직에 집중하지 못하게 합니다. 따라서 의미 없는 테스트(메소드 중심 테스트)를 작성할 확률이 높아집니다. 반면 헥사고날 아키텍처는 외부 기술과 핵심 비즈니스 로직을 분리함으로써 개발자가 중요한 유스케이스를 떠올리게 만드며 유효한 테스트를 작성하게 유도합니다. 또한 포트-어댑터 패턴을 사용함으로써 테스트 전용 mock 객체를 만들 수 있게 됩니다. 스프링을 실행시키지 않고도 단위 테스트를 빠르게 실행할 수 있습니다.

- 레이어드 아키텍처에서의 테스트
  
![기존테스트1](https://github.com/user-attachments/assets/1677b822-c39d-476b-b022-06e282588fc9)

- 헥사고날 아키텍처에서의 테스트
  
![헥사고날테스트2](https://github.com/user-attachments/assets/1410dcf7-8708-4bd5-aa3b-48192c065fa6)

## 🚀 레이어드 아키텍처 vs 헥사고날 아키텍처

- 레이어드 아키텍처의 장점 : 쉽다. 팀원들이 잘 따라온다.
- 헥사고날 아키텍처의 장점 : 유연하다, 테스트하기 쉽다, 유지 보수에 용이하다, OOP스럽다

- 레이어드 아키텍처의 단점 : 테스트하기 힘들다, 유지 보수하기 힘들다, 절차지향적이다
- 헥사고날 아키텍처의 단점 : 구현하기 복잡하다, 초반 개발 시간이 증가한다.

## 📚 개인 의견

1. Controller, Infrastructure 계층은 테스트가 불필요하다고 생각합니다
    - controller는 스프링에서, infrastructure는 외부 라이브러리에서 충분히 테스트 후 릴리즈 했으므로 한번 더 테스트하는 모양새가 됩니다.

2. 테스트 프레임워크 사용을 지양해야 합니다
   - 테스트 프레임워크 사용 시에 어떻게든 테스트를 통과할 수 있으므로 테스트를 위한 테스트 코드가 만들어집니다. 이는 더 나은 아키텍처에 대한 고민을 없앱니다. 따라서 순수 자바만으로 실행되는 코드 작성을 지향하되, 반복된 작업이나 가독성을 위해 테스트 프레임워크를 사용해야 한다고 생각합니다

3. 헥사고날 아키텍처만이 정답은 아닙니다
   - 결국 아키텍처는 상황 by 상황입니다. 초기 개발 단계는 MVP를 추구한다면 전통적인 레이어드 아키텍처가 맞을 수 있습니다. 하지만 규모가 커진다면 결국 더 나은 아키텍처를 위한 고민을 해야 합니다. 기술을 바꿔야 하는 상황이 생길 수도 있고 다른 팀원이 합류하는 상황이 생길 수 있습니다. 헥사고날 아키텍처가 정답은 아니지만 이정표가 되어줄 수 있습니다.
  
4. 절차지향은 틀리고 객체지향은 맞다는 뜻이 아닙니다
   - 절차지향과 객체지향은 각각의 장단점이 있습니다. 다만 자바는 객체 지향이고 스프링도 그 점을 이용해서 만들어진 프레임워크이기 때문에 이를 활용하는 것은 유용합니다. 따라서 절차지향과 객체지향을 섞어 쓰면서 둘의 장점을 흡수해야 합니다. 결국 코드는 사람이 읽기 때문에 가독성과 설득력을 추구해야 합니다.
  
## 🛠️ 개선해야 할 부분

1. UserService에 반복되는 if 문 : 현재는 if문이 몇개 안되지만 검증 해야하는 경우가 많아지면 가독성이 떨어진다. Validator를 구현하여 따로 분리해야 할 것 같다.

2. UserService의 분리 : UserService에 많은 기능이 추가된다면 회원가입,로그인,로그아웃등과 나머지를 분리해야 할 필요가 있을 것 같다. Authentication 만의 유효성 검사와 의존성이 있기 때문이다. 예를 들어 이메일을 보내는 기능을 추가한다면 이메일과 관련된 CertificationService를 만들어서 역할을 분리한다. 



