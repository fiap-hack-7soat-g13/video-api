CREATE TABLE "video" (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    content_type VARCHAR NOT NULL,
    size BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    status VARCHAR NOT NULL,
    created_by BIGSERIAL NOT NULL,
    created_at VARCHAR NOT NULL
);
