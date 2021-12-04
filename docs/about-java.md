
## Java 

###java 15

#### ```record``` 클래스
* kotlin의 ``data class`` 처럼 getter/setter 생성자를 내장한 일종의 Dto 클래스
* record 클래스 이전의 클래스 정의
```java
public class Person {
    private final String name;
    private final int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
```

* record 클래스 활용
```java
public record Person(String name, int age) { }
```

#### sealed class
* kotlin과 마찬가지로 자식 클래스의 종류를 제한하는 클래스를 정의하기 위해 사용되는 키워드로 보임.

```java
public abstract sealed class Person
    permits Employee, Manager {
 
    //...
}
```

* https://www.baeldung.com/java-15-new
* https://luvstudy.tistory.com/125

###java 17

#### switch문의 pattern-matching(JEP-406)

* scala와 비슷한 문법 표현식인듯.

```java
static record Human (String name, int age, String profession) {}

public String checkObject(Object obj) {
    return switch (obj) {
        case Human h -> "Name: %s, age: %s and profession: %s".formatted(h.name(), h.age(), h.profession());
        case Circle c -> "This is a circle";
        case Shape s -> "It is just a shape";
        case null -> "It is null";
        default -> "It is an object";
    };
}

public String checkShape(Shape shape) {
    return switch (shape) {
        case Triangle t && (t.getNumberOfSides() != 3) -> "This is a weird triangle";
        case Circle c && (c.getNumberOfSides() != 0) -> "This is a weird circle";
        default -> "Just a normal shape";
    };
}
```

- https://mkyong.com/java/what-is-new-in-java-17/
- https://www.baeldung.com/java-17-new-features
