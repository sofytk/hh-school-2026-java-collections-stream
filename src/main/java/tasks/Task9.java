package tasks;

import common.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Далее вы увидите код, который специально написан максимально плохо.
Постарайтесь без ругани привести его в надлежащий вид
P.S. Код в целом рабочий (не везде), комментарии оставлены чтобы вам проще понять чего же хотел автор
P.P.S Здесь ваши правки необходимо прокомментировать (можно в коде, можно в PR на Github)
 */
public class Task9 {

  //private long count;

  // Костыль, эластик всегда выдает в топе "фальшивую персону".
  // Конвертируем начиная со второй
  /* ИЗМЕНЕНИЯ: убираем persons.remove(0), т.к. это не выгодно по времени, потому что удаляя первый элемент,
     список перезаписывается полность, сдвигаясь на один элемент влево, т.е. получаем сложность О(n).
     Поэтому вызовем у stream метод skip(), который просто пропустит первого человека.
  */
  public List<String> getNames(List<Person> persons) {
    if (persons.size() == 0) {
      return Collections.emptyList();
    }
    return persons.stream().skip(1).map(Person::firstName).collect(Collectors.toList());
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  // ИЗМЕНЕНИЯ: убираем вызов distinct(), т.к. элементы и так не повторяются, когда мы собираем их в Set.

  public Set<String> getDifferentNames(List<Person> persons) {
    return getNames(persons).stream().collect(Collectors.toSet());
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  /* ИЗМЕНЕНИЯ: два раза проверяем person.secondName() != null,
   при этом middleName не включенно в результат, сделаем добавление в результат в формате:
   secondName(фамилия) + firstName(имя) + middleName (отчество), т.е. изменим последний if
   */
  public String convertPersonToString(Person person) {
    String result = "";
    if (person.secondName() != null) {
      result += person.secondName();
    }

    if (person.firstName() != null) {
      result += " " + person.firstName();
    }

    if (person.middleName() != null) {
      result += " " + person.middleName();
    }
    return result;
  }

  // словарь id персоны -> ее имя
  /* ИЗМЕНЕНИЯ: переименовала переменную map в personNamesById(может быть и не обязательно, но понятнее),
   убираем initialCapacity = 1 из параметра new HashMap<>(), т.к. это фиксирует ёмкость,
   что может привести к потерям производительности, потому что по сути всё будет записываться в один bucket и
   время поиска и получения элементов при большом количестве пользователей будет как в обычном списке, а при попытке
   расширения, элементы будут перезаписываться.
   При проверке содержится ли уже ключ, мы по сути обращаемся к мапе два раза, можно заменить на putIfAbsent
   */
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    Map<Integer, String> personNamesById = new HashMap<>();
    for (Person person : persons) {
      personNamesById.putIfAbsent(person.id(), convertPersonToString(person));
    }
    return personNamesById;
  }

  // есть ли совпадающие в двух коллекциях персоны?
  /* ИЗМЕНЕНИЯ: убираем двойной цикл, невыгодно по производительности, сложность в худшем случае О(n*m),
  если n и m это размеры коллекций соответсвенно, можно было улучшить, добавив return true, как только мы находим
  совпадения, но если совпадения находятся ближе к концу коллекции, выходит весьма затратно. Нашла метод retainAll,
  он удаляет элементы из одной коллекции, если они не находятся во второй(противоположность removeAll), а т.к. у HashSet
  удаление и contains по сложности O(1)(операции у retainAll под капотом), то выходит быстрее. Получаем, что в
  personSet1, остаются только повторяющиеся элементы, проверяем пустой ли список.
  */
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    Set<Person> personSet1 = new HashSet<>(persons1);
    Set<Person> personSet2 = new HashSet<>(persons2);
    personSet1.retainAll(personSet2);
    return !personSet1.isEmpty();
  }

  // Посчитать число четных чисел
  /* ИЗМЕНЕНИЯ: можно сразу посчитать .count() без forEach().
  Судя по тому, что count приватная и не используется в других методах, её можно было бы убрать
  */
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num % 2 == 0).count();
  }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  /* ОБЪЯСНЕНИЕ: для Integer hashCode() возращает само число. HashSet ищет элементы по хешу, возможно это как то связано
   с этим, но судя по тестам, если мы зададим не интервал чисел, а произвольные, то работать не будет. */
  void listVsSet() {
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString());
  }
}
