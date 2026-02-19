package tasks;

import common.ApiPersonDto;
import common.Area;
import common.Person;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/*
Имеются
- коллекция персон Collection<Person>
- словарь Map<Integer, Set<Integer>>, сопоставляющий каждой персоне множество id регионов
- коллекция всех регионов Collection<Area>
На выходе хочется получить множество строк вида "Имя - регион". Если у персон регионов несколько, таких строк так же будет несколько
 */
public class Task6 {

  public static Set<String> getPersonDescriptions(Collection<Person> persons,
                                                  Map<Integer, Set<Integer>> personAreaIds,
                                                  Collection<Area> areas) {
    Set<String> setOfPersonsDescriptions = new HashSet<>();
    Map<Integer, String> personNameById = persons.stream().collect(Collectors.toMap(Person::id, Person::firstName));
    Map<Integer, String> areaNameById = areas.stream().collect(Collectors.toMap(Area::getId, Area::getName));
    for (Map.Entry<Integer, Set<Integer>> entry : personAreaIds.entrySet()) {
      setOfPersonsDescriptions.addAll(entry.getValue().stream()
          .map(areaId ->
            personNameById.get(entry.getKey()) + " - " + areaNameById.get(areaId)
          )
          .collect(Collectors.toSet()));
    }
    return setOfPersonsDescriptions;
  }
}
