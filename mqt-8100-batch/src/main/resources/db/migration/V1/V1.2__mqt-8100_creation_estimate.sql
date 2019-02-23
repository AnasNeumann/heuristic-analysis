-- Création des tables du projet
-- created by Anas Neumann <anas.neumann.isamm@gmail.com>
-- since 22/02/2019

-- ********************************************************************************************
-- STRUCTURE DE DONNEES POUR LES ESTIMATION D'UNE BORNE INFERIEUR V1
-- ********************************************************************************************

CREATE TABLE ESTIMATE (
	ID SERIAL PRIMARY KEY,
	VALUE INTEGER NOT NULL DEFAULT 1,
	TIMESTAMPS TIMESTAMP
);