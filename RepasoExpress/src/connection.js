const express = require("express");
const cors = require('cors');
const bodyParser = require('body-parser');
const mysql = require('mysql2/promise');


const app = express();
const PORT = 3000;

app.use(cors());
app.use(bodyParser.json());

// Configuración de la conexión a la base de datos MySQL
const myPool = mysql.createPool({
    host: '127.0.0.1',          // Dirección del servidor MySQL (localhost o la IP del MariaDB)
    user: 'root',         // Usuario de MySQL
    password: '',    // Contraseña del usuario de MySQL
    database: 'repasomvp',   // Nombre de la base de datos en MySQL
    connectionLimit: 10         // Límite de conexiones del pool
});

// Verificar conexión con la base de datos
myPool.getConnection((err, connection) => {
    if (err) {
        console.error('Error al conectar con la base de datos:', err);
        return;
    }
    console.log('Conexión exitosa a la base de datos');
    connection.release();  // Liberar la conexión después de verificar
});

// Iniciar el servidor
app.listen(PORT, () => {
    console.log(`Servidor corriendo en http://localhost:${PORT}`);
});

async function loginUser(email, password) {
    const [rows] = await myPool.query('SELECT * FROM usuarios WHERE email = ? AND password = ?', [email, password]);//Se podria meter directamente en el metodo del app.post
    return rows; // rows siempre será un array
}


// Método para el login (@POST)
app.post('/login', async (req, res) => {
    const { email, password } = req.body;

    try {
        const users = await loginUser(email, password);
        if (users && users.length > 0) { // Verifica si 'users' es un array y tiene elementos
            const user = users[0]; // Toma el primer usuario
            res.json({ message: 'Login successful', user });
        } else {
            res.status(401).json({ message: 'Invalid email or password' });
        }
    } catch (error) {
        console.error('Error al iniciar sesión:', error);
        res.status(500).json({ message: 'Error al iniciar sesión' });
    }
});

