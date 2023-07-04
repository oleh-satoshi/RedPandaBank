UPDATE translates
SET value = REPLACE(value, '<p>', '\n')
WHERE translates.value LIKE '%<p>%';
