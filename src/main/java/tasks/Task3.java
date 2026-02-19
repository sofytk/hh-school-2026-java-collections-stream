package tasks;

import common.Person;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/*
Задача 3
Отсортировать коллекцию сначала по фамилии, по имени (при равной фамилии), и по дате создания (при равных фамилии и имени)
 */
public class Task3 {

  public static List<Person> sort(Collection<Person> persons) {
    List<Person> sortedPersons = persons.stream().sorted(new Comparator<Person>() {
      @Override
      public int compare(Person o1, Person o2) {
        if (!o1.secondName().equals(o2.secondName())) return o1.secondName().compareTo(o2.secondName());
        else if(!o1.firstName().equals(o2.firstName())) return o1.firstName().compareTo(o2.firstName());
        return o1.createdAt().compareTo(o2.createdAt());
      }
    }).toList();
    return sortedPersons;
  }
}
