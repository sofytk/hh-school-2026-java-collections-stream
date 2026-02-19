package tasks;

import common.Person;
import common.PersonService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимптотику работы
 */
public class Task1 {

  private final PersonService personService;

  public Task1(PersonService personService) {
    this.personService = personService;
  }

  public List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = personService.findPersons(personIds);
    Map<Integer, Person> personById = persons.stream().collect(Collectors.toMap(Person::id, Function.identity()));
    List<Person> personsList = new ArrayList<>();
    for (Integer id : personIds) {
      if (personById.get(id) != null) personsList.add(personById.get(id));
    }
    /* если n - размер списка personIds, m - размер сета persons
    Временная сложность: O(n+m), т.к. проход и добавление элементов в map примерно О(m),
    проход по personIds - О(n), поиск в map(personsById) - О(1), т.к. ищем по ключу(нашему id),
    добавление в конец списка personsList - О(1). Получаем, что сложность по времени равна О(n+m).
    Сложность по памяти: создание новых Map - personsById и List - personsList: O(n+m)
    */
    return personsList;
  }
}
