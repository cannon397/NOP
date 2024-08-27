package com.cannon.nop;

import com.cannon.nop.domain.event.model.Event;
import com.cannon.nop.domain.event.model.Event.EventBuilder;
import com.cannon.nop.domain.event.model.EventQuestionForm;
import com.cannon.nop.domain.event.model.EventQuestionForm.EventQuestionFormBuilder;
import com.cannon.nop.domain.event.model.EventResult;
import com.cannon.nop.domain.event.model.EventResult.EventResultBuilder;
import com.cannon.nop.domain.eventauth.model.EventAuth;
import com.cannon.nop.domain.eventauth.model.EventAuth.EventAuthBuilder;
import com.cannon.nop.domain.eventjoin.model.EventAnswerForm;
import com.cannon.nop.domain.eventjoin.model.EventAnswerForm.EventAnswerFormBuilder;
import com.cannon.nop.domain.eventjoin.model.EventJoin;
import com.cannon.nop.domain.eventjoin.model.EventJoin.EventJoinBuilder;
import com.cannon.nop.domain.eventjoin.model.EventJoinId;
import com.cannon.nop.domain.eventjoin.model.EventJoinId.EventJoinIdBuilder;

import java.time.LocalDateTime;
import java.util.Arrays;


public class Fixtures {
    public static EventBuilder aEvent(){
        return Event.builder()
                .eventUrlUUID("test_eventUrlUUID")
                .primaryId("질문지_기본키")
                .startDate(LocalDateTime.now())
                .joinLimit(20)
                .eventQuestionForms(Arrays.asList(
                        aEventQuestionForm().build()
                ));
    }
    public static EventAuthBuilder aEventAccessManagement(){
        return EventAuth.builder()
                .adminUrlUUID("test_adminUrlUUID")
                .adminKeyUUID("test_adminKeyUUID")
                .eventUrlUUID(aEvent().build().getEventUrlUUID());
    }
    public static EventQuestionFormBuilder aEventQuestionForm(){
        return EventQuestionForm.builder()
                .question("질문지1");
    }
    public static EventResultBuilder aEventResult(){
        return EventResult.builder()
                .eventJoins(Arrays.asList(aEventJoin().build()))
                .event(aEvent().build());
    }




    public static EventAnswerFormBuilder aEventAnswerForm(){
        return EventAnswerForm.builder()
                .answer("질문지에 대한 답변1");
    }
    public static EventJoinBuilder aEventJoin(){
        return EventJoin.builder()
                .eventJoinId(aEventJoinId().build())
                .formData(Arrays.asList(
                        aEventAnswerForm().build()
                ));
    }
    public static EventJoinIdBuilder aEventJoinId(){
        return EventJoinId.builder()
                .eventUrlUUID(aEvent().build().getEventUrlUUID())
                .primaryId("cannon397")
                .joinId(1L);
    }
//    public static List<EventQuestionForm> aEventQuestionForms(){
//        return new ArrayList<EventQuestionForm>().add(aEventQuestionForm());
//    }
//    eventAccessManagement = new EventAccessManagement();
//    List<EventQuestionForm> eventQuestionForms = new ArrayList<>();
//        eventQuestionForms.add(new EventQuestionForm());
}
