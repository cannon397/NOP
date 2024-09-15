## 프로젝트 설명
선착순 이벤트를 만들고 링크를 통해 공유할 수 있습니다. 
<br>
관리자 페이지에서 이벤트 결과를 확인할 수 있습니다.

### 접속 URL
<a target="_blank">https://nop-svelte.vercel.app/</a>

---
## 기술 스택

#### 백엔드
- JAVA / Spring Boot
- Redis
- Gradle

#### 프론트엔드
- Svelte / SvelteKit
- JavaScript
- TypeScript

#### 인프라
- Nginx
- Docker
- Github Actions
- Oracle Cloud

---

## 프로젝트 아키텍처
### 사용자 요청 흐름도
![사용자 요청 흐름도](https://cacoo.com/diagrams/socHbjCpR0M7t74U-06D3C.jpg)



### CI / CD
![CI / CD](https://cacoo.com/diagrams/socHbjCpR0M7t74U-EF82C.jpg)

---

## API 명세서

- **BASE_URL**: https://nop-cannon.duckdns.org/api/nop/v1
- **Content-Type**: `application/json`
- **Access-Control-Allow-Origin**: https://nop-svelte.vercel.app

#### 공통사항
- `string`타입 필드는 UUID를 제외하고 비어있는 값이거나 문자열의 길이가 50을 초과할 수 없습니다.
-  Path variable : `{eventUrlUUID}` uuid 길이 36

| HTTP Method | Endpoint                     | Description        |
|-------------|------------------------------|--------------------|
| GET         | `/time`                      | 서버 시간 가져오기         | 
| POST        | `/organizer`                 | 이벤트 생성하기           |
| GET         | `/event/{eventUrlUUID}`      | 이벤트 정보 가져오기        |
| POST        | `/event/{eventUrlUUID}/join` | 이벤트 참가하기           |
| POST        | `/login`                     | 이벤트 결과 가져오기 위한 로그인 |
| GET         | `/organizer/{eventUrlUUID}`  | 이벤트 결과 가져오기        |

<details><summary> GET /time</summary>

서버의 시간을 가져온다
#### Request Body
X
#### Response
- **text/plain**: 2024-06-01 20:15:30
-----------------------------------------------------------

</details>

<details><summary> POST /organizer</summary>


이벤트를 생성한다.
#### Request Body

| 필드          | 타입         | 설명                                    | 
|-------------|------------|---------------------------------------|
| `primaryId` | `string`   | 이벤트 참가자 식별 수단, 최대 길이 50자              | 
| `formData`  | `array`    | 이벤트 질문 폼 <br/> - question : `string`  |
| `joinLimit` | `int`      | 이벤트 참가 인원 제한                          |
| `startDate` | `datetime` | `yyyy-MM-dd HH:mm` `2024-09-11 19:27` |                  |

#### Response

| 필드             | 타입       | 설명                  | 
|----------------|----------|---------------------|
| `eventUrlUUID` | `string` | 이벤트 UUID            | 
| `adminKeyUUID` | `string` | 관리자 페이지 접속 KEY UUID |

-----------------------------------------------------------
</details>

<details><summary> GET /event/{eventUrlUUID}</summary>

#### Path variable
`eventUrlUUID`: uuid 길이 36

이벤트 참여를 위한 이벤트 정보를 가져온다.
#### Request Body

X

#### Response
| 필드          | 타입       | 설명                                  |
|-------------|----------|-------------------------------------|
| `primaryId` | `string` | 이벤트 UUID                            |
| `formdata`  | `array`  | 이벤트 질문 폼 <br/> - question: `string` |
| `startDate` | `string` | ISO 8601 `2024-09-11T19:27:00`      |

-----------------------------------------------------------
</details>

<details><summary> POST /event/{eventUrlUUID}/join</summary>

eventUrlUUID를 이용하여 이벤트에 참여한다.
#### Request Body

| 필드             | 타입       | 설명                                    |
|----------------|----------|---------------------------------------|
| `eventUrlUUID` | `string` | 이벤트 UUID                              |
| `primaryId`    | `string` | 이벤트 참가자 고유 ID                         |
| `formData`     | `array`  | 이벤트 참가자 답변 폼 <br/> - answer: `string` |

#### Response
| 필드        | 타입       | 설명                |
|-----------|----------|-------------------|
| `success` | `bool`   | 성공여부              |
| `message` | `string` | `이벤트 참여에 성공했습니다.` |


 -----------------------------------------------------------
</details>
<details><summary> POST /login</summary>

#### Request Body

| 필드             | 타입       | 설명           | 
|----------------|----------|--------------|
| `eventUrlUUID` | `string` | 이벤트 UUID     | 
| `adminKeyUUID` | `string` | 관리자 KEY UUID | 

#### Response

| 필드            | 타입       | 설명                 |
|---------------|----------|--------------------|
| `accessToken` | `string` | 이벤트결과 반환을 위한 인증 토큰 | 

-----------------------------------------------------------
</details>

<details><summary> GET /organizer/{eventUrlUUID}</summary>

eventUrlUUID를 이용하여 이벤트결과를 가져온다.

**Request Header**: Authorization Bearer < JWT >

#### Request Parameter

X

#### Response

| 필드           | 타입      | 설명          |
|--------------|---------|-------------|
| `event`      | `array` | 이벤트 질문 폼    |
| `eventJoins` | `array` | 이벤트 참가 답변 폼 |

</details>

