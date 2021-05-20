# Java8
## 6부 Date와 Time API
### 13. Date와 Time API 소개

  - 지금 이 순간을 기계 시간으로 표현하는 방법
    - Instant.now(): 현재 UTC(GMT) 를 리턴한다.
    - Universal Time Coordinated == Greenwich Mean TIme
    ```java
      Instant now = Instant.now();
      System.out.println(now);
      System.out.println(now.atZone(ZoneId.of("UTC")));

      ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
      System.out.println(zonedDateTime);
    ```

  - 인류용 일시를 표현하는 방법
    - LocalDateTime.now(): 현재 시스템 Zone에 해당하는(로컬) 일시를 리턴한다.
    - LocalDateTIme.of(int, Month, int, int, int, int): 로컬의 특정 일시를 리턴한다.
    - ZonedDateTime.of(int, Month, int, int, int, int, ZoneId): 특정 Zone의 특정 일시를 리턴한다.
  - 기간을 표현하는 방법
    - Period / Duration.between()

    ```java
      Period between = Period.between(today, birthDay);
       System.out.println(between.get(ChronoUnit.DAYS));
    ```
  - 파싱 및 포맷팅
    - 미리 정의해둔 포맷 참조
      - https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#predefined
    - LocalDateTime.parse(String, DateTimeFormatter);
    - DateTime
    ```java
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("MM/d/yyyy");
        LocalDate date = LocalDate.parse("07/15/1982", formatter);
        System.out.println(date);
        System.out.println(today.format(formatter));
    ```
  - 레거시 API 지원
    - GregorianCalendar와 Date 타입의 인스턴스를 Instant나 ZonedDateTime으로 변환 가능.
    - java.util.TimeZone에서 java.time.ZoneId로 상호 변환 가능.
    ```java
      ZoneId newZoneAPI = TimeZone.getTimeZone("PST").toZoneId();
      TimeZone legacyZoneAPI = TimeZone.getTimeZone(newZoneAPI);
      Instant newInstant = new Date().toInstant();
       Date legacyInstant = Date.from(newInstant);
    ```

    





    
