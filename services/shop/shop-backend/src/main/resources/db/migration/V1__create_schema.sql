CREATE TABLE IF NOT EXISTS articles (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1024) NOT NULL,
    price DOUBLE PRECISION NOT NULL
);

CREATE TABLE IF NOT EXISTS cart (
    user_id VARCHAR(255) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS cart_items (
    id UUID PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    article_id UUID NOT NULL,
    quantity INTEGER NOT NULL,
    price_per_item DOUBLE PRECISION NOT NULL,
    CONSTRAINT fk_cart_items_cart_user_id
        FOREIGN KEY (user_id) REFERENCES cart (user_id)
);

CREATE TABLE IF NOT EXISTS orders (
    id UUID PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    status VARCHAR(64) NOT NULL,
    order_date TIMESTAMP NOT NULL,
    total_amount DOUBLE PRECISION NOT NULL
);

CREATE TABLE IF NOT EXISTS order_items (
    id UUID PRIMARY KEY,
    order_id UUID NOT NULL,
    article_id UUID NOT NULL,
    article_name VARCHAR(255) NOT NULL,
    quantity INTEGER NOT NULL,
    price_per_item DOUBLE PRECISION NOT NULL,
    CONSTRAINT fk_order_items_order_id
        FOREIGN KEY (order_id) REFERENCES orders (id)
);

CREATE INDEX IF NOT EXISTS idx_orders_user_id ON orders (user_id);
CREATE INDEX IF NOT EXISTS idx_cart_items_user_id ON cart_items (user_id);
CREATE INDEX IF NOT EXISTS idx_order_items_order_id ON order_items (order_id);
