INSERT INTO translates (id, language, slug, value)
VALUES ((SELECT MAX(id) FROM translates) + 1, 'ENG', 'rescheduled-start-time-and-day', 'We have rescheduled the lesson time. 😄 On which day will this fun-filled lesson take place? 📅');
