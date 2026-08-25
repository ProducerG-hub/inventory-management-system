CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP
);

CREATE TABLE categories (
    category_id SERIAL PRIMARY KEY,
    category_name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,
    active BOOLEAN DEFAULT TRUE,
    deleted_at TIMESTAMP
);

CREATE TABLE suppliers (
    supplier_id SERIAL PRIMARY KEY,
    supplier_name VARCHAR(100) NOT NULL,
    company_name VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(100),
    street VARCHAR(100),
    district VARCHAR(100),
    active BOOLEAN DEFAULT TRUE,
    deleted_at TIMESTAMP
);

CREATE TABLE products (
    product_id SERIAL PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    buying_price DECIMAL(10,2) NOT NULL CHECK (buying_price >= 0),
    selling_price DECIMAL(10,2) NOT NULL CHECK (selling_price >= 0),
    quantity INTEGER NOT NULL DEFAULT 0 CHECK (quantity >= 0),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    category_id INTEGER NOT NULL,
    supplier_id INTEGER NOT NULL,

    CONSTRAINT fk_product_category
        FOREIGN KEY (category_id)
        REFERENCES categories(category_id),

    CONSTRAINT fk_product_supplier
        FOREIGN KEY (supplier_id)
        REFERENCES suppliers(supplier_id)
);

CREATE TABLE customers (
    customer_id SERIAL PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    street VARCHAR(100),
    district VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sales (
    sale_id SERIAL PRIMARY KEY,
    total_amount DECIMAL(10,2) NOT NULL CHECK (total_amount >= 0),
    sale_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    customer_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,

    CONSTRAINT fk_sale_customer
        FOREIGN KEY (customer_id)
        REFERENCES customers(customer_id),

    CONSTRAINT fk_sale_user
        FOREIGN KEY (user_id)
        REFERENCES users(user_id)
);

CREATE TABLE sale_items (
    saleitem_id SERIAL PRIMARY KEY,

    unit_price DECIMAL(10,2) NOT NULL CHECK (unit_price >= 0),
    cost_price DECIMAL(10,2) NOT NULL CHECK (cost_price >= 0),
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    subtotal DECIMAL(10,2) NOT NULL CHECK (subtotal >= 0),

    sale_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,

    CONSTRAINT fk_saleitem_sale
        FOREIGN KEY (sale_id)
        REFERENCES sales(sale_id),

    CONSTRAINT fk_saleitem_product
        FOREIGN KEY (product_id)
        REFERENCES products(product_id)
);

CREATE TABLE stock_movements (
    movement_id SERIAL PRIMARY KEY,

    quantity INTEGER NOT NULL CHECK (quantity > 0),

    movement_type VARCHAR(10) NOT NULL
        CHECK (movement_type IN ('IN', 'OUT')),

    movement_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    remarks TEXT,

    product_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,

    CONSTRAINT fk_movement_product
        FOREIGN KEY (product_id)
        REFERENCES products(product_id),

    CONSTRAINT fk_movement_user
        FOREIGN KEY (user_id)
        REFERENCES users(user_id)
);

-- Message tables
CREATE TABLE conversations (
    conversation_id SERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE conversation_participants (
    conversation_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    joined_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (conversation_id, user_id),

    CONSTRAINT fk_conversation_participant_conversation
        FOREIGN KEY (conversation_id)
        REFERENCES conversations(conversation_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_conversation_participant_user
        FOREIGN KEY (user_id)
        REFERENCES users(user_id)
        ON DELETE CASCADE
);

CREATE TABLE messages (
    message_id SERIAL PRIMARY KEY,

    conversation_id INTEGER NOT NULL,

    sender_id INTEGER NOT NULL,

    message_content TEXT NOT NULL,

    is_read BOOLEAN NOT NULL DEFAULT FALSE,

    sent_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    deleted_at TIMESTAMP,

    CONSTRAINT fk_message_conversation
        FOREIGN KEY (conversation_id)
        REFERENCES conversations(conversation_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_message_sender
        FOREIGN KEY (sender_id)
        REFERENCES users(user_id)
        ON DELETE RESTRICT
);