package com.example.redpandabank.strategy.stateStrategy;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.keyboard.main.ReplyMainMenuButton;
import com.example.redpandabank.keyboard.schedule.*;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonScheduleService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.strategy.stateStrategy.states.*;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@PackagePrivate
@Component
public class StateStrategyImpl implements StateStrategy {
    Map<String, StateHandler> stateStrategyMap;
    final ChildService childService;
    final LessonService lessonService;
    final InlineCheckCorrectTitle checkCorrectTitle;
    final InlineRepeatAddLesson repeatAddLesson;
    final InlineAddTeacherName inlineAddTeacherName;
    final InlineAddEventDuration inlineAddEventDuration;
    final LessonScheduleService lessonScheduleService;
    final InlineAddExtraDay inlineAddExtraDay;
    final InlineAddEventTime inlineAddEventTime;
    final ReplyMainMenuButton mainMenuButton;
    final InlineAddSpecificEventStartTimeButton startTimeButton;
    final ButtonEditSpecificScheduleEventDurationState eventDurationStateButton;

    public StateStrategyImpl(ChildService childService, LessonService lessonService,
                             InlineCheckCorrectTitle checkCorrectTitle,
                             InlineRepeatAddLesson repeatAddLesson,
                             InlineAddTeacherName inlineAddTeacherName,
                             InlineAddEventDuration inlineAddEventDuration,
                             LessonScheduleService lessonScheduleService,
                             InlineAddExtraDay inlineAddExtraDay, InlineAddEventTime inlineAddEventTime,
                             ReplyMainMenuButton mainMenuButton,
                             InlineAddSpecificEventStartTimeButton startTimeButton,
                             ButtonEditSpecificScheduleEventDurationState eventDurationStateButton) {
        this.childService = childService;
        this.lessonService = lessonService;
        this.checkCorrectTitle = checkCorrectTitle;
        this.repeatAddLesson = repeatAddLesson;
        this.inlineAddTeacherName = inlineAddTeacherName;
        this.inlineAddEventDuration = inlineAddEventDuration;
        this.lessonScheduleService = lessonScheduleService;
        this.inlineAddExtraDay = inlineAddExtraDay;
        this.inlineAddEventTime = inlineAddEventTime;
        this.mainMenuButton = mainMenuButton;
        this.startTimeButton = startTimeButton;
        this.eventDurationStateButton = eventDurationStateButton;
        stateStrategyMap = new HashMap<>();
        stateStrategyMap.put(State.SAVE_TITLE_EVENT.getState(), new SaveTitleEventState(childService, lessonService, checkCorrectTitle, repeatAddLesson));
        stateStrategyMap.put(State.SAVE_TEACHER_NAME.getState(), new SaveTeacherNameState(childService, lessonService, this.inlineAddTeacherName));
        stateStrategyMap.put(State.SAVE_DURATION.getState(), new SaveDurationEventState(childService, lessonService, inlineAddEventDuration));
        stateStrategyMap.put(State.SAVE_EVENT_DAY.getState(), new SaveEventDayState(childService, lessonService, this.lessonScheduleService, this.inlineAddExtraDay));
        stateStrategyMap.put(State.ADD_EVENT_TIME.getState(), new SaveEventTimeState(childService, lessonService, lessonScheduleService, inlineAddEventTime));
        stateStrategyMap.put(State.EDIT_SPECIFIC_EVENT_FIELD.getState(), new EditSpecificEventFieldState(childService, lessonService, this.mainMenuButton));
        stateStrategyMap.put(State.EDIT_SPECIFIC_EVENT_TEACHER_NAME.getState(), new EditSpecificEventTeacherNameState(childService, lessonService, mainMenuButton));
        stateStrategyMap.put(State.EDIT_SPECIFIC_EVENT_START_TIME.getState(), new EditSpecificEventStartTimeState(childService, lessonService, lessonScheduleService));
        stateStrategyMap.put(State.EDIT_SPECIFIC_EVENT_START_TIME_STEP2.getState(), new EditSpecificEventStartTimeStep2State(childService, lessonService, lessonScheduleService, mainMenuButton));
        stateStrategyMap.put(State.ADD_SPECIFIC_EVENT_START_TIME.getState(), new AddSpecificEventStartTimeState(childService, lessonService, lessonScheduleService, this.startTimeButton));
        stateStrategyMap.put(State.EDIT_SPECIFIC_SCHEDULE_EVENT_DURATION.getState(), new EditSpecificScheduleEventDurationState(childService, lessonService, this.eventDurationStateButton));
    }

    @Override
    public StateHandler get(Child child) {
        String state = child.getState();
        state = checkEditSpecificEventFieldCommand(state);
        state = checkAddSpecificEventStartTimeState(state);
        StateHandler stateHandler = stateStrategyMap.get(state);
        return stateHandler;
    }

    private String checkEditSpecificEventFieldCommand(String state) {
        return state.split(LessonService.COLON_SEPARATOR)[0];
    }

    private String checkAddSpecificEventStartTimeState(String state) {
        return state.split(LessonService.COLON_SEPARATOR)[0];
    }

}
