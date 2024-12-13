-- Insert sample data for Pets table
INSERT INTO pets (name, animal_type, breed, age, eircode) VALUES
                                                              ('Buddy', 'Dog', 'Golden Retriever', 3, 'D02XY45'),
                                                              ('Mittens', 'Cat', 'Siamese', 2, 'A94B6F3'),
                                                              ('Charlie', 'Dog', 'Beagle', 4, 'T12AB34'),
                                                              ('Whiskers', 'Cat', 'Persian', 5, 'C15DE67'),
                                                              ('Coco', 'Rabbit', 'Holland Lop', 1, 'F12GH89'),
                                                              ('Goldie', 'Fish', 'Goldfish', 1, 'B78IJ01'),
                                                              ('Polly', 'Bird', 'Parakeet', 2, 'M34KL56'),
                                                              ('Max', 'Dog', 'German Shepherd', 5, 'P90QR78'),
                                                              ('Luna', 'Cat', 'Maine Coon', 3, 'V23ST01'),
                                                              ('Nibbles', 'Hamster', 'Syrian Hamster', 1, 'X45UV67');

-- Insert sample data for Household table
INSERT INTO household (eircode, number_of_occupants, max_number_of_occupants, owner_occupied) VALUES
                                                                                                  ('D02XY45', 3, 5, 1),
                                                                                                  ('A94B6F3', 4, 6, 0),
                                                                                                  ('T12AB34', 2, 4, 1),
                                                                                                  ('C15DE67', 5, 7, 1),
                                                                                                  ('F12GH89', 1, 2, 0),
                                                                                                  ('B78IJ01', 3, 5, 1),
                                                                                                  ('M34KL56', 4, 6, 0),
                                                                                                  ('P90QR78', 2, 4, 1),
                                                                                                  ('V23ST01', 5, 7, 1),
                                                                                                  ('X45UV67', 1, 2, 0),
                                                                                                  ('Y67WX89', 3, 5, 1),
                                                                                                  ('Z01YZ23', 4, 6, 0),
                                                                                                  ('Q45AB78', 2, 4, 1),
                                                                                                  ('R67CD01', 5, 7, 1),
                                                                                                  ('S23EF45', 1, 2, 0);

INSERT INTO user (username, password, first_name, last_name, county, roles, locked)
VALUES ('user', '{bcrypt-encoded-password}', 'John', 'Doe', 'Cork', '["USER"]', false);

INSERT INTO user (username, password, first_name, last_name, county, roles, locked)
VALUES ('admin', '{bcrypt-encoded-password}', 'Jane', 'Doe', 'Kerry', '["ADMIN"]', false);
