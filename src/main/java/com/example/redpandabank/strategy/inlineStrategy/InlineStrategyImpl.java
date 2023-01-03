package com.example.redpandabank.strategy.inlineStrategy;

import com.example.redpandabank.buttons.main.BackToMainMenuButton;
import com.example.redpandabank.model.WeekDay;
import com.example.redpandabank.service.LessonScheduleService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.strategy.inlineStrategy.ScheduleInline.InlineScheduleFindLessonByDay;
import com.example.redpandabank.strategy.inlineStrategy.ScheduleInline.InlineScheduleWeekdayButton;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class InlineStrategyImpl implements InlineStrategy {
    private Map<String, InlineHandler> strategyMap;
    private final BackToMainMenuButton backToMainMenuButton;
    private final LessonService lessonService;
    private final LessonScheduleService lessonScheduleService;

    public InlineStrategyImpl(BackToMainMenuButton backToMainMenuButton,
                              LessonService lessonService,
                              LessonScheduleService lessonScheduleService) {
        this.lessonService = lessonService;
        this.backToMainMenuButton = backToMainMenuButton;
        this.lessonScheduleService = lessonScheduleService;
        strategyMap = new HashMap<>();
        strategyMap.put("/scheduleMonday", new InlineScheduleWeekdayButton(this.backToMainMenuButton, lessonService, this.lessonScheduleService));
        strategyMap.put("/scheduleTuesday", new InlineScheduleWeekdayButton(this.backToMainMenuButton, lessonService, this.lessonScheduleService));
        strategyMap.put("/scheduleWednesday", new InlineScheduleWeekdayButton(this.backToMainMenuButton, lessonService, this.lessonScheduleService));
        strategyMap.put("/scheduleThursday", new InlineScheduleWeekdayButton(this.backToMainMenuButton, lessonService, this.lessonScheduleService));
        strategyMap.put("/scheduleFriday", new InlineScheduleWeekdayButton(this.backToMainMenuButton, lessonService, this.lessonScheduleService));
        strategyMap.put("/scheduleSaturday", new InlineScheduleWeekdayButton(this.backToMainMenuButton, lessonService, this.lessonScheduleService));
        strategyMap.put("/scheduleSunday", new InlineScheduleWeekdayButton(this.backToMainMenuButton, lessonService, this.lessonScheduleService));
        strategyMap.put(WeekDay.MONDAY.getDay(), new InlineScheduleFindLessonByDay(this.backToMainMenuButton, lessonService));
        strategyMap.put(WeekDay.TUESDAY.getDay(), new InlineScheduleFindLessonByDay(this.backToMainMenuButton, lessonService));
        strategyMap.put(WeekDay.WEDNESDAY.getDay(), new InlineScheduleFindLessonByDay(this.backToMainMenuButton, lessonService));
        strategyMap.put(WeekDay.THURSDAY.getDay(), new InlineScheduleFindLessonByDay(this.backToMainMenuButton, lessonService));
        strategyMap.put(WeekDay.FRIDAY.getDay(), new InlineScheduleFindLessonByDay(this.backToMainMenuButton, lessonService));
        strategyMap.put(WeekDay.SATURDAY.getDay(), new InlineScheduleFindLessonByDay(this.backToMainMenuButton, lessonService));
        strategyMap.put(WeekDay.SUNDAY.getDay(), new InlineScheduleFindLessonByDay(this.backToMainMenuButton, lessonService));
        strategyMap.put(WeekDay.ALL_WEEK.getDay(), new InlineScheduleFindLessonByDay(this.backToMainMenuButton, lessonService));

    }

    @Override
    public InlineHandler get(String command) {
        InlineHandler inlineHandler = strategyMap.get(command);
        return inlineHandler;
    }
}
