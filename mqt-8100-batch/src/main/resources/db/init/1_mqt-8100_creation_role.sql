-- creation de role --
-- created by Anas Neumann <anas.neumann.isamm@gmail.com>
-- since 05/02/2019
CREATE ROLE mqt_admin LOGIN
 PASSWORD 'mqt456$pwd'
 NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;