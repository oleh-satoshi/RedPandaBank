package com.example.redpandabank.strategy.stateStrategy;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.keyboard.main.ReplyMainMenuButton;
import com.example.redpandabank.keyboard.schedule.InlineScheduleAddEventDurationButton;
import com.example.redpandabank.keyboard.schedule.InlineScheduleAddEventTimeButton;
import com.example.redpandabank.keyboard.schedule.InlineScheduleAddExtraDayButton;
import com.example.redpandabank.keyboard.schedule.InlineScheduleAddSpecificEventStartTimeButton;
import com.example.redpandabank.keyboard.schedule.InlineScheduleAddTeacherNameButton;
import com.example.redpandabank.keyboard.schedule.InlineScheduleCheckCorrectTitleButton;
import com.example.redpandabank.keyboard.schedule.InlineScheduleRepeatAddLessonButton;
import com.example.redpandabank.keyboard.schedule.ReplyScheduleEditSpecificEventDurationStateButton;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonScheduleService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.strategy.stateStrategy.states.AddSpecificEventStartTimeState;
import com.example.redpandabank.strategy.stateStrategy.states.EditSpecificEventFieldState;
import com.example.redpandabank.strategy.stateStrategy.states.EditSpecificEventStartTimeState;
import com.example.redpandabank.strategy.stateStrategy.states.EditSpecificEventStartTimeStep2State;
import com.example.redpandabank.strategy.stateStrategy.states.EditSpecificEventTeacherNameState;
import com.example.redpandabank.strategy.stateStrategy.states.EditSpecificScheduleEventDurationState;
import com.example.redpandabank.strategy.stateStrategy.states.SaveDurationEventState;
import com.example.redpandabank.strategy.stateStrategy.states.SaveEventDayState;
import com.example.redpandabank.strategy.stateStrategy.states.SaveEventTimeState;
import com.example.redpandabank.strategy.stateStrategy.states.SaveTeacherNameState;
import com.example.redpandabank.strategy.stateStrategy.states.SaveTitleEventState;
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
    final InlineScheduleAddExtraDayButton inlineScheduleAddExtraDayButton;
    final InlineScheduleAddEventTimeButton inlineScheduleAddEventTimeButton;
    final ReplyMainMenuButton mainMenuButton;
    final InlineScheduleAddSpecificEventStartTimeButton startTimeButton;
    final ReplyScheduleEditSpecificEventDurationStateButton eventDurationStateButton;
    final TranslateService translateService;

    public StateStrategyImpl(ChildService childService, LessonService lessonService,
                             InlineScheduleCheckCorrectTitleButton checkCorrectTitle,
                             InlineScheduleRepeatAddLessonButton repeatAddLesson,
                             InlineScheduleAddTeacherNameButton inlineScheduleAddTeacherNameButton,
                             InlineScheduleAddEventDurationButton inlineScheduleAddEventDurationButton,
                             LessonScheduleService lessonScheduleService,
                             InlineScheduleAddExtraDayButton inlineScheduleAddExtraDayButton,
                             InlineScheduleAddEventTimeButton inlineScheduleAddEventTimeButton,
                             ReplyMainMenuButton mainMenuButton,
                             InlineScheduleAddSpecificEventStartTimeButton startTimeButton,
                             ReplyScheduleEditSpecificEventDurationStateButton eventDurationStateButton,
                             TranslateService translateService) {
        this.childService = childService;
        this.lessonService = lessonService;
        this.checkCorrectTitle = checkCorrectTitle;
        this.repeatAddLesson = repeatAddLesson;
        this.inlineScheduleAddTeacherNameButton = inlineScheduleAddTeacherNameButton;
        this.inlineScheduleAddEventDurationButton = inlineScheduleAddEventDurationButton;
        this.lessonScheduleService = lessonScheduleService;
        this.inlineScheduleAddExtraDayButton = inlineScheduleAddExtraDayButton;
        this.inlineScheduleAddEventTimeButton = inlineScheduleAddEventTimeButton;
        this.mainMenuButton = mainMenuButton;
        this.startTimeButton = startTimeButton;
        this.eventDurationStateButton = eventDurationStateButton;
        this.translateService = translateService;
        stateStrategyMap = new HashMap<>();
        stateStrategyMap.put(State.SAVE_TITLE_EVENT.getState(),
                new SaveTitleEventState(childService, lessonService,
                        checkCorrectTitle, repeatAddLesson, translateService));
        stateStrategyMap.put(State.SAVE_TEACHER_NAME.getState(),
                new SaveTeacherNameState(childService, lessonService,
                        inlineScheduleAddTeacherNameButton, translateService));
        stateStrategyMap.put(State.SAVE_DURATION.getState(),
                new SaveDurationEventState(childService, lessonService,
                        inlineScheduleAddEventDurationButton, translateService));
        stateStrategyMap.put(State.SAVE_EVENT_DAY.getState(),
                new SaveEventDayState(childService, lessonService,
                        lessonScheduleService, inlineScheduleAddExtraDayButton, translateService));
        stateStrategyMap.put(State.ADD_EVENT_TIME.getState(),
                new SaveEventTimeState(childService, lessonService,
                        lessonScheduleService, inlineScheduleAddEventTimeButton, translateService));
        stateStrategyMap.put(State.EDIT_SPECIFIC_EVENT_FIELD.getState(),
                new EditSpecificEventFieldState(childService, lessonService,
                        mainMenuButton, translateService));
        stateStrategyMap.put(State.EDIT_SPECIFIC_EVENT_TEACHER_NAME.getState(),
                new EditSpecificEventTeacherNameState(childService,
                        lessonService, mainMenuButton, translateService));
        stateStrategyMap.put(State.EDIT_SPECIFIC_EVENT_START_TIME.getState(),
                new EditSpecificEventStartTimeState(childService, lessonService,
                        lessonScheduleService, translateService));
        stateStrategyMap.put(State.EDIT_SPECIFIC_EVENT_START_TIME_STEP2.getState(),
                new EditSpecificEventStartTimeStep2State(childService, lessonService,
                        lessonScheduleService, mainMenuButton, translateService));
        stateStrategyMap.put(State.ADD_SPECIFIC_EVENT_START_TIME.getState(),
                new AddSpecificEventStartTimeState(childService, lessonService,
                        lessonScheduleService, startTimeButton, translateService));
        stateStrategyMap.put(State.EDIT_SPECIFIC_SCHEDULE_EVENT_DURATION.getState(),
                new EditSpecificScheduleEventDurationState(childService, lessonService,
                        eventDurationStateButton, translateService));
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
        return state.split(Separator.COLON_SEPARATOR)[0];
    }

    private String checkAddSpecificEventStartTimeState(String state) {
        return state.split(Separator.COLON_SEPARATOR)[0];
    }

}
