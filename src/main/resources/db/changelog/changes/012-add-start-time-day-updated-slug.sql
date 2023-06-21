INSERT INTO translates (id, language, slug, value)
VALUES ((SELECT MAX(id) FROM translates) + 1, 'ENG', 'start-time-day-updated', 'The time ⏰ and 📅 day 📅 have been updated! 🎉✨');
