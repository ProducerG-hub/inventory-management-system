-- Users table schema
USERS(
    user_id SERIAL,
    full_name VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(255),
    created_at TIMESTAMP,

    PK(user_id)
)

-- Categories table schema
CATEGORIES(
    category_id SERIAL,
    category_name VARCHAR(100),
    description TEXT,

    PK(category_id)
)

-- Suppliers table schema
SUPPLIERS(
    supplier_id SERIAL,
    supplier_name VARCHAR(100),
    company_name VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(100),
    street VARCHAR(100),
    district VARCHAR(100),

    PK(supplier_id)
)

-- Products table schema
PRODUCTS(
    product_id SERIAL,
    product_name VARCHAR(100),
    buying_price DECIMAL(10,2),
    selling_price DECIMAL(10,2),
    quantity INTEGER,
    is_active BOOLEAN,
    created_at TIMESTAMP,

    category_id INTEGER,
    supplier_id INTEGER,

    PK(product_id),

    FK(category_id)
        REFERENCES CATEGORIES(category_id),

    FK(supplier_id)
        REFERENCES SUPPLIERS(supplier_id)
)

-- Customers table schema
CUSTOMERS(
    customer_id SERIAL,
    customer_name VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(100),
    street VARCHAR(100),
    district VARCHAR(100),
    created_at TIMESTAMP,

    PK(customer_id)
)

-- Sales table schema
SALES(
    sale_id SERIAL,
    total_amount DECIMAL(10,2),
    sale_date TIMESTAMP,

    customer_id INTEGER,
    user_id INTEGER,

    PK(sale_id),

    FK(customer_id)
        REFERENCES CUSTOMERS(customer_id),

    FK(user_id)
        REFERENCES USERS(user_id)
)

-- Sale Items table schema
SALE_ITEMS(
    saleitem_id SERIAL,
    unit_price DECIMAL(10,2),
    quantity INTEGER,
    subtotal DECIMAL(10,2),

    sale_id INTEGER,
    product_id INTEGER,

    PK(saleitem_id),

    FK(sale_id)
        REFERENCES SALES(sale_id),

    FK(product_id)
        REFERENCES PRODUCTS(product_id)
)

-- Stock Movements table schema
STOCK_MOVEMENTS(
    movement_id SERIAL,
    quantity INTEGER,
    movement_type VARCHAR(10),
    movement_date TIMESTAMP,

    product_id INTEGER,
    user_id INTEGER,

    PK(movement_id),

    FK(product_id)
        REFERENCES PRODUCTS(product_id),

    FK(user_id)
        REFERENCES USERS(user_id)
)
