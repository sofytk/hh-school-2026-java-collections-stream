package tasks;

import common.Company;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/*
Из коллекции компаний необходимо получить всевозможные различные названия вакансий
 */
public class Task7 {

  public static Set<String> vacancyNames(Collection<Company> companies) {

    Set<String> vacanciesName = new HashSet<>();
    companies.stream().map(company ->
        vacanciesName.addAll(company.getVacancies().stream().map(vacancy -> vacancy.getTitle())
            .collect(Collectors.toSet())))
        .collect(Collectors.toSet());

    return vacanciesName;
  }
}
