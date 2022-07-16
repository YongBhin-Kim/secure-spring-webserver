<h2>(국민대학교 학부연구생) SpringBoot Client-to-Server 암/복호화 통신 - Java Native Interface </h2>

**[Docker image Environment]**
- Docker image
- Language : Java, C
- Framework : Spring Boot(Gradle)
<br><br>

**[제공하는 것]**
1. Docker Image 다운로드 & 실행 방법 <br>
2. Client-Server 통신 <br>
3. Docker Image 생성 <br>
- ver1.0 : 간단한 스프링 부트 이미지
- - Docker Image : `docker pull coji68/web-server:1.0`
- ver1.1 : 스프링 부트 도커 이미지를 이용한 통신
- - Docker Image(Server) : `docker pull coji68/web-server:1.1`
- - Client : `git clone https://github.com/YongBhin-Kim/WebServer.git` <br>
- ver1.2 : JNI를 이용한 암/복호화 통신 스프링 부트 도커 이미지
- - Docker Image(Server) : `docker pull coji68/web-server:1.2` (업데이트 완료)
- - Client : `git clone https://github.com/YongBhin-Kim/WebServer.git`
- - Docker Image(Client) : x (로컬 실행)
- ver1.2.1 : 
- - Docker Image(Server) 최적화 테스트 단계 (오류 수정 완료)
- ver1.2.2 : 
- - Docker Image(Server) : `docker pull coji68/web-server:1.2.2` (22-07-01 업데이트, 도커 이미지 파일 인식 오류)
<br><br><br>


<h3>[1. Docker Image 다운 및 실행 방법]</h3> <br>

**[Spring Boot Docker image (Server) 실행하기]** <br>
0. Docker Container 로그인<br>
1. Docker image 다운 <br>
- Docker image 링크 - https://hub.docker.com/r/coji68/web-server/tags <br>
- 터미널에 명령어 `docker pull coji68/web-server:ver1.x` 을 입력한다. <br>
<img width="750" height="30" alt="image" src="https://user-images.githubusercontent.com/98372474/166339548-7a06b54f-304e-4016-8d87-5a3c66c9c7f9.png"> <br>

2. 다운로드한 image 확인 명령어 : `docker images` <br>
<img width="800" height="50" alt="image" src="https://user-images.githubusercontent.com/98372474/166337688-b2a4e403-d952-4773-842b-ec4349bf4cfa.png"> <br>

3. 다운로드한 image를 컨테이너 생성과 동시에 실행 <br>
- Docker Image 실행 명령어 <br>
 `docker run -p [browser port number]:[container port number] -t [docker repository name/image name:tag]` <br><br>
 ver1.0 : `docker run -p 8080:8080 -t coji68/web-server:1.0` <br>
 ver1.1 : `docker run -p 10000:10000 -t coji68/web-server:1.1` <br>
 ver1.2 : `docker run -p 10000:10000 -t coji68/web-server:1.2` <br>
 ver1.2.x : `docker run -p 8080:8080 -t coji68/web-server:1.2.x` <br>
 <br><br>

- 명령어 실행 결과 Spring이 정상 작동<br>
<img width="700" height="300" alt="image" src="https://user-images.githubusercontent.com/98372474/167175447-7dee3ec2-b738-4fb4-ac6a-f9a1dac39390.png"> <br>

- 브라우저를 통해 localhost로 접속한 결과(ver1.0) <br>
<img width="500" height="250" alt="image" src="https://user-images.githubusercontent.com/98372474/166337952-26e86920-adf7-4ed7-9ce5-8c0130c6d516.png"><br><br>


<h3>[2. Server-Client 통신] </h3><br>

- Server (ver 1.1)
- Server (ver 1.2)
- Server (ver 1.2.2)

**[통신을 위한 Server]** <br>
- 다운받은 도커 이미지를 브라우저/컨테이너 포트번호를 10000으로 열어준다.(스프링 내부 구현을 10000포트로 했습니다.)
- 터미널에 명령어 입력 `docker run -p 10000:10000 coji68/web-server:1.1` <br>
- 터미널에 명령어 입력 `docker run -p 10000:10000 coji68/web-server:1.2` <br>

