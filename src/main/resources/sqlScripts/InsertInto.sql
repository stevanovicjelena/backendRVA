--PROJEKAT PODACI

INSERT INTO "projekat"("id", "naziv", "oznaka", "opis")
VALUES (NEXTVAL('projekat_seq'), 'Restoran', 'APP', 'Razvoj veb aplikacije za podrsku poslovanju rada restorana');
INSERT INTO "projekat"("id", "naziv", "oznaka", "opis")
VALUES (NEXTVAL('projekat_seq'), 'Konferencija', 'KONF', 'Planiranje neophodnih resursa i organizacije naucne konferencije');
INSERT INTO "projekat"("id", "naziv", "oznaka", "opis")
VALUES (NEXTVAL('projekat_seq'), 'Afrodita', 'AFR', 'Izrada logoa za potrebe kozmeticke kuce');
INSERT INTO "projekat"("id", "naziv", "oznaka", "opis")
VALUES (NEXTVAL('projekat_seq'), 'Robot', 'ROB', 'Programiranje robota koji ce voditi racuna o temperaturi u prostoriji i na osnovu nje po potrebi ukljuciti/iskljuciti klima uredjaje');

INSERT INTO "projekat"("id", "naziv", "oznaka", "opis")
VALUES (-100, 'TestNaziv', 'TestOznaka', 'TestOpis');

--  SMER PODACI

INSERT INTO "smer"("id", "naziv", "oznaka")
VALUES (NEXTVAL('smer_seq'), 'Inzenjerstvo informacionih sistema', 'IIS');
INSERT INTO "smer"("id", "naziv", "oznaka")
VALUES (NEXTVAL('smer_seq'), 'Inzenjerski menadzment', 'IM');
INSERT INTO "smer"("id", "naziv", "oznaka")
VALUES (NEXTVAL('smer_seq'), 'Graficko inzenjerstvo i dizajn', 'GRID');
INSERT INTO "smer"("id", "naziv", "oznaka")
VALUES (NEXTVAL('smer_seq'), 'Mehatronika', 'MH');

INSERT INTO "smer"("id", "naziv", "oznaka")
VALUES (-100, 'TestNaziv', 'TestOznaka');

-- GRUPA PODACI

INSERT INTO "grupa"("id", "oznaka", "smer")
VALUES (NEXTVAL('grupa_seq'), 'I', 1);
INSERT INTO "grupa"("id", "oznaka", "smer")
VALUES (NEXTVAL('grupa_seq'), 'II', 2);
INSERT INTO "grupa"("id", "oznaka", "smer")
VALUES (NEXTVAL('grupa_seq'), 'III', 3);
INSERT INTO "grupa"("id", "oznaka", "smer")
VALUES (NEXTVAL('grupa_seq'), 'IV', 4);

INSERT INTO "grupa"("id", "oznaka", "smer")
VALUES (-100, 'TestOznaka', 1);


-- STUDENT PODACI

INSERT INTO "student"("id", "ime", "prezime", "broj_indeksa", "grupa", "projekat")
VALUES (NEXTVAL('student_seq'), 'Marko', 'Petrovic', 'IT1/2017', 1, 1);
INSERT INTO "student"("id", "ime", "prezime", "broj_indeksa", "grupa", "projekat")
VALUES (NEXTVAL('student_seq'), 'Ana', 'Filipovic', 'IM2/2018', 2, 2);
INSERT INTO "student"("id", "ime", "prezime", "broj_indeksa", "grupa", "projekat")
VALUES (NEXTVAL('student_seq'), 'Petar', 'Milosevic', 'II3/2019', 3, 3);
INSERT INTO "student"("id", "ime", "prezime", "broj_indeksa", "grupa", "projekat")
VALUES (NEXTVAL('student_seq'), 'Marija', 'Aleksic', 'MH4/2020', 4, 4);

INSERT INTO "student"("id", "ime", "prezime", "broj_indeksa", "grupa", "projekat")
VALUES (-100, 'TestIme', 'TestPrz', 'TestBrInd', 1, 1);
