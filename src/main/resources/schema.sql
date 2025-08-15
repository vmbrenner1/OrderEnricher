-- Create a table for 'product'
CREATE TABLE IF NOT EXISTS product (
    productId VARCHAR(6) PRIMARY KEY,
    category VARCHAR(255),
    name VARCHAR(255),
    price DECIMAL(10, 2),
    tags VARCHAR(255)
);

-- Create a table for 'customer'
CREATE TABLE IF NOT EXISTS customer (
    customerId VARCHAR(6) PRIMARY KEY,
    country VARCHAR(255),
    name VARCHAR(255),
    street VARCHAR(255),
    zip VARCHAR(10)
)