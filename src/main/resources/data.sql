-- Dodanie rozszerzenia UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Czyszczenie tabel
TRUNCATE TABLE users, movies, genres, cast_members, reviews, comments CASCADE;

-- Dodanie administratora (hasło: admin)
INSERT INTO users (username, password, email, enabled, registration_date, role)
VALUES ('admin', 
        '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW',
        'admin@example.com', 
        true, 
        CURRENT_TIMESTAMP, 
        'ADMIN');

-- Dodanie gatunków
INSERT INTO genres (name, description) VALUES
('Akcja', 'Filmy pełne dynamicznych scen i zwrotów akcji'),
('Komedia', 'Filmy wywołujące śmiech i rozbawienie'),
('Dramat', 'Poważne filmy o życiowych problemach'),
('Sci-Fi', 'Filmy science fiction'),
('Horror', 'Filmy grozy i strachu');

-- Dodanie aktorów
INSERT INTO cast_members (first_name, last_name, date_of_birth, biography, image_url) VALUES
('Tom', 'Cruise', '1962-07-03', 'Znany amerykański aktor', 'https://example.com/tom-cruise.jpg'),
('Leonardo', 'DiCaprio', '1974-11-11', 'Zdobywca Oscara', 'https://example.com/leonardo-dicaprio.jpg'),
('Scarlett', 'Johansson', '1984-11-22', 'Popularna aktorka', 'https://example.com/scarlett-johansson.jpg');

-- Dodanie filmów
INSERT INTO movies (title, description, release_date, created_at, view_count, image_url) VALUES
('Incepcja', 'Film o snach w snach', '2010-07-16', CURRENT_TIMESTAMP, 1000, 'https://example.com/inception.jpg'),
('Matrix', 'Film o wirtualnej rzeczywistości', '1999-03-31', CURRENT_TIMESTAMP, 2000, 'https://example.com/matrix.jpg'),
('Titanic', 'Romans na tonącym statku', '1997-12-19', CURRENT_TIMESTAMP, 3000, 'https://example.com/titanic.jpg');

-- Powiązanie filmów z gatunkami
INSERT INTO movies_genres (movies_id, genres_id) 
SELECT m.id, g.id FROM movies m, genres g 
WHERE m.title = 'Incepcja' AND g.name IN ('Akcja', 'Sci-Fi');

INSERT INTO movies_genres (movies_id, genres_id)
SELECT m.id, g.id FROM movies m, genres g
WHERE m.title = 'Matrix' AND g.name IN ('Akcja', 'Sci-Fi');

INSERT INTO movies_genres (movies_id, genres_id)
SELECT m.id, g.id FROM movies m, genres g
WHERE m.title = 'Titanic' AND g.name = 'Dramat';

-- Dodanie recenzji
INSERT INTO reviews (rating, content, user_id, movie_id, created_at, moderated)
SELECT 5, 'Świetny film!', u.id, m.id, CURRENT_TIMESTAMP, true
FROM users u, movies m
WHERE u.username = 'admin' AND m.title = 'Incepcja';

-- Dodanie komentarzy
INSERT INTO comments (content, user_id, review_id, created_at)
SELECT 'Zgadzam się z recenzją!', u.id, r.id, CURRENT_TIMESTAMP
FROM users u, reviews r
WHERE u.username = 'admin'; 