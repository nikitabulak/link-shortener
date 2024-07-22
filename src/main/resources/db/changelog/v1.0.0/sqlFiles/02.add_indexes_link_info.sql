CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_link_info_end_time
    ON link_shortener.link_info (end_time);

CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_link_info_link
    ON link_shortener.link_info USING gin (link gin_trgm_ops);

CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_link_info_description
    ON link_shortener.link_info USING gin (description gin_trgm_ops);