**[통신을 위한 Client]** <br>

clone한 클라이언트를 사용하려면 몇몇 설정과 명령을 수정해야 하며 windows와 macOS의 사용 방법이 다르다.<br>
[1. (Windows만 해당) jni_md.h 파일을 다음과 같이 수정한다.] <br>

![image](https://user-images.githubusercontent.com/98372474/174834119-74b35e3c-ad67-4c37-b539-10e1d55296b9.png)<br>
<br>

[2. java 클래스파일 생성] <br>
- 윈도우는 기본 MS949 인코딩을 사용하므로 작업 파일의 인코딩 UTF-8로 수정하여 javac 명령을 사용한다.

macOS : javac Client.java <br>
Windows : javac Client.java -encoding UTF-8 <br>
<br>

[3. 헤더 재생성] <br>
macOS : `javac Client.java -h .` <br>
Windows : `javac Client.java -h . -encoding UTF-8` <br>
<br>

[4. 라이브러리 컴파일] <br>
macOS : `gcc -I”/[JDK 경로]/Contents/Home/include" -I”/[JDK 경로]/Contents/Home/include/darwin" -o libBlockCipher.jnilib -shared Client.c` <br>
Windows : 내pc 우클릭 -> 속성 -> 관련설정 -> 고급 시스템 설정 -> 환경 변수 -> 시스템 변수 -> CLASSPATH의 변수값을 `%JAVA_HOME%\lib;.` 으로 설정 <br>
Windows : `gcc -I"[jdk경로]/include" -I"[jdk경로]/include/win32" -o libBlockCipher.jnilib -shared Client.c` <br>
<br>

[5. 라이브러리 경로 가져오기] <br>
macOS : `java -Djava.library.path=. Client` <br>
Windows : `java -Djava.library.path=[라이브러리(libBlockCipher.jnilib) 절대경로] Client` <br>
<br>

[6. (공통)클라이언트 컴파일] <br>
Client.java 파일이 존재하는 폴더 내에서 명령어 입력 <br>
`java Client.java` <br>
<br>
-----------------------------------------<br>
<br>

**다음과 같이 Server-Client가 통신이 가능하다.(위쪽 : Server / 아래쪽 : Client)** <br>
(예시 - 통신의 순서)
- Server - 포트번호 10000으로 서버를 열고 클라이언트의 접속을 기다린다.
- Client - 포트번호 10000으로 접속을 요청한다.
- Server-Client 연결 완료
- Client - Input : 'message' 작성 
- Server - Client로부터 전달받은 'message'를 Client에게 재전송
- Client - From Server : 'message' 서버로부터 재전송받은 메시지를 standard output(모니터)으로 출력

**다음과 같이 Server-Client가 통신이 가능하다.(위쪽 : Server / 아래쪽 : Client)** <br>
- ver 1.1
<img width="1429" alt="image" src="https://user-images.githubusercontent.com/98372474/167178601-d1e7a872-c7f9-4681-8b37-e68ef0f0b977.png"><br><br>

<br><br><br>
  
<h3>[2. JNI를 이용한 암/복호화가 추가된 Server-Client 통신 (for ver1.2)] </h3><br>

**[JNI를 이용하여 ver1.1 위에 C language 블록암호 암호화/복호화 과정을 추가한다.]** <br>

- (Client) - jni 이용을 선언한다.
- (C) - 입력받은 메시지를 블록암호를 이용하여 암호화한 후 Client로 넘긴다.
- (Client) - Server로 암호문을 전송한다.
- (Server) - jni를 이용하여 Client로부터 받은 암호문을 C로 넘긴다.
- (C) - Server로부터 받은 암호문을 블록암호를 이용하여 복호화한 후 복호문(= 평문)을 Server로 넘긴다.
- (Server) - Client로부터 받은(C로 복호화한) 평문을 확인한다.

- (Server) - jni 이용을 선언한다. 
- (C) - 입력받은 메시지를 블록암호를 이용하여 암호화한 후 Server로 넘긴다.
- (Server) - Client로 암호문을 전송한다.
- (Client) - jni를 이용하여 Server로부터 받은 암호문을 C로 넘긴다.
- (C) - Client로부터 받은 암호문을 블록암호를 이용하여 복호화한 후 복호문(= 평문)을 Client로 넘긴다.
- (Client) - Server로부터 받은(C로 복호화한) 평문을 확인한다.

- Client 암/복호화 구현 및 서버와의 통신 구현 기능 구현
- ASCII에 속한 모든 문자 구현
- Server 암/복호화 구현 (진행중 - 서버에서는 의존성을 수정해야 한다.) ###

**다음과 같이 Server-Client가 JNI를 이용하여 암/복호화된 통신이 가능하다.** <br>
**[Server]**

<img width="714" alt="image" src="https://user-images.githubusercontent.com/98372474/174623814-4b2e4f7a-ebed-46dd-ae15-f7071f108c78.png"> <br>

**[Client]**

<img width="841" alt="image" src="https://user-images.githubusercontent.com/98372474/174623887-fb366591-93a6-4883-af1a-8fa23dbd034b.png"> <br>
<br>

- ver 1.2.2
<img width="1436" alt="image" src="https://user-images.githubusercontent.com/98372474/176786776-1e5df1e4-6aac-4f9c-addc-639da4226cb0.png"><br><br>

<img width="1429" alt="image" src="https://user-images.githubusercontent.com/98372474/167178601-d1e7a872-c7f9-4681-8b37-e68ef0f0b977.png"><br><br>


------------------------------------------------------------------------------------------------------------------------------
<h3>[3. Spring Boot Docker image 생성, Docker Hub에 올리기]</h3> <br>

- 먼저 Docker Desktop을 실행합니다.

- Spring Boot 코드 clone, SNAPSHOT 제대로 동작 하는지 체크
```
git clone https://github.com/spring-guides/gs-spring-boot-docker.git // spring image clone

./gradlew build && java -jar build/libs/initial-0.0.1-SNAPSHOT.jar // SNAPSHOT check

```
<img width="400" height="280" alt="image" src="https://user-images.githubusercontent.com/98372474/166336173-c5f9382b-d004-44e1-a95e-1427e35b16ff.png"> <br>


- [Dockerfile 생성]

-Docker Image를 만들기 위해 Dockerfile이 필요하다. gradlew와 동등한 위치 `.../gs-spring-boot-docker/initial/Dockerfile` 에 만들어준다.

```
FROM openjdk:8-jdk-alpine // openjdk 사용
ARG JAR_FILE=target/*.jar // SNAPSHOT 사용
COPY ${JAR_FILE} app.jar 
ENTRYPOINT ["java","-jar","/app.jar"]
```
<br>

- [Spring boot Docker Image 생성]<br>

- Spring Boot Docker Image 생성 명령어는 다음과 같습니다.

`docker build --build-arg JAR_FILE=[jar파일 경로] -t [도커레포지토리:태그] [Dockerfile위치](.은 현재디렉토리)` <br>
<img width="900" height="20" alt="image" src="https://user-images.githubusercontent.com/98372474/166337129-e494bf25-8913-41b9-933a-c4f5ee2e5e62.png"> <br>

- `docker images` 명령으로 생성된 이미지 확인 <br>

<img width="1002" alt="image" src="https://user-images.githubusercontent.com/98372474/167173172-f1ea37b2-c369-4b10-830e-11496fd6a9e7.png"> <br>


- docker image 삭제 명령어 : `docker image rm [Image ID]` <br>

- [만든 이미지 Docker Hub에 올리기]

- 우선 터미널에서 `Docker login` 명령으로 도커에 로그인 한다. 

<img width="1340" alt="image" src="https://user-images.githubusercontent.com/98372474/167173758-25a5d22a-daf9-42ea-afb6-24315b58e198.png">

- docker push [레포지토리]:[태그] 명령으로 도커 레포지토리에 push 한다.

<img width="898" alt="image" src="https://user-images.githubusercontent.com/98372474/167174523-79ac92e5-4fb5-46d9-a4f8-9e79db14b5be.png">

- 도커 레포지토리에 올라갔는지 확인한다.
- https://hub.docker.com/
<img width="1270" alt="image" src="https://user-images.githubusercontent.com/98372474/167174484-82a427f9-e9d2-49a5-8301-3600616d8e46.png">

