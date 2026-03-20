package tasks;

import common.Person;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class Task9Test {

  private static Person person1, person2, person3, person4;
  private static List<Person> persons = new ArrayList<>();

  @InjectMocks
  private Task9 task9;

  @BeforeAll
  public static void before() {
    Instant time = Instant.now();
    person1 = new Person(1, "Person 1", null, null, time);
    person2 = new Person(2, "Person 2", null, null, time.plusSeconds(1));
    person3 = new Person(3, "Person 3", null, null, time.minusSeconds(1));
    person4 = new Person(4, "Person 4", null, null, time.plusSeconds(2));
    persons = List.of(person1, person2, person3, person4);
  }

  @Test
  public void testGetNameMethod() {
    List<Person> testList = List.of(person2, person3, person4);
    assertEquals(testList.stream().map(Person::firstName).collect(Collectors.toList()),
        task9.getNames(persons));
  }

  @Test
  public void testConvertPersonToString() {
    Person testPerson = new Person(5, "Ivan", "Ivanov", "Ivanovish", Instant.now());
    Person testPerson1 = new Person(5, null, "Ivanov", "Ivanovish", Instant.now());
    assertEquals("Ivanov Ivan Ivanovish", task9.convertPersonToString(testPerson));
    assertEquals("Ivanov Ivanovish", task9.convertPersonToString(testPerson1));
  }

  @Test
  public void testFindSamePersonsInCollections() {

    Person person5 = new Person(5, "Person 6", null, null, Instant.now());
    Person person6 = new Person(6, "Person 6", null, null, Instant.now().plusSeconds(4));
    List<Person> collectionWithoutSamePersons = List.of(person5, person6);
    List<Person> collectionWithSamePersons = List.of(person1, person3, person5);

    assertEquals(false, task9.hasSamePersons(persons, collectionWithoutSamePersons));
    assertEquals(true, task9.hasSamePersons(persons, collectionWithSamePersons));
  }

  @Test
  public void testContEven() {
    List<Integer> nums = List.of(1, 2, 3, 4, 5, 6);
    assertEquals(3, task9.countEven(nums.stream()));
  }

  @Test
  public void testlistVsSet() {
    task9.listVsSet();
  }

}
