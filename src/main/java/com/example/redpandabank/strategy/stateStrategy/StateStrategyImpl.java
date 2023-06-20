package com.example.redpandabank.strategy.stateStrategy;

import com.example.redpandabank.enums.StateCommands;
import com.example.redpandabank.keyboard.main.ReplyMainMenuButton;
import com.example.redpandabank.keyboard.schedule.*;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonScheduleService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSender;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.strategy.stateStrategy.states.*;
import com.example.redpandabank.util.Separator;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@FieldDefaults(level = AccessLevel.PRIVATE)

@Component
public class StateStrategyImpl implements StateStrategy {
    Map<String, StateHandler> stateStrategyMap;
    final ChildService childService;
    final LessonService lessonService;
    final InlineScheduleCheckCorrectTitleButton checkCorrectTitle;
    final InlineScheduleRepeatAddLessonButton repeatAddLesson;
    final InlineScheduleAddTeacherNameButton inlineScheduleAddTeacherNameButton;
    final InlineScheduleAddEventDurationButton inlineScheduleAddEventDurationButton;
    final LessonScheduleService lessonScheduleService;
    final InlineScheduleAddEventTimeAndDayButton inlineScheduleAddEventTimeAndDayButton;
    final ReplyMainMenuButton mainMenuButton;
    final InlineScheduleAddSpecificEventStartTimeButton startTimeButton;
    final ReplyScheduleEditSpecificEventDurationStateButton eventDurationStateButton;
    final TranslateService translateService;
    final MessageSender messageSender;
    final InlineScheduleEditEventDayStep2 editEventDayStep2;
    final InlineScheduleAddExtraDaySpecificStartTimeButton
            inlineScheduleAddExtraDaySpecificStartTimeButton;
    final InlineScheduleAddAgainEventTimeAndDayButton
            inlineScheduleAddAgainEventTimeAndDayButton;

    public StateStrategyImpl(ChildService childService, LessonService lessonService,
                             InlineScheduleCheckCorrectTitleButton checkCorrectTitle,
                             InlineScheduleRepeatAddLessonButton repeatAddLesson,
                             InlineScheduleAddTeacherNameButton inlineScheduleAddTeacherNameButton,
                             InlineScheduleAddEventDurationButton inlineScheduleAddEventDurationButton,
                             LessonScheduleService lessonScheduleService,
                             InlineScheduleAddEventTimeAndDayButton inlineScheduleAddEventTimeAndDayButton,
                             ReplyMainMenuButton mainMenuButton,
                             InlineScheduleAddSpecificEventStartTimeButton startTimeButton,
                             ReplyScheduleEditSpecificEventDurationStateButton eventDurationStateButton,
                             TranslateService translateService,
                             MessageSender messageSender,
                             InlineScheduleEditEventDayStep2 editEventDayStep2,
                             InlineScheduleAddExtraDaySpecificStartTimeButton
                                     inlineScheduleAddExtraDaySpecificStartTimeButton, InlineScheduleAddAgainEventTimeAndDayButton inlineScheduleAddAgainEventTimeAndDayButton) {
        this.childService = childService;
        this.lessonService = lessonService;
        this.checkCorrectTitle = checkCorrectTitle;
        this.repeatAddLesson = repeatAddLesson;
        this.inlineScheduleAddTeacherNameButton = inlineScheduleAddTeacherNameButton;
        this.inlineScheduleAddEventDurationButton = inlineScheduleAddEventDurationButton;
        this.lessonScheduleService = lessonScheduleService;
        this.inlineScheduleAddEventTimeAndDayButton = inlineScheduleAddEventTimeAndDayButton;
        this.mainMenuButton = mainMenuButton;
        this.startTimeButton = startTimeButton;
        this.eventDurationStateButton = eventDurationStateButton;
        this.translateService = translateService;
        this.messageSender = messageSender;
        this.editEventDayStep2 = editEventDayStep2;
        this.inlineScheduleAddExtraDaySpecificStartTimeButton = inlineScheduleAddExtraDaySpecificStartTimeButton;
        this.inlineScheduleAddAgainEventTimeAndDayButton = inlineScheduleAddAgainEventTimeAndDayButton;

        stateStrategyMap = new HashMap<>();
        stateStrategyMap.put(StateCommands.SAVE_TITLE_EVENT.getState(),
                new SaveTitleEventState(childService, lessonService,
                        this.messageSender, checkCorrectTitle, repeatAddLesson, translateService));
        stateStrategyMap.put(StateCommands.SAVE_EVENT_TEACHER_NAME.getState(),
                new SaveTeacherNameState(childService, lessonService,
                        inlineScheduleAddTeacherNameButton, translateService, messageSender));
        stateStrategyMap.put(StateCommands.SAVE_EVENT_DURATION.getState(),
                new SaveDurationEventState(childService, lessonService,
                        inlineScheduleAddEventDurationButton, translateService, messageSender));
               stateStrategyMap.put(StateCommands.ADD_SPECIFIC_EVENT_START_TIME.getState(),
                new SaveEventTimeState(childService, lessonService,
                        lessonScheduleService, inlineScheduleAddEventTimeAndDayButton, translateService, messageSender));
        stateStrategyMap.put(StateCommands.EDIT_SPECIFIC_EVENT_FIELD.getState(),
                new EditSpecificEventFieldState(childService, lessonService,
                        mainMenuButton, translateService, messageSender));
        stateStrategyMap.put(StateCommands.EDIT_SPECIFIC_EVENT_TEACHER_NAME.getState(),
                new EditSpecificEventTeacherNameState(childService,
                        lessonService, mainMenuButton, translateService, messageSender));
        stateStrategyMap.put(StateCommands.EDIT_SPECIFIC_EVENT_START_TIME.getState(),
                new EditSpecificEventStartTimeState(childService, lessonService,
                        lessonScheduleService, translateService, messageSender));
        stateStrategyMap.put(StateCommands.EDIT_SPECIFIC_EVENT_START_TIME_STEP2.getState(),
                new EditSpecificEventStartTimeStep2State(childService, lessonService,
                        lessonScheduleService, editEventDayStep2, translateService, messageSender));
        stateStrategyMap.put(StateCommands.ADD_SPECIFIC_EVENT_START_TIME.getState(),
                new AddSpecificEventStartTimeState(childService, lessonService,
                        lessonScheduleService, translateService, messageSender,
                        inlineScheduleAddAgainEventTimeAndDayButton));
        stateStrategyMap.put(StateCommands.EDIT_SPECIFIC_SCHEDULE_EVENT_DURATION.getState(),
                new EditSpecificScheduleEventDurationState(childService, lessonService,
                        eventDurationStateButton, translateService, messageSender));
    }

    @Override
    public StateHandler get(Child child) {
        String state = child.getState();
        state = checkEditSpecificEventFieldCommand(state);
        state = checkAddSpecificEventStartTimeState(state);
        return stateStrategyMap.get(state);
    }

    private String checkEditSpecificEventFieldCommand(String state) {
        return state.split(Separator.COLON_SEPARATOR)[0];
    }

    private String checkAddSpecificEventStartTimeState(String state) {
        return state.split(Separator.COLON_SEPARATOR)[0];
    }

}
