# Blog Application

[우아한테크코스 - 웹 백엔드 과정](http://woowabros.github.io/woowabros/2019/02/08/woowacourse.html)에서 진행한 블로그 어플리케이션 미션의 결과물입니다. 

## 기술 스택

### 프론트엔드
웹 백엔드 과정이다보니 프론트엔드 코드를 우아한형제들 프론트엔드 개발자께서 제공해주셨습니다. 따라서 
필요한 부분만 수정해 사용했습니다.

### 백엔드
- Spring Boot
- JPA (Hibernate)
- JUnit

레이어드 아키텍처를 적극적으로 활용해 'Controller (presentation layer) 
\- Service (service layer) - Domain (domain layer) 
\- Persistence (persistence layer)' 로 관심사를 분리하려고 노력했습니다.

인수테스트와 서비스 단위테스트를 작성하였습니다.

객체지향 원칙을 준수하려고 노력했습니다.