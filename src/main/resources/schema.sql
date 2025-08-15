-- Create a table for 'product'
CREATE TABLE IF NOT EXISTS product (
    product_id VARCHAR(6) PRIMARY KEY,
    category VARCHAR(255),
    name VARCHAR(255),
    price DECIMAL(10, 2),
    tags VARCHAR(255)
);

-- Create a table for 'customer'
CREATE TABLE IF NOT EXISTS customer (
    customer_id VARCHAR(6) PRIMARY KEY,
    country VARCHAR(255),
    name VARCHAR(255),
    street VARCHAR(255),
    zip VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS enriched_order (
    id LONG PRIMARY KEY,
    order_id VARCHAR(255),
    timestamp TIMESTAMP WITH TIME ZONE,
    customer_id VARCHAR(6),
    CONSTRAINT fk_customer
        FOREIGN KEY (customer_id)
        REFERENCES customer(customer_id)
);