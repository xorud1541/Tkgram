# Tkgram API 문서

---
## 회원가입
| Method | url |
|---|:---:|
| POST | http://3.16.133.231:8080/api/join

### Header
| key | value |
|---|:---:|
| Content-Type | application/json |

### Body
| key | type | description |
|---|:---:|:---:|
| email | String | 이메일 |
| password | String | 비밀번호 |
| username | String | 유저 닉네임 |

### Response(200)

---
## 로그인
| Method | url |
|---|:---:|
| POST | http://3.16.133.231:8080/api/login

### Header
| key | value |
|---|:---:|
| Content-Type | application/json |

### Body
| key | type | description |
|---|:---:|:---:|
| email | String | 이메일 |
| password | String | 비밀번호 |
| secretKey | String | secret key |

### Response(200)
| key | type |
|---|:---:|
| user | Long |
| token | String |
| expireTime | Long |

---
## 유저 정보
| Method | url |
|---|:---:|
| GET | http://3.16.133.231:8080/api/v1/user/{id}

### Header
| key | value |
|---|:---:|
| Authorization | Bearer :access_token |

### Response(200)
| key | type | description |
|---|:---:|:---:|
| user | type | 유저 번호(PK) |
| email | String | 이메일 |
| username | String | 유저 닉네임 |
| profile | String | 유저 프로필 url |
| followersCnt | int | follower 수 |
| followeesCnt | int | followee 수 |
| relation | int | 본인(0), 팔로잉(1), 언팔로잉(2) |
