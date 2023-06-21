INSERT INTO translates (id, language, slug, value)
VALUES ((SELECT MAX(id) FROM translates) + 1, 'ENG', 'day', '🌤️ Day: ');
