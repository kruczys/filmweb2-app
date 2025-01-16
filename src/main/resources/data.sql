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
('Incepcja', 'Film o złodziejach snów i zagłębianiu się w podświadomość', '2010-07-16', CURRENT_TIMESTAMP, 1500, 'https://m.media-amazon.com/images/I/714b1KQmskL._AC_SL1280_.jpg', 'https://youtube.com/watch?v=inception'),
('Matrix', 'Kultowy film o wirtualnej rzeczywistości', '1999-03-31', CURRENT_TIMESTAMP, 2000, 'https://storage.googleapis.com/pod_public/1300/105082.jpg', 'https://youtube.com/watch?v=matrix'),
('Władca Pierścieni', 'Epicka opowieść o wyprawie do Mordoru', '2001-12-19', CURRENT_TIMESTAMP, 2500, 'https://images.squarespace-cdn.com/content/v1/606dc96785a0fa58fdbc57fe/b3d10633-9889-42ca-83e6-ee0fa3335a98/LOTR+-+Fellowship+-+REG+LQ.jpg', 'https://youtube.com/watch?v=lotr'),
('Pulp Fiction', 'Kultowy film Quentina Tarantino', '1994-10-14', CURRENT_TIMESTAMP, 1800, 'https://i.etsystatic.com/35704812/r/il/d1ee54/4228922390/il_794xN.4228922390_3pbr.jpg', 'https://youtube.com/watch?v=pulp-fiction'),
('Avengers', 'Superprodukcja o grupie superbohaterów', '2012-04-11', CURRENT_TIMESTAMP, 3000, 'https://4rooms.com.pl/environment/cache/images/600_600_productGfx_14854/Avegers-w-ramcee.jpg', 'https://youtube.com/watch?v=avengers'),
('Interstellar', 'Kosmiczna podróż w poszukiwaniu nowego domu', '2014-11-07', CURRENT_TIMESTAMP, 1700, 'https://static.printler.com/cache/5/5/0/a/5/6/550a5621331cc45d676ec5053203e2e5ec27ef76.jpg', 'https://youtube.com/watch?v=interstellar');

-- Powiązanie filmów z gatunkami
INSERT INTO movie_genre (movie_id, genre_id) 
SELECT m.id, g.id FROM movies m, genres g 
WHERE m.title = 'Incepcja' AND g.name IN ('Akcja', 'Sci-Fi', 'Thriller');

INSERT INTO movie_genre (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g
WHERE m.title = 'Matrix' AND g.name IN ('Akcja', 'Sci-Fi');

INSERT INTO movie_genre (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g
WHERE m.title = 'Władca Pierścieni' AND g.name IN ('Fantasy', 'Przygodowy', 'Akcja');

INSERT INTO movie_genre (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g
WHERE m.title = 'Pulp Fiction' AND g.name IN ('Dramat', 'Thriller');

INSERT INTO movie_genre (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g
WHERE m.title = 'Avengers' AND g.name IN ('Akcja', 'Sci-Fi', 'Przygodowy');

INSERT INTO movie_genre (movie_id, genre_id)
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