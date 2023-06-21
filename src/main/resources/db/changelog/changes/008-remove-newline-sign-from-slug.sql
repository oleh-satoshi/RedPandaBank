UPDATE translates
SET value = REPLACE(value, '\n', '<p>')
WHERE translates.value LIKE '%\n%';
