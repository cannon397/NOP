package com.cannon.nop.interfaces.event.mapstruct;

import com.cannon.nop.domain.event.model.EventQuestionForm;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EventFormMapper {

    @Named("stringToForm")
    default EventQuestionForm stringToForm(String data) {
        return new EventQuestionForm(data);
    }

    @Named("stringsToForms")
    default List<EventQuestionForm> stringsToForms(List<String> data) {
        return Optional.ofNullable(data).orElseGet(Collections::emptyList)
                .stream()
                .map(this::stringToForm)
                .collect(Collectors.toList());
    }

    @Named("formToString")
    default String formToString(EventQuestionForm form) {
        return form.getQuestionInput();
    }

    @Named("formsToStrings")
    default List<String> formsToStrings(List<EventQuestionForm> forms) {
        return forms.stream().map(this::formToString).collect(Collectors.toList());
    }
}
