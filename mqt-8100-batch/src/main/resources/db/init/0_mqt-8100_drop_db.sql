-- creation de la base de données --
-- created by Anas Neumann <anas.neumann.isamm@gmail.com>
-- since 05/02/2019
-- Unix
SELECT pg_terminate_backend(procpid) FROM pg_stat_activity WHERE datname = 'mqt-8100';
-- Windows
-- SELECT pg_terminate_backend (pg_stat_activity.pid) FROM pg_stat_activity WHERE pg_stat_activity.datname = 'mqt-8100';
DROP DATABASE mqt-8100;