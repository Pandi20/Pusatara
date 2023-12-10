require('dotenv').config()
const express = require("express");
const cors = require("cors");
const bcrypt = require("bcryptjs");

const app = express();

app.use(cors());
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

const db = require("./app/models");
const User = db.user;

db.sequelize.sync({force: true}).then(() => {
  console.log('Drop and Resync Db');
  initial();
});

function initial() {
  User.create({
    username: "admin",
    password: bcrypt.hashSync(process.env.testing_password, 8),
    email: "meta@bangkit.academy"
  });
}

app.get("/", (req, res) => {
  res.json({ message: "Welcome to my application." });
});

require('./app/routes/ai.routes')(app);
require('./app/routes/auth.routes')(app);
require('./app/routes/user.routes')(app);
require('./app/routes/post.routes')(app);

const PORT = process.env.PORT || 8080;
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}.`);
});