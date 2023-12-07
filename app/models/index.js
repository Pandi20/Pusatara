let config = require("../config/db.config.js");
const args = process.argv.slice(2);
if (args[0] && args[0] == 'dev') {
  config = require("../config/db.dev.config.js")
}

const Sequelize = require("sequelize");
const sequelize = new Sequelize(
  config.DB,
  config.USER,
  config.PASSWORD,
  {
    host: config.HOST,
    dialect: config.dialect,
    pool: {
      max: config.pool.max,
      min: config.pool.min,
      acquire: config.pool.acquire,
      idle: config.pool.idle
    }
  }
);

const db = {};

db.Sequelize = Sequelize;
db.sequelize = sequelize;

db.user = require("../models/user.model.js")(sequelize, Sequelize);
db.post = require("../models/post.model.js")(sequelize, Sequelize);
db.like = require("../models/like.model.js")(sequelize, Sequelize);
db.comment = require("../models/comment.model.js")(sequelize, Sequelize);

db.user.hasMany(db.post, {
  foreignKey: 'userId'
});
db.post.belongsTo(db.user);
db.user.hasMany(db.like, {
  foreignKey: 'userId'
});
db.like.belongsTo(db.user);
db.user.hasMany(db.comment, {
  foreignKey: 'userId'
});
db.comment.belongsTo(db.user);
db.like.belongsTo(db.post);
db.post.hasMany(db.like, {
  foreignKey: 'postId'
});
db.comment.belongsTo(db.post);
db.post.hasMany(db.comment, {
  foreignKey: 'postId'
});

module.exports = db;
