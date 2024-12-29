CREATE TABLE IF NOT EXISTS public.users (
    id UUID PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    authorities VARCHAR(100) NOT NULL,
    points INTEGER NOT NULL
);