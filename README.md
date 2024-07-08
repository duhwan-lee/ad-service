# ad-service

- docker 디렉토리 하위에 docker-compose.yml 파일을 이용하여 DB 실행 가능
- swagger : http://localhost:8080/webjars/swagger-ui/index.html
```
Sample 

curl -X 'POST' \
  'http://localhost:8080/api/v1/ads' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "adTitle": "string1",
  "adReward": 10,
  "adMaxParticipation": 2,
  "adImageUrl": "string",
  "adStartDateTime": "2024-07-08 13:07:29",
  "adEndDateTime": "2024-07-18 13:07:29",
  "adDescription": "string",
  "adParticipationConditions": [
    {
      "participationType": "FIRST",
      "value": "string"
    }
  ]
}'

curl -X 'GET' \
  'http://localhost:8080/api/v1/ads/1' \
  -H 'accept: */*'
  
curl -X 'GET' \
  'http://localhost:8080/api/v1/ads/1/histories?page=0' \
  -H 'accept: */*'

curl -X 'POST' \
  'http://localhost:8080/api/v1/ads/1/3/participate' \
  -H 'accept: */*' \
  -d ''  
```
- kotlin, spring boot, r2dbc, webflux, hexagonal(port & adapter) 기반의 광고 서비스 구현
  - 가장 최근에 주요하게 작업한 프로젝트와 비슷한 스펙으로 구현.
  - 초기에 저장소등의 스펙을 명확하게 정하지 못해 유연하게 변경 가능하도록 구현.
  - 모듈별 테스트 코드 작성. (미비함)
  

- 문제 해결
  - 동적으로 추가될 수 있는 룰에 대해서는 코드에서 구현과, 저장되는 패턴을 정하여 할 수 있도록하여, 구현이 완료된 이후에 DB등의 저장소에 저장되도록 함.
  - enum에서 구현하여 단일 클래스에서 유사한 패턴으로 각 룰에 맞춰 구현하도록 함.
  - 광고 등록 시 update 쿼리에 조건을 주어 크게 별도의 로직을 추가하지 않고, 동시성을 처리
  - 광고 등록 시 트랜잭션은 최소한으로 하여, 최초 등록 이후 포인트를 적립하고, 과정에서 문제가 별생할 경우, 트랜잭션에 의한 롤백이 아닌 적립횟수 반환을 구현