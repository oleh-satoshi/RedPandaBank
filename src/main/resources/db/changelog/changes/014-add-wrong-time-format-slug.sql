INSERT INTO translates (id, language, slug, value)
VALUES ((SELECT MAX(id) FROM translates) + 1, 'ENG', 'wrong-time-format', 'Oops! You entered an incorrect time format. 😕 Please enter the time in the format HH:MM (for example, 12:30)');
