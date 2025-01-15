-- Dodanie rozszerzenia UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Czyszczenie tabel
TRUNCATE TABLE users, movies, genres, cast_members, reviews, comments CASCADE;

-- Dodanie zwykłych użytkowników (hasło dla wszystkich: password123)
INSERT INTO users (username, password, email, enabled, registration_date, role) VALUES
('jan_kowalski', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW', 'jan@example.com', true, CURRENT_TIMESTAMP, 'USER'),
('anna_nowak', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW', 'anna@example.com', true, CURRENT_TIMESTAMP, 'USER'),
('marek_wisniewski', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW', 'marek@example.com', true, CURRENT_TIMESTAMP, 'USER');

-- Dodanie gatunków
INSERT INTO genres (name, description) VALUES
('Akcja', 'Filmy pełne dynamicznych scen i zwrotów akcji'),
('Komedia', 'Filmy wywołujące śmiech i rozbawienie'),
('Dramat', 'Poważne filmy o życiowych problemach'),
('Sci-Fi', 'Filmy science fiction'),
('Horror', 'Filmy grozy i strachu'),
('Przygodowy', 'Filmy o wielkich przygodach i podróżach'),
('Animowany', 'Filmy animowane dla wszystkich grup wiekowych'),
('Thriller', 'Trzymające w napięciu filmy z zagadkami'),
('Fantasy', 'Filmy w światach fantasy i magii'),
('Dokumentalny', 'Filmy dokumentalne o rzeczywistych wydarzeniach');

-- Dodanie aktorów
INSERT INTO cast_members (first_name, last_name, date_of_birth, biography, image_url) VALUES
('Tom', 'Cruise', '1962-07-03', 'Znany amerykański aktor, gwiazda filmu Top Gun', 'https://example.com/tom-cruise.jpg'),
('Leonardo', 'DiCaprio', '1974-11-11', 'Zdobywca Oscara, znany z filmu Titanic', 'https://example.com/leonardo-dicaprio.jpg'),
('Scarlett', 'Johansson', '1984-11-22', 'Popularna aktorka, gwiazda filmu Lost in Translation', 'https://example.com/scarlett-johansson.jpg'),
('Robert', 'Downey Jr.', '1965-04-04', 'Znany z roli Iron Mana', 'https://example.com/rdj.jpg'),
('Morgan', 'Freeman', '1937-06-01', 'Legendarny aktor o charakterystycznym głosie', 'https://example.com/freeman.jpg'),
('Jennifer', 'Lawrence', '1990-08-15', 'Zdobywczyni Oscara, gwiazda Igrzysk Śmierci', 'https://example.com/lawrence.jpg');

-- Dodanie filmów
INSERT INTO movies (title, description, release_date, created_at, view_count, image_url, trailer_url) VALUES
('Incepcja', 'Film o złodziejach snów i zagłębianiu się w podświadomość', '2010-07-16', CURRENT_TIMESTAMP, 1500, 'https://example.com/inception.jpg', 'https://youtube.com/watch?v=inception'),
('Matrix', 'Kultowy film o wirtualnej rzeczywistości', '1999-03-31', CURRENT_TIMESTAMP, 2000, 'https://example.com/matrix.jpg', 'https://youtube.com/watch?v=matrix'),
('Władca Pierścieni', 'Epicka opowieść o wyprawie do Mordoru', '2001-12-19', CURRENT_TIMESTAMP, 2500, 'https://example.com/lotr.jpg', 'https://youtube.com/watch?v=lotr'),
('Pulp Fiction', 'Kultowy film Quentina Tarantino', '1994-10-14', CURRENT_TIMESTAMP, 1800, 'https://example.com/pulp-fiction.jpg', 'https://youtube.com/watch?v=pulp-fiction'),
('Avengers', 'Superprodukcja o grupie superbohaterów', '2012-04-11', CURRENT_TIMESTAMP, 3000, 'https://example.com/avengers.jpg', 'https://youtube.com/watch?v=avengers'),
('Interstellar', 'Kosmiczna podróż w poszukiwaniu nowego domu', '2014-11-07', CURRENT_TIMESTAMP, 1700, 'https://example.com/interstellar.jpg', 'https://youtube.com/watch?v=interstellar');

-- Powiązanie filmów z gatunkami
INSERT INTO movies_genres (movies_id, genres_id) 
SELECT m.id, g.id FROM movies m, genres g 
WHERE m.title = 'Incepcja' AND g.name IN ('Akcja', 'Sci-Fi', 'Thriller');

INSERT INTO movies_genres (movies_id, genres_id)
SELECT m.id, g.id FROM movies m, genres g
WHERE m.title = 'Matrix' AND g.name IN ('Akcja', 'Sci-Fi');

INSERT INTO movies_genres (movies_id, genres_id)
SELECT m.id, g.id FROM movies m, genres g
WHERE m.title = 'Władca Pierścieni' AND g.name IN ('Fantasy', 'Przygodowy', 'Akcja');

INSERT INTO movies_genres (movies_id, genres_id)
SELECT m.id, g.id FROM movies m, genres g
WHERE m.title = 'Pulp Fiction' AND g.name IN ('Dramat', 'Thriller');

INSERT INTO movies_genres (movies_id, genres_id)
SELECT m.id, g.id FROM movies m, genres g
WHERE m.title = 'Avengers' AND g.name IN ('Akcja', 'Sci-Fi', 'Przygodowy');

INSERT INTO movies_genres (movies_id, genres_id)
SELECT m.id, g.id FROM movies m, genres g
WHERE m.title = 'Interstellar' AND g.name IN ('Sci-Fi', 'Dramat', 'Przygodowy');

-- Dodanie recenzji
INSERT INTO reviews (rating, content, user_id, movie_id, created_at, moderated)
SELECT 5, 'Absolutne arcydzieło! Film, który zmienia sposób patrzenia na kino.', u.id, m.id, CURRENT_TIMESTAMP - INTERVAL '2 days', true
FROM users u, movies m
WHERE u.username = 'jan_kowalski' AND m.title = 'Incepcja';

INSERT INTO reviews (rating, content, user_id, movie_id, created_at, moderated)
SELECT 4, 'Świetny film, choć momentami trudny w odbiorze. Warto obejrzeć kilka razy.', u.id, m.id, CURRENT_TIMESTAMP - INTERVAL '5 days', true
FROM users u, movies m
WHERE u.username = 'anna_nowak' AND m.title = 'Matrix';

INSERT INTO reviews (rating, content, user_id, movie_id, created_at, moderated)
SELECT 5, 'Niesamowita produkcja! Efekty specjalne i fabuła na najwyższym poziomie.', u.id, m.id, CURRENT_TIMESTAMP - INTERVAL '1 day', true
FROM users u, movies m
WHERE u.username = 'marek_wisniewski' AND m.title = 'Władca Pierścieni';

-- Dodanie komentarzy do recenzji
INSERT INTO comments (content, user_id, review_id, created_at)
SELECT 'Całkowicie się zgadzam! Świetna analiza filmu.', u.id, r.id, CURRENT_TIMESTAMP - INTERVAL '1 day'
FROM users u, reviews r, movies m
WHERE u.username = 'anna_nowak' 
AND r.movie_id = m.id 
AND m.title = 'Incepcja'
LIMIT 1;

INSERT INTO comments (content, user_id, review_id, created_at)
SELECT 'Moim zdaniem film zasługuje na 5 gwiazdek!', u.id, r.id, CURRENT_TIMESTAMP - INTERVAL '12 hours'
FROM users u, reviews r, movies m
WHERE u.username = 'marek_wisniewski' 
AND r.movie_id = m.id 
AND m.title = 'Matrix'
LIMIT 1; 