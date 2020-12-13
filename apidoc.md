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
| key | type | description |
|---|:---:|:---:|
| user | Long | 유저 정보(PK) |
| token | String | access token |
| expireTime | Long | access token 만료시간 |

---
## 유저 정보
| Method | url |
|---|:---:|
| GET | http://3.16.133.231:8080/api/v1/user/{id:유저번호}

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

---
## 유저 피드 정보
| Method | url |
|---|:---:|
| GET | http://3.16.133.231:8080/api/v1/user/{id:유저번호}/feed/{count:불러올 피드정보 수}

### Header
| key | value |
|---|:---:|
| Authorization | Bearer :access_token |

### Parameter
| key | type | description |
|---|:---:|:---:|
| start | Long | post 번호 |

### Response(200)
| key | type | description |
|---|:---:|:---:|
| ResponseFeedInfo | List[Obejct] | 피드 정보들 |
| FeedItem | Object | 피드 정보 |
| ㄴ post | Long | 게시물 번호 |
| ㄴ thumbnail | String | 게시물 썸네일 url |

---
## 유저 게시물 리스트
| Method | url |
|---|:---:|
| GET | http://3.16.133.231:8080/api/v1/user/{id:유저번호}/posts/{count:불러올 게시물 수}

### Header
| key | value |
|---|:---:|
| Authorization | Bearer :access_token |

### Parameter
| key | type | description |
|---|:---:|:---:|
| start | Long | 게시물 번호 |

### Response(200)
| key | type | description |
|---|:---:|:---:|
| postInfos | List[Object] | 피드 정보들 |
| ResponsePostInfo | Object | 피드 정보 |
| ㄴ post | Long | 게시물 번호 |
| ㄴ poster | Long | 게시자 |
| ㄴ description | String | 설명 |
| ㄴ thumbnail | String | 게시물 썸네일 url |
| ㄴ photos | List[Object] | 사진 정보들 |
| ::: Photo | Object | 사진 정보 |
| ::: ㄴ photo | Long | 사진 번호 |
| ::: ㄴ url | String | 사진 url |

---
## 게시물 업로드
| Method | url |
|---|:---:|
| POST | http://3.16.133.231:8080/api/v1/post

### Header
| key | value |
|---|:---:|
| Authorization | Bearer :access_token |

### Body
| key | type | description |
|---|:---:|:---:|
| photos | List[String] | 업로드할 사진데이터(Base64) |
| description | String | 게시물 설명 |

### Response(200)
| key | type | description |
|---|:---:|:---:|
| postNum | Long | 등록된 게시물 번호 |

---
## 게시물 정보보기
| Method | url |
|---|:---:|
| GET | http://3.16.133.231:8080/api/v1/post/{id:게시물 번호}

### Header
| key | value |
|---|:---:|
| Authorization | Bearer :access_token |

### Response(200)
| key | type | description |
|---|:---:|:---:|
| post | Long | 게시물 번호 |
| poster | Long | 게시자 |
| description | String | 설명 |
| thumbnail | String | 게시물 썸네일 url |
| photos | List[Object] | 사진 정보들 |
| Photo | Object | 사진 정보 |
| ㄴ photo | Long | 사진 번호 |
| ㄴ url | String | 사진 url |

---
## 타임라인 보기
| Method | url |
|---|:---:|
| GET | http://3.16.133.231:8080/api/v1/timeline/{count}/{start}

### Header
| key | value |
|---|:---:|
| Authorization | Bearer :access_token |

### Parameter
| key | type | description |
|---|:---:|:---:|
| type | int | 현재 타임라인(0), 최신 타임라인(1), 과거 타임라인(2) |

### Response(200)
| key | type | description |
|---|:---:|:---:|
| timeline | List[Object] | 타임라인 정보들 |
| TimelinePostInfo | Object | 타임라인 정보 |
| ㄴ user | Long | 유저 번호 |
| ㄴ username | String | 유저 닉네임 |
| ㄴ post | Long | 게시물 번호 |
| ㄴ createTime | Long | 게시물 등록된 시간 |
| ㄴ description | String| 게시물 설명 |
| ㄴ photos | List[Object] | 사진 정보들 |
| ::: Photo | Object | 사진 정보 |
| ::: ㄴ photo | Long | 사진 번호 |
| ::: ㄴ url | String | 사진 url |

---
## 게시물 삭제
| Method | url |
|---|:---:|
| DELETE | http://3.16.133.231:8080/api/v1/post/{id:삭제할 게시물 번호}

### Header
| key | value |
|---|:---:|
| Authorization | Bearer :access_token |

### Response(200)

---
## 게시물 수정
| Method | url |
|---|:---:|
| PUT | http://3.16.133.231:8080/api/v1/post/{id:수정할 게시물 번호}

### Header
| key | value |
|---|:---:|
| Authorization | Bearer :access_token |

### Body
| key | type | description |
|---|:---:|:---:|
| description | String | 변경할 게시물 설명 |

### Response(200)

---
## 팔로우 하기
| Method | url |
|---|:---:|
| POST | http://3.16.133.231:8080/api/v1/follow/{id:유저 번호}

### Header
| key | value |
|---|:---:|
| Authorization | Bearer :access_token |

### Response(200)

---
## 팔로우 취소하기
| Method | url |
|---|:---:|
| DELETE | http://3.16.133.231:8080/api/v1/follow/{id:유저 번호}

### Header
| key | value |
|---|:---:|
| Authorization | Bearer :access_token |

### Response(200)

