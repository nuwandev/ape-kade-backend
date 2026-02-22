CREATE TABLE category
(
    id              BINARY(16)   NOT NULL,
    display_name    VARCHAR(100) NOT NULL,
    tagline         VARCHAR(150),
    slug            VARCHAR(100) NOT NULL,
    visibility      ENUM ('PUBLIC', 'HIDDEN', 'ARCHIVED') DEFAULT 'PUBLIC',
    icon            VARCHAR(255),
    seo_description TEXT,
    created_at      TIMESTAMP                             DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP                             DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_category_slug (slug)
);

CREATE INDEX idx_category_visibility ON category (visibility);
CREATE INDEX idx_category_slug ON category (slug);