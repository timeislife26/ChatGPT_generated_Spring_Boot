-- Create Household Table
CREATE TABLE household (
                           eircode VARCHAR(8) PRIMARY KEY,
                           number_of_occupants INT NOT NULL,
                           max_number_of_occupants INT NOT NULL,
                           owner_occupied BIT NOT NULL
);

-- Create Pets Table
CREATE TABLE pets (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      animal_type VARCHAR(255) NOT NULL,
                      breed VARCHAR(255) NOT NULL,
                      age INT NOT NULL,
                      eircode VARCHAR(8) NOT NULL,  -- Foreign Key referencing household.eircode
                      FOREIGN KEY (eircode) REFERENCES household(eircode)
);

-- Create User Table
CREATE TABLE user (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      username VARCHAR(255) NOT NULL UNIQUE,
                      password VARCHAR(255) NOT NULL,
                      first_name VARCHAR(255),
                      last_name VARCHAR(255),
                      county VARCHAR(255),
                      roles VARCHAR(255) NOT NULL,  -- Can store comma-separated roles, or use a separate table if roles are more complex
                      locked BIT NOT NULL DEFAULT 0
);
